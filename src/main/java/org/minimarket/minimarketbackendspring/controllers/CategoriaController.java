package org.minimarket.minimarketbackendspring.controllers;

import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.CategoriaDTO;
import org.minimarket.minimarketbackendspring.services.interfaces.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Controlador REST para manejar operaciones sobre Categorías.
 * TODO: aplicar @Valid y manejo de errores
 *
 */
@RestController
@RequestMapping("/api/categoria")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/categoria/{estado}")
    public ResponseEntity<List<CategoriaDTO>> getActiveCategorias(@PathVariable String estado) {
        return ResponseEntity.ok(categoriaService.findByEstado(estado));
    }

    /**
     * Obtiene una categoría por su ID.
     *
     * @param id el identificador de la categoría
     * @return la categoría encontrada o un error 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getCategoriaById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findById(id));

    }

    @PostMapping
    public ResponseEntity<Void> createCategoria(@RequestBody CategoriaDTO categoria) {
        categoriaService.save(categoria);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoria) {
        categoria.setId(id);
        categoriaService.update(categoria);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.ok().build();
    }

}
