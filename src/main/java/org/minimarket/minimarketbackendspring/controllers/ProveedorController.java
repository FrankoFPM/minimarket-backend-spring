package org.minimarket.minimarketbackendspring.controllers;

import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.ProveedorDTO;
import org.minimarket.minimarketbackendspring.services.interfaces.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para manejar operaciones sobre Proveedor.
 * TODO: aplicar @Valid y manejo de errores
 */
@RestController
@RequestMapping("/api/proveedor")
@CrossOrigin(origins = "*")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    /**
     * Obtiene una lista de todos los proveedores.
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> getAllProveedores() {
        return ResponseEntity.ok(proveedorService.findAll());
    }

    /**
     * Obtiene un proveedor por su ID.
     *
     * @param id el identificador del proveedor
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> getProveedorById(@PathVariable String id) {
        return ResponseEntity.ok(proveedorService.findById(id));
    }

    /**
     * Busca un proveedor por su nombre.
     *
     * @param nombre el nombre del proveedor
     * @return
     */
    @GetMapping("/proveedor/{nombre}")
    public ResponseEntity<ProveedorDTO> getProveedorByNombre(@PathVariable String nombre) {
        return proveedorService.findByEmail(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Busca un proveedor por su email.
     *
     * @param email el email del proveedor
     * @return
     */
    @GetMapping("/proveedor/{email}")
    public ResponseEntity<ProveedorDTO> getProveedorByEmail(@PathVariable String email) {
        return proveedorService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo proveedor.
     *
     * @param proveedor el objeto ProveedorDTO a crear
     * @return una respuesta HTTP 201 si se crea correctamente
     */
    @PostMapping
    public ResponseEntity<Void> createProveedor(@RequestBody ProveedorDTO proveedor) {
        proveedorService.save(proveedor);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Actualiza un proveedor existente.
     *
     * @param id el identificador del proveedor a actualizar
     * @param usuario el objeto ProveedoroDTO con los datos actualizados
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProveedor(@PathVariable String id, @RequestBody ProveedorDTO proveedor) {
        proveedor.setId(id);
        proveedorService.update(proveedor);
        return ResponseEntity.ok().build();
    }

    /**
     * Elimina un proveedor por su ID.
     *
     * @param id el identificador del proveedor a eliminar
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable String id) {
        proveedorService.delete(id);
        return ResponseEntity.ok().build();
    }

}