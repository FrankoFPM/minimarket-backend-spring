package org.minimarket.minimarketbackendspring.unit;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.minimarket.minimarketbackendspring.dtos.CarritoTemporalDto;
import org.minimarket.minimarketbackendspring.entities.CarritoTemporal;
import org.minimarket.minimarketbackendspring.entities.Producto;
import org.minimarket.minimarketbackendspring.entities.Usuario;
import org.minimarket.minimarketbackendspring.repositories.CarritoTemporalRepository;
import org.minimarket.minimarketbackendspring.repositories.ProductoRepository;
import org.minimarket.minimarketbackendspring.repositories.UsuarioRepository;
import org.minimarket.minimarketbackendspring.services.impl.CarritoTemporalServiceImpl;
import org.minimarket.minimarketbackendspring.services.interfaces.DescuentoPromocionService;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;

/**
 * Tests TDD para CarritoTemporalService Estos tests fueron escritos ANTES de la
 * implementación siguiendo metodología TDD
 */
@ExtendWith(MockitoExtension.class)
class CarritoTemporalServiceTest {

    @Mock
    private CarritoTemporalRepository carritoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private DescuentoPromocionService descuentoService;

    @InjectMocks
    private CarritoTemporalServiceImpl carritoService;

    private Usuario usuarioMock;
    private Producto productoMock;
    private CarritoTemporal carritoMock;
    private CarritoTemporalDto carritoDTO;

    @BeforeEach
    void setUp() {
        // Setup de datos de prueba para TDD usando tus DTOs reales
        usuarioMock = new Usuario();
        usuarioMock.setIdUsuario("USER123");
        usuarioMock.setNombre("Juan Test");
        usuarioMock.setApellido("Pérez Test");
        usuarioMock.setEmail("juan.test@email.com");

        productoMock = new Producto();
        productoMock.setIdProducto("PROD001");
        productoMock.setNombre("Leche Gloria");
        productoMock.setPrecio(4.50);
        productoMock.setStock(100L);
        productoMock.setEstado("activo");

        carritoMock = new CarritoTemporal();
        carritoMock.setId(1L);
        carritoMock.setIdUsuario(usuarioMock);
        carritoMock.setIdProducto(productoMock);
        carritoMock.setCantidad(2L);
        carritoMock.setFechaAgregado(LocalDateTime.now());

        carritoDTO = new CarritoTemporalDto();
        carritoDTO.setId(1L);
        carritoDTO.setIdUsuario("USER123");
        carritoDTO.setIdProducto("PROD001");
        carritoDTO.setIdProductoNombre("Leche Gloria");
        carritoDTO.setIdProductoPrecio(4.50);
        carritoDTO.setCantidad(2L);
        carritoDTO.setFechaAgregado(LocalDateTime.now());
    }

    /**
     * TDD FASE RED: Test escrito primero para agregar productos al carrito
     * Motivó la implementación del método agregarProductoAlCarrito
     */
    @Test
    void debeAgregarProductoAlCarrito_ProductoNuevo_TDD() {
        // Given - Configuración TDD para producto nuevo en carrito
        String idUsuario = "USER123";
        String idProducto = "PROD001";
        Long cantidad = 2L;

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuarioMock));
        when(productoRepository.findById(idProducto)).thenReturn(Optional.of(productoMock));
        when(carritoRepository.existsByIdUsuario_IdUsuarioAndIdProducto_IdProducto(idUsuario, idProducto))
                .thenReturn(false);
        when(carritoRepository.save(any(CarritoTemporal.class))).thenReturn(carritoMock);

        // When - Ejecutar funcionalidad bajo prueba TDD
        CarritoTemporalDto resultado = carritoService.agregarProductoAlCarrito(idUsuario, idProducto, cantidad);

        // Then - Validaciones TDD
        assertNotNull(resultado, "Debe retornar el item agregado al carrito");
        assertEquals("USER123", resultado.getIdUsuario());
        assertEquals("PROD001", resultado.getIdProducto());
        assertEquals("Leche Gloria", resultado.getIdProductoNombre());
        assertEquals(4.50, resultado.getIdProductoPrecio());
        assertEquals(2L, resultado.getCantidad());

        // Verificar interacciones según TDD
        verify(usuarioRepository).findById(idUsuario);
        verify(productoRepository).findById(idProducto);
        verify(carritoRepository).existsByIdUsuario_IdUsuarioAndIdProducto_IdProducto(idUsuario, idProducto);
        verify(carritoRepository).save(any(CarritoTemporal.class));
    }

    /**
     * TDD FASE RED: Test que motivó el cálculo de total con descuentos
     */
    @Test
    void debeIncrementarCantidad_ProductoYaExisteEnCarrito_TDD() {
        // Given - Producto ya existe en carrito, debe incrementar cantidad
        String idUsuario = "USER123";
        String idProducto = "PROD001";
        Long cantidadAdicional = 3L;

        CarritoTemporal carritoExistente = new CarritoTemporal();
        carritoExistente.setId(1L);
        carritoExistente.setIdUsuario(usuarioMock);
        carritoExistente.setIdProducto(productoMock);
        carritoExistente.setCantidad(2L);

        when(carritoRepository.existsByIdUsuario_IdUsuarioAndIdProducto_IdProducto(idUsuario, idProducto))
                .thenReturn(true);
        when(carritoRepository.findByIdUsuario_IdUsuarioAndIdProducto_IdProducto(idUsuario, idProducto))
                .thenReturn(carritoExistente);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carritoExistente));        
        when(carritoRepository.save(any(CarritoTemporal.class))).thenReturn(carritoExistente);

        // When - Agregar más cantidad del mismo producto
        CarritoTemporalDto resultado = carritoService.agregarProductoAlCarrito(idUsuario, idProducto, cantidadAdicional);

        // Then - Debe incrementar cantidad existente (2 + 3 = 5)
        assertNotNull(resultado);
        assertEquals(5L, resultado.getCantidad(), "Cantidad debe incrementarse de 2 a 5");
        verify(carritoRepository).existsByIdUsuario_IdUsuarioAndIdProducto_IdProducto(idUsuario, idProducto);
        verify(carritoRepository, atLeast(1)).findByIdUsuario_IdUsuarioAndIdProducto_IdProducto(idUsuario, idProducto);
        verify(carritoRepository).save(any(CarritoTemporal.class));
    }

    /**
     * TDD FASE RED: Test que motivó el cálculo de total con descuentos
     */
    @Test
    void debeCalcularTotalCarritoConDescuentosAplicados_TDD() {
        // Given - Carrito con productos que tienen descuentos activos
        String idUsuario = "USER123";

        List<CarritoTemporal> itemsCarrito = Arrays.asList(carritoMock);


        when(usuarioRepository.existsById(idUsuario)).thenReturn(true);
        when(carritoRepository.findByIdUsuario_IdUsuario(idUsuario)).thenReturn(itemsCarrito);

        // Configurar descuentos - producto con 10% descuento
        when(descuentoService.calcularPrecioConDescuento("PROD001", BigDecimal.valueOf(4.50)))
                .thenReturn(BigDecimal.valueOf(4.05));

        // When - Calcular total con descuentos
        BigDecimal total = carritoService.calcularTotalCarritoConDescuentos(idUsuario);

        // Then - Debe aplicar descuentos en el cálculo (4.05 * 2 = 8.10)
        assertNotNull(total, "Debe calcular total con descuentos");
        assertEquals(0, BigDecimal.valueOf(8.10).compareTo(total), "Total con descuento debe ser 8.10");
        assertTrue(total.compareTo(BigDecimal.ZERO) > 0, "Total debe ser mayor a cero");

        // Verificar que se calcularon descuentos
        verify(descuentoService).calcularPrecioConDescuento("PROD001", BigDecimal.valueOf(4.50));
    }

    /**
     * TDD FASE RED: Test que motivó la funcionalidad de obtener carrito con
     * descuentos
     */
    @Test
    void debeObtenerCarritoConInformacionDescuentos_TDD() {
        // Given - Usuario con items en carrito
        String idUsuario = "USER123";

        List<CarritoTemporal> itemsCarrito = Arrays.asList(carritoMock);

        when(usuarioRepository.existsById(idUsuario)).thenReturn(true);
        when(carritoRepository.findByIdUsuario_IdUsuario(idUsuario)).thenReturn(itemsCarrito);
        when(descuentoService.tieneDescuentosActivos("PROD001")).thenReturn(true);
        when(descuentoService.calcularPrecioConDescuento("PROD001", BigDecimal.valueOf(4.50)))
                .thenReturn(BigDecimal.valueOf(4.05));

        // When - Obtener carrito con descuentos
        List<CarritoTemporalDto> resultado = carritoService.findByUsuarioConDescuentos(idUsuario);

        // Then - Debe retornar items con información de descuentos
        assertNotNull(resultado, "Debe retornar lista de items del carrito");
        assertFalse(resultado.isEmpty(), "Lista no debe estar vacía");

        // Verificar que se consultaron descuentos
        verify(descuentoService).tieneDescuentosActivos("PROD001");
        verify(descuentoService).calcularPrecioConDescuento("PROD001", BigDecimal.valueOf(4.50));
    }

    /**
     * TDD FASE RED: Test que motivó la funcionalidad de vaciar carrito
     */
    @Test
    void debeVaciarCarritoCompleto_TDD() {
        // Given - Usuario con carrito lleno
        String idUsuario = "USER123";
        when(usuarioRepository.existsById(idUsuario)).thenReturn(true);

        // When - Vaciar carrito
        carritoService.vaciarCarrito(idUsuario);

        // Then - Debe eliminar todos los items del usuario
        verify(carritoRepository).deleteByIdUsuario_IdUsuario(idUsuario);
    }

    /**
     * TDD FASE RED: Test que motivó validaciones de usuario inexistente
     */
    @Test
    void debeLanzarExcepcion_UsuarioNoExiste_TDD() {
        // Given - ID de usuario que no existe
        String idUsuarioInexistente = "USER999";
        String idProducto = "PROD001";

        when(usuarioRepository.findById(idUsuarioInexistente)).thenReturn(Optional.empty());

        // When & Then - Debe lanzar excepción
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> carritoService.agregarProductoAlCarrito(idUsuarioInexistente, idProducto, 1L));

        assertEquals("Usuario no encontrado con ID: USER999", exception.getMessage());

        // No debe intentar guardar nada
        verify(carritoRepository, never()).save(any());
    }

    /**
     * TDD FASE RED: Test que motivó validaciones de producto inexistente
     */
    @Test
    void debeLanzarExcepcion_ProductoNoExiste_TDD() {
        // Given - Usuario válido pero producto inexistente
        String idUsuario = "USER123";
        String idProductoInexistente = "PROD999";

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuarioMock));
        when(productoRepository.findById(idProductoInexistente)).thenReturn(Optional.empty());

        // When & Then - Debe lanzar excepción
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> carritoService.agregarProductoAlCarrito(idUsuario, idProductoInexistente, 1L));

        assertEquals("Producto no encontrado con ID: PROD999", exception.getMessage());
    }

    /**
     * TDD FASE GREEN: Test que validó el conteo de items en carrito
     */
    @Test
    void debeContarItemsEnCarritoCorrectamente_TDD() {
        // Given - Usuario con 3 items en carrito
        String idUsuario = "USER123";
        when(carritoRepository.countByIdUsuario_IdUsuario(idUsuario)).thenReturn(3L);

        // When - Contar items
        Long count = carritoService.countByUsuario(idUsuario);

        // Then - Debe retornar cantidad correcta
        assertEquals(3L, count, "Debe retornar el número correcto de items en carrito");
        verify(carritoRepository).countByIdUsuario_IdUsuario(idUsuario);
    }

    /**
     * TDD FASE REFACTOR: Test que guió la eliminación de producto específico
     */
    @Test
    void debeEliminarProductoEspecificoDelCarrito_TDD() {
        // Given - Usuario y producto específico en carrito
        String idUsuario = "USER123";
        String idProducto = "PROD001";

        CarritoTemporal carritoExistente = new CarritoTemporal();
        carritoExistente.setId(1L);
        carritoExistente.setIdUsuario(usuarioMock);
        carritoExistente.setIdProducto(productoMock);

        when(carritoRepository.findByIdUsuario_IdUsuarioAndIdProducto_IdProducto(idUsuario, idProducto))
                .thenReturn(carritoExistente);
 
        when(carritoRepository.existsById(1L)).thenReturn(true);      
        // When - Eliminar producto específico
        carritoService.eliminarProductoDelCarrito(idUsuario, idProducto);

        // Then - Debe eliminar solo ese producto
       verify(carritoRepository).deleteById(1L);
    }
}
