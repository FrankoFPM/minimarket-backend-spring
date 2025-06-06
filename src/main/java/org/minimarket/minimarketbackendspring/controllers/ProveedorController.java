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

    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> getAllProveedores() {
        return ResponseEntity.ok(proveedorService.findAll());
    }

    @GetMapping("/proveedor/{estado}")
    public ResponseEntity<List<ProveedorDTO>> getActiveProveedores(@PathVariable String estado) {
        return ResponseEntity.ok(proveedorService.findByEstado(estado));
    }

    /**
     * Obtiene un proveedor por su ID.
     *
     * @param id el identificador del proveedor
     * @return el proveedor encontrado o un error 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> getProveedorById(@PathVariable Long id) {
        return ResponseEntity.ok(proveedorService.findById(id));

    }

    /**
     * Obtiene un proveedor por su nombre.
     *
     * @param nombre el nombre del proveedor
     * @return el proveedor encontrado o un error 404 si no existe
     */
    @GetMapping("/{nombre}")
    public ResponseEntity<ProveedorDTO> getProveedorByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(proveedorService.findByNombre(nombre));

    }

    /**
     * Obtiene un proveedor por su email.
     *
     * @param email el email del proveedor
     * @return el proveedor encontrado o un error 404 si no existe
     */
    @GetMapping("/{email}")
    public ResponseEntity<ProveedorDTO> getProveedorByEmail(@PathVariable String email) {
        return ResponseEntity.ok(proveedorService.findByEmail(email));

    }

    @PostMapping
    public ResponseEntity<Void> createProveedor(@RequestBody ProveedorDTO proveedor) {
        proveedorService.save(proveedor);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProveedor(@PathVariable Long id, @RequestBody ProveedorDTO proveedor) {
        proveedor.setId(id);
        proveedorService.update(proveedor);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        proveedorService.delete(id);
        return ResponseEntity.ok().build();
    }

}