package org.minimarket.minimarketbackendspring.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.minimarket.minimarketbackendspring.dtos.DescuentoPromocionDTO;
import org.minimarket.minimarketbackendspring.entities.DescuentoPromocion;
import org.minimarket.minimarketbackendspring.entities.Producto;
import org.minimarket.minimarketbackendspring.repositories.DescuentoPromocionRepository;
import org.minimarket.minimarketbackendspring.repositories.ProductoRepository;
import org.minimarket.minimarketbackendspring.services.interfaces.DescuentoPromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementación del servicio de gestión de descuentos y promociones.
 * 
 * Maneja la lógica de aplicar descuentos por porcentaje a productos específicos,
 * validando fechas de vigencia y estados activos/inactivos.
 */
@Service
@Transactional
public class DescuentoPromocionServiceImpl implements DescuentoPromocionService {

    @Autowired
    private DescuentoPromocionRepository descuentoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Obtiene todos los descuentos del sistema.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DescuentoPromocionDTO> findAll() {
        List<DescuentoPromocion> descuentos = descuentoRepository.findAll();
        return convertToDTOList(descuentos);
    }

    /**
     * Busca un descuento específico por su ID.
     */
    @Override
    @Transactional(readOnly = true)
    public DescuentoPromocionDTO findById(Long id) {
        DescuentoPromocion descuento = descuentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Descuento no encontrado con ID: " + id));
        return convertToDTO(descuento);
    }

    /**
     * Crea un nuevo descuento asociado a un producto específico.
     * Aplica valores por defecto y valida reglas de negocio.
     */
    @Override
    public DescuentoPromocionDTO save(DescuentoPromocionDTO descuentoDTO, String idProducto) {
        // Validar que el producto existe
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + idProducto));

        DescuentoPromocion descuento = convertToEntity(descuentoDTO);
        descuento.setIdProducto(producto);

        // Establecer valores por defecto si no se proporcionan
        if (descuento.getEstado() == null) {
            descuento.setEstado("activo");
        }
        if (descuento.getFechaInicio() == null) {
            descuento.setFechaInicio(LocalDate.now());
        }
        if (descuento.getFechaFin() == null) {
            descuento.setFechaFin(LocalDate.now().plusDays(30)); // 30 días por defecto
        }
        if (descuento.getPorcentaje() == null) {
            descuento.setPorcentaje(BigDecimal.ZERO);
        }

        // Validar porcentaje entre 0% y 100%
        if (descuento.getPorcentaje().compareTo(BigDecimal.ZERO) < 0 || 
            descuento.getPorcentaje().compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100");
        }
        
        // Validar que fecha inicio no sea posterior a fecha fin
        if (descuento.getFechaInicio().isAfter(descuento.getFechaFin())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        DescuentoPromocion savedDescuento = descuentoRepository.save(descuento);
        return convertToDTO(savedDescuento);
    }

    /**
     * Actualiza un descuento existente.
     */
    @Override
    public DescuentoPromocionDTO update(Long id, DescuentoPromocionDTO descuentoDTO) {
        DescuentoPromocion existingDescuento = descuentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Descuento no encontrado con ID: " + id));

        // Actualizar solo campos proporcionados (no null)
        if (descuentoDTO.getDescripcion() != null) {
            existingDescuento.setDescripcion(descuentoDTO.getDescripcion());
        }
        if (descuentoDTO.getPorcentaje() != null) {
            // Validar nuevo porcentaje
            if (descuentoDTO.getPorcentaje().compareTo(BigDecimal.ZERO) < 0 || 
                descuentoDTO.getPorcentaje().compareTo(BigDecimal.valueOf(100)) > 0) {
                throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100");
            }
            existingDescuento.setPorcentaje(descuentoDTO.getPorcentaje());
        }
        if (descuentoDTO.getFechaInicio() != null) {
            existingDescuento.setFechaInicio(descuentoDTO.getFechaInicio());
        }
        if (descuentoDTO.getFechaFin() != null) {
            existingDescuento.setFechaFin(descuentoDTO.getFechaFin());
        }
        if (descuentoDTO.getEstado() != null) {
            existingDescuento.setEstado(descuentoDTO.getEstado());
        }

        // Validar fechas después de actualizar
        if (existingDescuento.getFechaInicio().isAfter(existingDescuento.getFechaFin())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        DescuentoPromocion updatedDescuento = descuentoRepository.save(existingDescuento);
        return convertToDTO(updatedDescuento);
    }

    /**
     * Elimina un descuento por su ID.
     */
    @Override
    public void deleteById(Long id) {
        if (!descuentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Descuento no encontrado con ID: " + id);
        }
        descuentoRepository.deleteById(id);
    }

    /**
     * Obtiene todos los descuentos asociados a un producto específico.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DescuentoPromocionDTO> findByProducto(String idProducto) {
        if (!productoRepository.existsById(idProducto)) {
            throw new EntityNotFoundException("Producto no encontrado con ID: " + idProducto);
        }

        List<DescuentoPromocion> descuentos = descuentoRepository.findByIdProducto_IdProducto(idProducto);
        return convertToDTOList(descuentos);
    }

    /**
     * Busca descuentos por estado (activo, inactivo, expirado).
     */
    @Override
    @Transactional(readOnly = true)
    public List<DescuentoPromocionDTO> findByEstado(String estado) {
        List<DescuentoPromocion> descuentos = descuentoRepository.findByEstado(estado);
        return convertToDTOList(descuentos);
    }

    /**
     * Obtiene descuentos activos y vigentes para un producto específico.
     * Un descuento es vigente si está activo y la fecha actual está entre fechaInicio y fechaFin.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DescuentoPromocionDTO> findDescuentosActivosParaProducto(String idProducto) {
        if (!productoRepository.existsById(idProducto)) {
            throw new EntityNotFoundException("Producto no encontrado con ID: " + idProducto);
        }

        LocalDate fechaActual = LocalDate.now();
        List<DescuentoPromocion> descuentos = descuentoRepository
                .findByIdProducto_IdProductoAndEstadoAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                        idProducto, "activo", fechaActual, fechaActual);
        return convertToDTOList(descuentos);
    }

    /**
     * Obtiene todos los descuentos vigentes del sistema.
     * Útil para mostrar promociones actuales en el frontend.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DescuentoPromocionDTO> findDescuentosVigentes() {
        LocalDate fechaActual = LocalDate.now();
        List<DescuentoPromocion> descuentos = descuentoRepository
                .findByEstadoAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                        "activo", fechaActual, fechaActual);
        return convertToDTOList(descuentos);
    }

    /**
     * Encuentra el descuento con mayor porcentaje para un producto específico.
     * Retorna null si no hay descuentos vigentes.
     */
    @Override
    @Transactional(readOnly = true)
    public DescuentoPromocionDTO findMejorDescuentoParaProducto(String idProducto) {
        if (!productoRepository.existsById(idProducto)) {
            throw new EntityNotFoundException("Producto no encontrado con ID: " + idProducto);
        }

        LocalDate fechaActual = LocalDate.now();
        List<DescuentoPromocion> descuentos = descuentoRepository
                .findByIdProducto_IdProductoAndEstadoAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByPorcentajeDesc(
                        idProducto, "activo", fechaActual, fechaActual);

        if (descuentos.isEmpty()) {
            return null; // No hay descuentos disponibles
        }

        return convertToDTO(descuentos.get(0)); // El primero es el de mayor porcentaje
    }

    /**
     * Calcula el precio final aplicando el mejor descuento disponible.
     * Si no hay descuentos, retorna el precio original.
     */
    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcularPrecioConDescuento(String idProducto, BigDecimal precioOriginal) {
        DescuentoPromocionDTO mejorDescuento = findMejorDescuentoParaProducto(idProducto);

        if (mejorDescuento == null) {
            return precioOriginal; // Sin descuentos aplicables
        }

        // Calcular descuento: porcentaje / 100 * precio
        BigDecimal porcentajeDescuento = mejorDescuento.getPorcentaje().divide(BigDecimal.valueOf(100));
        BigDecimal descuentoAplicado = precioOriginal.multiply(porcentajeDescuento);
        BigDecimal precioFinal = precioOriginal.subtract(descuentoAplicado);

        return precioFinal.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Activa un descuento cambiando su estado a "activo".
     */
    @Override
    public DescuentoPromocionDTO activarDescuento(Long id) {
        DescuentoPromocion descuento = descuentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Descuento no encontrado con ID: " + id));

        descuento.setEstado("activo");
        DescuentoPromocion updatedDescuento = descuentoRepository.save(descuento);
        return convertToDTO(updatedDescuento);
    }

    /**
     * Desactiva un descuento cambiando su estado a "inactivo".
     */
    @Override
    public DescuentoPromocionDTO desactivarDescuento(Long id) {
        DescuentoPromocion descuento = descuentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Descuento no encontrado con ID: " + id));

        descuento.setEstado("inactivo");
        DescuentoPromocion updatedDescuento = descuentoRepository.save(descuento);
        return convertToDTO(updatedDescuento);
    }

    /**
     * Cuenta el número total de descuentos asociados a un producto.
     */
    @Override
    @Transactional(readOnly = true)
    public Long countByProducto(String idProducto) {
        return descuentoRepository.countByIdProducto_IdProducto(idProducto);
    }

    /**
     * Verifica si un producto tiene al menos un descuento activo.
     */
    @Override
    @Transactional(readOnly = true)
    public boolean tieneDescuentosActivos(String idProducto) {
        return descuentoRepository.existsByIdProducto_IdProductoAndEstado(idProducto, "activo");
    }

    /**
     * Valida si un descuento específico está vigente.
     * Un descuento es vigente si está activo y dentro del rango de fechas.
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isDescuentoVigente(Long idDescuento) {
        DescuentoPromocion descuento = descuentoRepository.findById(idDescuento).orElse(null);
        
        if (descuento == null || !"activo".equals(descuento.getEstado())) {
            return false;
        }

        LocalDate fechaActual = LocalDate.now();
        return !fechaActual.isBefore(descuento.getFechaInicio()) && 
               !fechaActual.isAfter(descuento.getFechaFin());
    }

    /**
     * Convierte entidad DescuentoPromocion a DTO incluyendo datos del producto.
     */
    private DescuentoPromocionDTO convertToDTO(DescuentoPromocion descuento) {
        return new DescuentoPromocionDTO(
                descuento.getId(),
                descuento.getIdProducto() != null ? descuento.getIdProducto().getIdProducto() : null,
                descuento.getIdProducto() != null ? descuento.getIdProducto().getNombre() : null,
                descuento.getDescripcion(),
                descuento.getPorcentaje(),
                descuento.getFechaInicio(),
                descuento.getFechaFin(),
                descuento.getEstado()
        );
    }

    /**
     * Convierte DTO a entidad DescuentoPromocion (sin relaciones).
     */
    private DescuentoPromocion convertToEntity(DescuentoPromocionDTO dto) {
        DescuentoPromocion descuento = new DescuentoPromocion();
        descuento.setId(dto.getId());
        descuento.setDescripcion(dto.getDescripcion());
        descuento.setPorcentaje(dto.getPorcentaje());
        descuento.setFechaInicio(dto.getFechaInicio());
        descuento.setFechaFin(dto.getFechaFin());
        descuento.setEstado(dto.getEstado());
        return descuento;
    }

    /**
     * Convierte lista de entidades a lista de DTOs.
     */
    private List<DescuentoPromocionDTO> convertToDTOList(List<DescuentoPromocion> descuentos) {
        return descuentos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}