package org.minimarket.minimarketbackendspring.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.minimarket.minimarketbackendspring.dtos.DetallePedidoDTO;
import org.minimarket.minimarketbackendspring.entities.DetallePedido;
import org.minimarket.minimarketbackendspring.entities.Pedido;
import org.minimarket.minimarketbackendspring.entities.Producto;
import org.minimarket.minimarketbackendspring.repositories.DetallePedidoRepository;
import org.minimarket.minimarketbackendspring.repositories.PedidoRepository;
import org.minimarket.minimarketbackendspring.repositories.ProductoRepository;
import org.minimarket.minimarketbackendspring.services.interfaces.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class DetallePedidoServiceImpl implements DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DetallePedidoDTO> findAll() {
        List<DetallePedido> detalles = detallePedidoRepository.findAll();
        return convertToDTOList(detalles);
    }

    @Override
    @Transactional(readOnly = true)
    public DetallePedidoDTO findById(Long id) {
        DetallePedido detalle = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DetallePedido no encontrado con ID: " + id));
        return convertToDTO(detalle);
    }

    @Override
    public DetallePedidoDTO save(DetallePedidoDTO detallePedidoDTO, Long idPedido, String idProducto) {
        // Validar que el pedido existe
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + idPedido));

        // Validar que el producto existe
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + idProducto));

        DetallePedido detalle = convertToEntity(detallePedidoDTO);
        detalle.setIdPedido(pedido);
        detalle.setIdProducto(producto);

        // Establecer valores por defecto si no están presentes
        if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
            detalle.setCantidad(1L);
        }
        if (detalle.getPrecioUnitario() == null) {
            detalle.setPrecioUnitario(BigDecimal.valueOf(producto.getPrecio()));
        }

        // Calcular subtotal automáticamente
        BigDecimal subtotal = detalle.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(detalle.getCantidad()));
        detalle.setSubtotal(subtotal);

        DetallePedido savedDetalle = detallePedidoRepository.save(detalle);
        return convertToDTO(savedDetalle);
    }

    @Override
    public DetallePedidoDTO update(Long id, DetallePedidoDTO detallePedidoDTO) {
        DetallePedido existingDetalle = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DetallePedido no encontrado con ID: " + id));

        // Actualizar campos permitidos
        if (detallePedidoDTO.getCantidad() != null && detallePedidoDTO.getCantidad() > 0) {
            existingDetalle.setCantidad(detallePedidoDTO.getCantidad());
        }
        if (detallePedidoDTO.getPrecioUnitario() != null) {
            existingDetalle.setPrecioUnitario(detallePedidoDTO.getPrecioUnitario());
        }

        // Recalcular subtotal
        BigDecimal subtotal = existingDetalle.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(existingDetalle.getCantidad()));
        existingDetalle.setSubtotal(subtotal);

        DetallePedido updatedDetalle = detallePedidoRepository.save(existingDetalle);
        return convertToDTO(updatedDetalle);
    }

    @Override
    public void deleteById(Long id) {
        if (!detallePedidoRepository.existsById(id)) {
            throw new EntityNotFoundException("DetallePedido no encontrado con ID: " + id);
        }
        detallePedidoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetallePedidoDTO> findByPedidoId(Long idPedido) {
        // Validar que el pedido existe
        if (!pedidoRepository.existsById(idPedido)) {
            throw new EntityNotFoundException("Pedido no encontrado con ID: " + idPedido);
        }
        
        List<DetallePedido> detalles = detallePedidoRepository.findByIdPedido_IdPedido(idPedido); 
        return convertToDTOList(detalles);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetallePedidoDTO> findByProductoId(String idProducto) {
        // Validar que el producto existe
        if (!productoRepository.existsById(idProducto)) {
            throw new EntityNotFoundException("Producto no encontrado con ID: " + idProducto);
        }
        
        List<DetallePedido> detalles = detallePedidoRepository.findByIdProducto_IdProducto(idProducto);
        return convertToDTOList(detalles);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetallePedidoDTO> findByUsuario(String idUsuario) {
        List<DetallePedido> detalles = detallePedidoRepository.findByIdPedido_IdUsuario_IdUsuario(idUsuario);
        return convertToDTOList(detalles);
    }

    @Override
    @Transactional(readOnly = true)
    public DetallePedidoDTO findByPedidoAndProducto(Long idPedido, String idProducto) {
        DetallePedido detalle = detallePedidoRepository.findByIdPedido_IdPedidoAndIdProducto_IdProducto(idPedido, idProducto); 
        return detalle != null ? convertToDTO(detalle) : null;
    }

    @Override
    public DetallePedidoDTO agregarProductoAPedido(Long idPedido, String idProducto, Long cantidad) {
        // Verificar si el producto ya existe en el pedido usando método existente
        boolean existe = detallePedidoRepository.existsByIdPedido_IdPedidoAndIdProducto_IdProducto(idPedido, idProducto); 
        
        if (existe) {
            // Si existe, actualizar cantidad
            DetallePedidoDTO detalleExistente = findByPedidoAndProducto(idPedido, idProducto);
            Long nuevaCantidad = detalleExistente.getCantidad() + cantidad;
            return actualizarCantidad(idPedido, idProducto, nuevaCantidad);
        } else {
            // Si no existe, crear nuevo detalle
            DetallePedidoDTO nuevoDetalle = new DetallePedidoDTO();
            nuevoDetalle.setCantidad(cantidad);
            return save(nuevoDetalle, idPedido, idProducto);
        }
    }

    @Override
    public DetallePedidoDTO actualizarCantidad(Long idPedido, String idProducto, Long nuevaCantidad) {
        DetallePedidoDTO detalleDTO = findByPedidoAndProducto(idPedido, idProducto);
        
        if (detalleDTO == null) {
            throw new EntityNotFoundException("No se encontró el producto en el pedido");
        }

        if (nuevaCantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }

        // Actualizar usando el método update existente
        detalleDTO.setCantidad(nuevaCantidad);
        return update(detalleDTO.getId(), detalleDTO);
    }

    @Override
    public void eliminarProductoDePedido(Long idPedido, String idProducto) {
        DetallePedidoDTO detalleDTO = findByPedidoAndProducto(idPedido, idProducto);
        
        if (detalleDTO == null) {
            throw new EntityNotFoundException("No se encontró el producto en el pedido");
        }

        deleteById(detalleDTO.getId());
    }

    @Override
    public void eliminarTodosDetallesPorPedido(Long idPedido) {
        // Validar que el pedido existe
        if (!pedidoRepository.existsById(idPedido)) {
            throw new EntityNotFoundException("Pedido no encontrado con ID: " + idPedido);
        }
        
        detallePedidoRepository.deleteByIdPedido_IdPedido(idPedido); 
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByPedidoId(Long idPedido) {
        return detallePedidoRepository.countByIdPedido_IdPedido(idPedido);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeProductoEnPedido(Long idPedido, String idProducto) {
        return detallePedidoRepository.existsByIdPedido_IdPedidoAndIdProducto_IdProducto(idPedido, idProducto);
    } 


    private DetallePedidoDTO convertToDTO(DetallePedido detalle) {
        return new DetallePedidoDTO(
            detalle.getId(),                                                 
            detalle.getIdPedido() != null ? detalle.getIdPedido().getId() : null, 
            detalle.getIdPedido() != null && detalle.getIdPedido().getIdUsuario() != null ? 
                detalle.getIdPedido().getIdUsuario().getNombre() : null,             
            detalle.getIdPedido() != null && detalle.getIdPedido().getIdUsuario() != null ? 
                detalle.getIdPedido().getIdUsuario().getApellido() : null,         
            detalle.getIdProducto() != null ? detalle.getIdProducto().getNombre() : null, 
            detalle.getCantidad(),                                                   
            detalle.getPrecioUnitario(),                                     
            detalle.getSubtotal()                                 
        );
    }

    private DetallePedido convertToEntity(DetallePedidoDTO dto) {
        DetallePedido detalle = new DetallePedido();
        detalle.setId(dto.getId());  
        detalle.setCantidad(dto.getCantidad());       
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setSubtotal(dto.getSubtotal());           
        return detalle;
    }

    private List<DetallePedidoDTO> convertToDTOList(List<DetallePedido> detalles) {
        return detalles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}