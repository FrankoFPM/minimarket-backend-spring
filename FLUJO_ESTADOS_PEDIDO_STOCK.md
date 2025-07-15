# Flujo de Estados de Pedido y Actualización de Stock

## Estados del Pedido y Gestión de Stock

### 📋 Estados de Pedido

1. **`solicitado`** - Pedido creado, carrito convertido a pedido
2. **`pendiente_pago`** - Pedido listo para pagar
3. **`pagado`** - **🔥 AQUÍ SE ACTUALIZA EL STOCK**
4. **`completado`** - Pedido entregado/finalizado
5. **`cancelado`** - Pedido cancelado

### 🔄 Flujo de Actualización de Stock

#### **Crear Pedido (Estado: `solicitado`)**

```
1. Validar stock disponible
2. Si no hay stock → Limpiar carrito + Error
3. Si hay stock → Crear pedido sin afectar stock
4. Vaciar carrito temporal
```

#### **Cambiar a Estado `pagado`**

```
1. Validar stock nuevamente (por si cambió)
2. Si no hay stock → Error, no cambiar estado
3. Si hay stock → Actualizar stock en BD
4. Limpiar productos agotados de todos los carritos
5. Cambiar estado a "pagado"
```

#### **Cambiar a Estado `completado`**

```
1. Solo permitir desde estado "pagado"
2. NO afectar stock (ya fue descontado)
3. Cambiar estado a "completado"
```

#### **Cambiar a Estado `cancelado`**

```
1. Si está en "solicitado" o "pendiente_pago" → No afectar stock
2. Si está en "pagado" → Devolver stock a BD
3. Cambiar estado a "cancelado"
```

## 🎯 Métodos Implementados

### **PedidoServiceImpl**

#### `crearPedidoDesdeCarrito()`

- ✅ Valida stock antes de crear pedido
- ✅ Limpia carrito si hay productos sin stock
- ✅ Crea pedido sin afectar stock
- ❌ **NO actualiza stock** (solo en pago)

#### `cambiarEstado()`

- ✅ Valida transiciones de estado
- ✅ Valida stock antes de marcar como "pagado"
- ✅ Actualiza stock cuando cambia a "pagado"
- ✅ Transaccional - revierte todo si falla

#### `cancelarPedido()`

- ✅ Permite cancelar pedidos no finalizados
- ✅ Devuelve stock si el pedido estaba "pagado"
- ✅ Registra quien canceló el pedido

#### `completarPedido()`

- ✅ Solo permite completar pedidos "pagados"
- ❌ **NO afecta stock** (ya fue descontado)
- ✅ Cambia estado a "completado"

### **Métodos Privados de Soporte**

#### `validarStockAntesDePago()`

- Valida que hay stock suficiente antes de pagar
- Lanza excepción si no hay stock

#### `actualizarStockPorPagoDePedido()`

- Descuenta stock de todos los productos del pedido
- Limpia carritos de productos agotados
- Transaccional - revierte si falla

#### `devolverStockPorCancelacion()`

- Devuelve stock cuando se cancela pedido "pagado"
- Maneja errores sin fallar la cancelación

#### `devolverStockProducto()`

- Suma stock de vuelta al producto
- Método auxiliar para cancelaciones

## 🔧 Validaciones de Seguridad

### **Transiciones de Estado Válidas**

```
solicitado → pendiente_pago, cancelado
pendiente_pago → pagado, cancelado
pagado → completado, cancelado
completado → (final, no se puede cambiar)
cancelado → (final, no se puede cambiar)
```

### **Validaciones de Stock**

1. **Al crear pedido**: Validar stock disponible
2. **Al marcar como pagado**: Validar stock nuevamente
3. **Al cancelar pedido pagado**: Devolver stock

### **Manejo de Errores**

- Stock insuficiente → IllegalStateException
- Transición inválida → IllegalArgumentException
- Producto no encontrado → EntityNotFoundException
- Todas las operaciones son transaccionales

## 📊 Casos de Uso

### **Caso 1: Flujo Normal**

```
1. Usuario crea pedido → "solicitado" (stock no afectado)
2. Usuario procede al pago → "pendiente_pago" (stock no afectado)
3. Pago exitoso → "pagado" (stock actualizado ✅)
4. Pedido entregado → "completado" (stock no afectado)
```

### **Caso 2: Cancelación Después de Pago**

```
1. Pedido está "pagado" (stock ya descontado)
2. Usuario cancela → "cancelado" (stock devuelto ✅)
```

### **Caso 3: Stock Insuficiente en Pago**

```
1. Pedido está "pendiente_pago"
2. Otro usuario compra el último producto
3. Intento de pago → Error por stock insuficiente
4. Pedido permanece "pendiente_pago"
```

### **Caso 4: Producto Se Agota Durante Pago**

```
1. Pedido se marca como "pagado"
2. Stock se actualiza → Producto se agota
3. Todos los carritos se limpian automáticamente
4. Otros usuarios ven carrito actualizado
```

## 🚀 Endpoints Modificados

### **Cambiar Estado de Pedido**

```
PUT /api/pedidos/{id}/estado
Body: {"estado": "pagado"}
```

- Valida stock antes de cambiar
- Actualiza stock si cambia a "pagado"
- Devuelve stock si se cancela pedido "pagado"

### **Completar Pedido**

```
PUT /api/pedidos/{id}/completar
```

- Solo permite completar pedidos "pagados"
- No afecta stock

### **Cancelar Pedido**

```
PUT /api/pedidos/{id}/cancelar
```

- Devuelve stock si el pedido estaba "pagado"
- Permite cancelar pedidos no finalizados

## ⚡ Características Clave

1. **Stock Real**: El stock se actualiza solo cuando hay venta confirmada
2. **Transaccional**: Todas las operaciones críticas son transaccionales
3. **Validación Doble**: Stock se valida al crear pedido y al pagar
4. **Limpieza Automática**: Carritos se limpian cuando productos se agotan
5. **Recuperación**: Stock se devuelve si se cancela pedido pagado
6. **Auditoria**: Se registra quien hace cada cambio de estado

Este sistema garantiza que el stock mostrado al usuario siempre refleje la disponibilidad real y que las ventas se procesen de manera consistente y segura.
