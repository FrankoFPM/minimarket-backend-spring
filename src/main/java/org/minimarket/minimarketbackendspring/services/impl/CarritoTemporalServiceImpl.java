package org.minimarket.minimarketbackendspring.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.minimarket.minimarketbackendspring.dtos.CarritoTemporalDto;
import org.minimarket.minimarketbackendspring.entities.CarritoTemporal;
import org.minimarket.minimarketbackendspring.entities.Producto;
import org.minimarket.minimarketbackendspring.entities.Usuario;
import org.minimarket.minimarketbackendspring.repositories.CarritoTemporalRepository;
import org.minimarket.minimarketbackendspring.repositories.ProductoRepository;
import org.minimarket.minimarketbackendspring.repositories.UsuarioRepository;
import org.minimarket.minimarketbackendspring.services.interfaces.CarritoTemporalService;
import org.minimarket.minimarketbackendspring.services.interfaces.DescuentoPromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementación del servicio de gestión de carrito temporal.
 * 
 * Maneja la lógica de agregar, actualizar y eliminar productos del carrito
 * antes de crear el pedido final.
 */
@Service
@Transactional
public class CarritoTemporalServiceImpl implements CarritoTemporalService {

    @Autowired
    private CarritoTemporalRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DescuentoPromocionService descuentoService;

    /**
     * Obtiene todos los items del carrito temporal.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarritoTemporalDto> findAll() {
        List<CarritoTemporal> items = carritoRepository.findAll();
        return convertToDTOList(items);
    }

    /**
     * Busca un item específico por su ID.
     */
    @Override
    @Transactional(readOnly = true)
    public CarritoTemporalDto findById(Long id) {
        CarritoTemporal item = carritoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item de carrito no encontrado con ID: " + id));
        return convertToDTO(item);
    }


    /**
     * Calcula el total del carrito aplicando descuentos disponibles.
     */
    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcularTotalCarritoConDescuentos(String idUsuario) {
        List<CarritoTemporalDto> items = findByUsuario(idUsuario);
        
        return items.stream()
                .map(item -> {
                    // Precio original del producto
                    BigDecimal precioOriginal = BigDecimal.valueOf(item.getIdProductoPrecio());
                    BigDecimal cantidad = BigDecimal.valueOf(item.getCantidad());
                    
                    // Aplicar descuentos si existen
                    BigDecimal precioConDescuento = descuentoService.calcularPrecioConDescuento(
                            item.getIdProducto(), precioOriginal);
                    
                    return precioConDescuento.multiply(cantidad);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }


    /**
     * Obtiene items del carrito con información de descuentos aplicados.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarritoTemporalDto> findByUsuarioConDescuentos(String idUsuario) {
        List<CarritoTemporalDto> items = findByUsuario(idUsuario);
        
        // Enriquecer cada item con información de descuentos
        return items.stream()
                .map(item -> {
                    // Verificar si tiene descuentos activos
                    boolean tieneDescuento = descuentoService.tieneDescuentosActivos(item.getIdProducto());
                    
                    if (tieneDescuento) {
                        BigDecimal precioOriginal = BigDecimal.valueOf(item.getIdProductoPrecio());
                        BigDecimal precioConDescuento = descuentoService.calcularPrecioConDescuento(
                                item.getIdProducto(), precioOriginal);
                        
                    }
                    
                    return item;
                })
                .collect(Collectors.toList());
    }

    /**
     * Crea un nuevo item en el carrito temporal.
     */
    @Override
    public CarritoTemporalDto save(CarritoTemporalDto carritoDTO, String idUsuario, String idProducto) {
        // Validar que el usuario existe
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + idUsuario));

        // Validar que el producto existe
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + idProducto));

        CarritoTemporal item = convertToEntity(carritoDTO);
        item.setIdUsuario(usuario);
        item.setIdProducto(producto);

        // Establecer valores por defecto
        if (item.getCantidad() == null || item.getCantidad() <= 0) {
            item.setCantidad(1L);
        }
        if (item.getFechaAgregado() == null) {
            item.setFechaAgregado(Instant.now());
        }

        CarritoTemporal savedItem = carritoRepository.save(item);
        return convertToDTO(savedItem);
    }

    /**
     * Actualiza un item existente en el carrito.
     */
    @Override
    public CarritoTemporalDto update(Long id, CarritoTemporalDto carritoDTO) {
        CarritoTemporal existingItem = carritoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item de carrito no encontrado con ID: " + id));
        if (carritoDTO.getCantidad() != null && carritoDTO.getCantidad() > 0) {
            existingItem.setCantidad(carritoDTO.getCantidad());
        }

        CarritoTemporal updatedItem = carritoRepository.save(existingItem);
        return convertToDTO(updatedItem);
    }

    /**
     * Elimina un item del carrito por su ID.
     */
    @Override
    public void deleteById(Long id) {
        if (!carritoRepository.existsById(id)) {
            throw new EntityNotFoundException("Item de carrito no encontrado con ID: " + id);
        }
        carritoRepository.deleteById(id);
    }

    /**
     * Obtiene todos los items del carrito de un usuario.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarritoTemporalDto> findByUsuario(String idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + idUsuario);
        }

        List<CarritoTemporal> items = carritoRepository.findByIdUsuario_IdUsuario(idUsuario);
        return convertToDTOList(items);
    }

    /**
     * Busca carritos que contienen un producto específico.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarritoTemporalDto> findByProducto(String idProducto) {
        if (!productoRepository.existsById(idProducto)) {
            throw new EntityNotFoundException("Producto no encontrado con ID: " + idProducto);
        }

        List<CarritoTemporal> items = carritoRepository.findByIdProducto_IdProducto(idProducto);
        return convertToDTOList(items);
    }

    /**
     * Busca un producto específico en el carrito de un usuario.
     */
    @Override
    @Transactional(readOnly = true)
    public CarritoTemporalDto findByUsuarioAndProducto(String idUsuario, String idProducto) {
        CarritoTemporal item = carritoRepository.findByIdUsuario_IdUsuarioAndIdProducto_IdProducto(idUsuario, idProducto);
        return item != null ? convertToDTO(item) : null;
    }

    /**
     * Agrega un producto al carrito o incrementa la cantidad si ya existe.
     */
    @Override
    public CarritoTemporalDto agregarProductoAlCarrito(String idUsuario, String idProducto, Long cantidad) {
        boolean existe = carritoRepository.existsByIdUsuario_IdUsuarioAndIdProducto_IdProducto(idUsuario, idProducto);

        if (existe) {
            // Si ya existe, incrementar cantidad
            CarritoTemporalDto itemExistente = findByUsuarioAndProducto(idUsuario, idProducto);
            Long nuevaCantidad = itemExistente.getCantidad() + cantidad;
            return actualizarCantidad(idUsuario, idProducto, nuevaCantidad);
        } else {
            // Si no existe, crear nuevo item
            CarritoTemporalDto nuevoItem = new CarritoTemporalDto();
            nuevoItem.setCantidad(cantidad);
            return save(nuevoItem, idUsuario, idProducto);
        }
    }

    /**
     * Actualiza la cantidad de un producto en el carrito.
     */
    @Override
    public CarritoTemporalDto actualizarCantidad(String idUsuario, String idProducto, Long nuevaCantidad) {
        CarritoTemporalDto itemDTO = findByUsuarioAndProducto(idUsuario, idProducto);

        if (itemDTO == null) {
            throw new EntityNotFoundException("No se encontró el producto en el carrito");
        }

        if (nuevaCantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }

        itemDTO.setCantidad(nuevaCantidad);
        return update(itemDTO.getId(), itemDTO);
    }

    /**
     * Elimina un producto específico del carrito de un usuario.
     */
    @Override
    public void eliminarProductoDelCarrito(String idUsuario, String idProducto) {
        CarritoTemporalDto itemDTO = findByUsuarioAndProducto(idUsuario, idProducto);

        if (itemDTO == null) {
            throw new EntityNotFoundException("No se encontró el producto en el carrito");
        }

        deleteById(itemDTO.getId());
    }

    /**
     * Vacía completamente el carrito de un usuario.
     */
    @Override
    public void vaciarCarrito(String idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + idUsuario);
        }

        carritoRepository.deleteByIdUsuario_IdUsuario(idUsuario);
    }

    /**
     * Cuenta el número de items en el carrito de un usuario.
     */
    @Override
    @Transactional(readOnly = true)
    public Long countByUsuario(String idUsuario) {
        return carritoRepository.countByIdUsuario_IdUsuario(idUsuario);
    }

    /**
     * Verifica si un producto específico está en el carrito del usuario.
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existeProductoEnCarrito(String idUsuario, String idProducto) {
        return carritoRepository.existsByIdUsuario_IdUsuarioAndIdProducto_IdProducto(idUsuario, idProducto);
    }

    /**
     * Verifica si un usuario tiene items en su carrito.
     */
    @Override
    @Transactional(readOnly = true)
    public boolean tieneCarrito(String idUsuario) {
        return carritoRepository.existsByIdUsuario_IdUsuario(idUsuario);
    }


    /**
     * Calcula el total del carrito de un usuario.
     * 
     * Suma el precio de cada producto multiplicado por su cantidad.
     */

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcularTotalCarrito(String idUsuario) {
        List<CarritoTemporalDto> items = findByUsuario(idUsuario);
        
        return items.stream()
                .map(item -> {
                    BigDecimal precio = BigDecimal.valueOf(item.getIdProductoPrecio());
                    BigDecimal cantidad = BigDecimal.valueOf(item.getCantidad());
                    return precio.multiply(cantidad);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Convierte entidad CarritoTemporal a DTO.
     */
    private CarritoTemporalDto convertToDTO(CarritoTemporal item) {
        return new CarritoTemporalDto( // ✅ CORREGIDO
                item.getId(),
                item.getIdUsuario() != null ? item.getIdUsuario().getIdUsuario() : null,
                item.getIdProducto() != null ? item.getIdProducto().getIdProducto() : null,
                item.getIdProducto() != null ? item.getIdProducto().getNombre() : null,
                item.getIdProducto() != null ? item.getIdProducto().getPrecio() : null,
                item.getCantidad(),
                item.getFechaAgregado()
        );
    }

    /**
     * Convierte DTO a entidad CarritoTemporal.
     */
    private CarritoTemporal convertToEntity(CarritoTemporalDto dto) {
        CarritoTemporal item = new CarritoTemporal();
        item.setId(dto.getId());
        item.setCantidad(dto.getCantidad());
        item.setFechaAgregado(dto.getFechaAgregado());
        return item;
    }

    /**
     * Convierte lista de entidades a lista de DTOs.
     */
    private List<CarritoTemporalDto> convertToDTOList(List<CarritoTemporal> items) {
        return items.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}