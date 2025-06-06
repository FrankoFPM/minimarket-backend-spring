package org.minimarket.minimarketbackendspring.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.minimarket.minimarketbackendspring.dtos.ProveedorDTO;
import org.minimarket.minimarketbackendspring.entities.Proveedor;
import org.minimarket.minimarketbackendspring.repositories.ProveedorRepository;
import org.minimarket.minimarketbackendspring.services.interfaces.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementación del servicio de Proveedor.
 */
@Service
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    /**
     * Convierte una entidad Proveedor a ProveedorDTO.
     *
     * @param pv la entidad Proveedor
     * @return 
     */
    public ProveedorDTO convertToDTO(Proveedor pv) {
        return new ProveedorDTO(
                pv.getIdProveedor(),
                pv.getNombre(),
                pv.getContacto(),
                pv.getTelefono(),
                pv.getDireccion(),
                pv.getEmail(),
                pv.getEstado(),
                null, // createdBy (ajusta si tienes este dato en la entidad)
                null  // updatedBy (ajusta si tienes este dato en la entidad)
        );
    }

    /**
     * Obtiene una lista de todos los proveedores.
     *
     * @return
     */
    @Override
    public List<ProveedorDTO> findAll() {
        List<Proveedor> proveedores = proveedorRepository.findAll();

        return proveedores.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un proveedor por su ID.
     *
     * @param id el identificador del proveedor
     * @return 
     */
    @Override
    public ProveedorDTO findById(String id) {
        return proveedorRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado con ID: " + id));
    }

    /**
     * Busca un proveedor por su nombre.
     *
     * @param nombre el nombre del proveedor
     * @return
     */
    @Override
    public Optional<ProveedorDTO> findByNombre(String nombre) {
        return proveedorRepository.findByNombre(nombre)
                .map(this::convertToDTO);
    }

    /**
     * Busca un proveedor por su email.
     *
     * @param email el email del proveedor
     * @return
     */
    @Override
    public Optional<ProveedorDTO> findByEmail(String email) {
        return proveedorRepository.findByEmail(email)
                .map(this::convertToDTO);
    }

    /**
     * Guarda un nuevo proveedor.
     *
     * @param proveedor el objeto ProveedorDTO a guardar
     */
    @Override
    public void save(ProveedorDTO proveedor) {
        Proveedor pv = new Proveedor();
        
        // Aqui se genera un ID único para nuevos proveedores
        if (proveedor.getId() == null || proveedor.getId().isEmpty()) {
            pv.setIdProveedor(UUID.randomUUID().toString());
        } else {
            pv.setIdProveedor(proveedor.getId());
        }
        
        pv.setNombre(proveedor.getNombre());
        pv.setContacto(proveedor.getContacto());
        pv.setTelefono(proveedor.getTelefono());
        pv.setDireccion(proveedor.getDireccion());
        pv.setEmail(proveedor.getEmail());
        pv.setEstado("activo"); 

        proveedorRepository.save(pv);
    }

    /**
     * Actualiza un proveedor existente.
     *
     * @param proveedor
     */
    @Override
    public void update(ProveedorDTO proveedor) {
        Proveedor pv = proveedorRepository.findById(proveedor.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + proveedor.getId()));

        pv.setNombre(proveedor.getNombre());
        pv.setContacto(proveedor.getContacto());
        pv.setTelefono(proveedor.getTelefono());
        pv.setDireccion(proveedor.getDireccion());
        pv.setEmail(proveedor.getEmail());
        pv.setEstado(proveedor.getEstado());

        proveedorRepository.save(pv);
    }

    /**
     * Elimina un proveedor por su ID.
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        if (!proveedorRepository.existsById(id)) {
            throw new EntityNotFoundException("Proveedor no encontrado con ID: " + id);
        }
        proveedorRepository.deleteById(id);
    }
}