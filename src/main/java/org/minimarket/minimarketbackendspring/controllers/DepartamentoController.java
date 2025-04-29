package org.minimarket.minimarketbackendspring.controllers;

import org.minimarket.minimarketbackendspring.entities.Departamento;
import org.minimarket.minimarketbackendspring.services.interfaces.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para manejar operaciones sobre Departamento.
 */
@RestController
@RequestMapping("/api/departamento")
@CrossOrigin(origins = "*")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public ResponseEntity<List<Departamento>> getAllDepartamentos() {
        return ResponseEntity.ok(departamentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departamento> getDepartamentoById(@PathVariable Long id) {
        return ResponseEntity.ok(departamentoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createDepartamento(@RequestBody Departamento departamento) {
        departamentoService.save(departamento);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDepartamento(@PathVariable Long id, @RequestBody Departamento departamento) {
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
