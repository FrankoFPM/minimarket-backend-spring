package org.minimarket.minimarketbackendspring.controllers;

import org.minimarket.minimarketbackendspring.dtos.DepartamentoDTO;
import org.minimarket.minimarketbackendspring.services.interfaces.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * Controlador REST para manejar operaciones sobre Departamento.
 * TODO: aplicar @Valid y manejo de errores
 *
 */
@RestController
@RequestMapping("/api/departamento")
@CrossOrigin(origins = "*")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public ResponseEntity<List<DepartamentoDTO>> getAllDepartamentos() {
        return ResponseEntity.ok(departamentoService.findAll());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<DepartamentoDTO>> getActiveDepartamentos(@PathVariable String estado) {
        return ResponseEntity.ok(departamentoService.findByEstado(estado));
    }

    /**
     * Obtiene un departamento por su ID.
     *
     * @param id el identificador del departamento
     * @return el departamento encontrado o un error 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> getDepartamentoById(@PathVariable Long id) {
        return ResponseEntity.ok(departamentoService.findById(id));

    }

    @PostMapping
    public ResponseEntity<Void> createDepartamento(@RequestBody DepartamentoDTO departamento) {
        departamentoService.save(departamento);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDepartamento(@PathVariable Long id, @RequestBody DepartamentoDTO departamento) {
        departamento.setId(id);
        departamentoService.update(departamento);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamento(@PathVariable Long id) {
        departamentoService.delete(id);
        return ResponseEntity.ok().build();
    }

}
