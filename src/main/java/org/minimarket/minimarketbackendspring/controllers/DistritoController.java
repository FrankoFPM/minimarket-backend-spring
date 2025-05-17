package org.minimarket.minimarketbackendspring.controllers;

import org.minimarket.minimarketbackendspring.dtos.DistritoDTO;
import org.minimarket.minimarketbackendspring.services.interfaces.DistritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para manejar operaciones sobre Distrito.
 * TODO: aplicar @Valid y manejo de errores
 */
@RestController
@RequestMapping("/api/distrito")
@CrossOrigin(origins = "*")
public class DistritoController {

    @Autowired
    private DistritoService distritoService;

    /**
     * Obtiene una lista de todos los distritos.
     *
     * @return una lista de objetos DistritoDTO
     */
    @GetMapping
    public ResponseEntity<List<DistritoDTO>> getAllDistritos() {
        return ResponseEntity.ok(distritoService.findAll());
    }

    /**
     * Obtiene una lista de distritos asociados a un departamento específico.
     *
     * @param idDepartamento el identificador del departamento
     * @return una lista de objetos DistritoDTO
     */
    @GetMapping("/departamento/{idDepartamento}")
    public ResponseEntity<List<DistritoDTO>> getDistritosByDepartamento(@PathVariable Long idDepartamento) {
        return ResponseEntity.ok(distritoService.findByDepartamentoId(idDepartamento));
    }

    /**
     * Obtiene un distrito por su ID.
     *
     * @param id el identificador del distrito
     * @return el distrito encontrado o un error 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<DistritoDTO> getDistritoById(@PathVariable Long id) {
        return ResponseEntity.ok(distritoService.findById(id));
    }

    /**
     * Crea un nuevo distrito.
     *
     * @param distrito el objeto DistritoDTO a crear
     * @return una respuesta HTTP 200 si se crea correctamente
     */
    @PostMapping
    public ResponseEntity<Void> createDistrito(@RequestBody DistritoDTO distrito) {
        distritoService.save(distrito);
        return ResponseEntity.ok().build();
    }

    /**
     * Actualiza un distrito existente.
     *
     * @param id el identificador del distrito a actualizar
     * @param distrito el objeto DistritoDTO con los datos actualizados
     * @return una respuesta HTTP 200 si se actualiza correctamente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDistrito(@PathVariable Long id, @RequestBody DistritoDTO distrito) {
        distrito.setId(id);
        distritoService.update(distrito);
        return ResponseEntity.ok().build();
    }

    /**
     * Elimina un distrito por su ID.
     *
     * @param id el identificador del distrito a eliminar
     * @return una respuesta HTTP 200 si se elimina correctamente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistrito(@PathVariable Long id) {
        distritoService.delete(id);
        return ResponseEntity.ok().build();
    }
}