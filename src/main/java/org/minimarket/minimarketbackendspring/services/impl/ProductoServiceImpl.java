package org.minimarket.minimarketbackendspring.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.minimarket.minimarketbackendspring.dtos.ProductoDTO;
import org.minimarket.minimarketbackendspring.entities.Categoria;
import org.minimarket.minimarketbackendspring.entities.Producto;
import org.minimarket.minimarketbackendspring.entities.Proveedor;
import org.minimarket.minimarketbackendspring.repositories.CategoriaRepository;
import org.minimarket.minimarketbackendspring.repositories.ProductoRepository;
import org.minimarket.minimarketbackendspring.repositories.ProveedorRepository;
import org.minimarket.minimarketbackendspring.services.interfaces.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementación del servicio de Producto.
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    /**
     * Convierte una entidad Producto a ProductoDTO.
     *
     * @param p la entidad Producto
     * @return
     */
    public ProductoDTO convertToDTO(Producto p) {
        return new ProductoDTO(
                p.getIdProducto(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getStock(),
                p.getFoto(),
                p.getEstado(),
                p.getIdCategoria() != null ? p.getIdCategoria().getNombre() : null,
                p.getIdProveedor() != null ? p.getIdProveedor().getNombre() : null,
                p.getCreatedAt() != null ? p.getCreatedAt().toString() : null,
                p.getUpdatedAt() != null ? p.getUpdatedAt().toString() : null,
                p.getCreatedBy() != null ? p.getCreatedBy().getIdUsuario() : null,
                p.getUpdateBy() != null ? p.getUpdateBy().getIdUsuario() : null,
                p.getIdCategoria() != null ? p.getIdCategoria().getId() : null, // ID para inserción
                p.getIdProveedor() != null ? p.getIdProveedor().getId() : null // ID para inserción
        );
    }

    /**
     * Obtiene una lista de todos los productos.
     *
     * @return
     */
    @Override
    public List<ProductoDTO> findAll() {
        List<Producto> productos = productoRepository.findAll();

        return productos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un producto por su ID.
     *
     * @param id el identificador del producto
     * @return
     */
    @Override
    public ProductoDTO findById(String id) {
        return productoRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));
    }

    /**
     * Busca un producto por su nombre.
     *
     * @param nombre el nombre del producto
     * @return
     */
    @Override
    public Optional<ProductoDTO> findByNombre(String nombre) {
        return productoRepository.findByNombre(nombre)
                .map(this::convertToDTO);
    }

    /**
     * Obtiene una lista de productos asociados a una categoría específica.
     *
     * @param idCategoria el identificador de la categoría
     * @return
     */
    @Override
    public List<ProductoDTO> findByCategoriaId(Long idCategoria) {
        return productoRepository.findByIdCategoria_Id(idCategoria).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una lista de productos asociados a un proveedor específico.
     *
     * @param idProveedor el identificador del proveedor
     * @return
     */
    @Override
    public List<ProductoDTO> findByProveedorId(Long idProveedor) {
        return productoRepository.findByIdProveedor_Id(idProveedor).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Guarda un nuevo producto.
     *
     * @param producto el objeto ProductoDTO a guardar
     */
    @Override
    public void save(ProductoDTO producto) {
        Producto p = new Producto();

        // Aqui se genera un ID único para nuevos productos
        if (producto.getIdProducto() == null || producto.getIdProducto().isEmpty()) {
            p.setIdProducto(UUID.randomUUID().toString().replace("-", "").toUpperCase());
        } else {
            p.setIdProducto(producto.getIdProducto());
        }

        p.setNombre(producto.getNombre());
        p.setDescripcion(producto.getDescripcion());
        p.setPrecio(producto.getPrecio());
        p.setStock(producto.getStock());
        p.setFoto(producto.getFoto());
        p.setEstado("activo");

        // Verifica si la categoría existe antes de asignarlo
        if (producto.getIdCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(producto.getIdCategoria())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Categoría no encontrada con ID: " + producto.getIdCategoria()));
            p.setIdCategoria(categoria);
        } else {
            p.setIdCategoria(null);
        }

        // Verifica si el proveedor existe antes de asignarlo
        if (producto.getIdProveedor() != null) {
            Proveedor proveedor = proveedorRepository.findById(producto.getIdProveedor())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Proveedor no encontrado con ID: " + producto.getIdProveedor()));
            p.setIdProveedor(proveedor);
        }

        productoRepository.save(p);
    }

    /**
     * Actualiza un producto existente.
     *
     * @param producto
     */
    @Override
    public void update(ProductoDTO producto) {
        Producto p = productoRepository.findById(producto.getIdProducto())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Producto no encontrado con ID: " + producto.getIdProducto()));

        p.setNombre(producto.getNombre());
        p.setDescripcion(producto.getDescripcion());
        p.setPrecio(producto.getPrecio());
        p.setStock(producto.getStock());
        p.setFoto(producto.getFoto());
        p.setEstado(producto.getEstado());

        // Validar y asignar categoría
        if (producto.getIdCategoria() == null) {
            throw new IllegalArgumentException("La categoría no puede ser nula.");
        }
        Categoria categoria = categoriaRepository.findById(producto.getIdCategoria())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Categoría no encontrada con ID: " + producto.getIdCategoria()));
        p.setIdCategoria(categoria);

        // Validar y asignar proveedor
        if (producto.getIdProveedor() == null) {
            throw new IllegalArgumentException("El proveedor no puede ser nulo.");
        }
        Proveedor proveedor = proveedorRepository.findById(producto.getIdProveedor())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Proveedor no encontrado con ID: " + producto.getIdProveedor()));
        p.setIdProveedor(proveedor);

        productoRepository.save(p);
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));
        producto.setEstado("inactivo");
        productoRepository.save(producto);
    }

    /**
        * Obtiene productos con stock bajo (5 o menos unidades).
        *
        * @return una lista de productos con stock bajo
     */
    @Override
    public List<ProductoDTO> findProductosConStockBajo() {
        List<Producto> productos = productoRepository.findByStockLessThanEqual(LOW_STOCK_THRESHOLD);
        return productos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Constant for low stock threshold
    private static final long LOW_STOCK_THRESHOLD = 5L;
}