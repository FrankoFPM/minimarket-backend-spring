package org.minimarket.minimarketbackendspring.services.impl;

import java.util.List;
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
     * Obtiene una lista de todos los proveedores
     *
     * @return una lista de objetos de tipo Proveedor
     */
    @Override
    public List<ProveedorDTO> findAll() {
        List<Proveedor> proveedores = proveedorRepository.findAll();

        return proveedores.stream()
                .map(pv -> new ProveedorDTO(
                        pv.getId(),
                        pv.getNombre(),
                        pv.getContacto(),
                        pv.getTelefono(),
                        pv.getDireccion(),
                        pv.getEmail(),
                        pv.getEstado(),
                        pv.getCreatedBy(),
                        pv.getUpdateBy(),
                        pv.getCreatedAt(),
                        pv.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Busca un proveedor por su ID
     *
     * @param id el identificador del proveedor
     * @return el proveedor encontrado o null si no existe
     */
    @Override
    public ProveedorDTO findById(Long id) {
        return proveedorRepository.findById(id)
                .map(pv -> new ProveedorDTO(
                        pv.getId(),
                        pv.getNombre(),
                        pv.getContacto(),
                        pv.getTelefono(),
                        pv.getDireccion(),
                        pv.getEmail(),
                        pv.getEstado(),
                        pv.getCreatedBy(),
                        pv.getUpdateBy(),
                        pv.getCreatedAt(),
                        pv.getUpdatedAt()
                ))
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado con ID: " + id));
    }

    /**
     * Busca un proveedor por su nombre
     *
     * @param nombre el identificador del proveedor
     * @return el proveedor encontrado o null si no existe
     */
    @Override
    public ProveedorDTO findByNombre(String nombre) {
        return proveedorRepository.findByNombre(nombre)
                .map(pv -> new ProveedorDTO(
                        pv.getId(),
                        pv.getNombre(),
                        pv.getContacto(),
                        pv.getTelefono(),
                        pv.getDireccion(),
                        pv.getEmail(),
                        pv.getEstado(),
                        pv.getCreatedBy(),
                        pv.getUpdateBy(),
                        pv.getCreatedAt(),
                        pv.getUpdatedAt()
                ))
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado con nombre: " + nombre));
    }

    /**
     * Busca un proveedor por su email
     *
     * @param email el identificador del proveedor
     * @return el proveedor encontrado o null si no existe
     */
    @Override
    public ProveedorDTO findByEmail(String email) {
        return proveedorRepository.findByEmail(email)
                .map(pv -> new ProveedorDTO(
                        pv.getId(),
                        pv.getNombre(),
                        pv.getContacto(),
                        pv.getTelefono(),
                        pv.getDireccion(),
                        pv.getEmail(),
                        pv.getEstado(),
                        pv.getCreatedBy(),
                        pv.getUpdateBy(),
                        pv.getCreatedAt(),
                        pv.getUpdatedAt()
                ))
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado con email: " + email));
    }

    /**
     * Obtiene una lista de todos los proveedores de un estado específico.
     *
     * @return una lista de objetos de tipo Proveedor
     */
    @Override
    public List<ProveedorDTO> findByEstado(String estado) {
        return proveedorRepository.findByEstado(estado).stream()
                .map(pv -> new ProveedorDTO(
                        pv.getId(),
                        pv.getNombre(),
                        pv.getContacto(),
                        pv.getTelefono(),
                        pv.getDireccion(),
                        pv.getEmail(),
                        pv.getEstado(),
                        pv.getCreatedBy(),
                        pv.getUpdateBy(),
                        pv.getCreatedAt(),
                        pv.getUpdatedAt()
                        ))
                .collect(Collectors.toList());
    }

    /**
     * Guarda un nuevo proveedor.
     *
     * @param proveedor el objeto Proveedor a guardar
     */
    @Override
    public void save(ProveedorDTO proveedor) {
        Proveedor pv = new Proveedor();
        pv.setNombre(proveedor.getNombre());
        pv.setContacto(proveedor.getContacto());
        pv.setTelefono(proveedor.getTelefono());
        pv.setDireccion(proveedor.getDireccion());
        pv.setEmail(proveedor.getEmail());
        pv.setEstado(proveedor.getEstado());
        pv.setCreatedBy(proveedor.getCreatedBy());
        pv.setUpdateBy(proveedor.getUpdatedBy());
        proveedorRepository.save(pv);
    }

    /**
     * Actualiza un proveedor existente.
     *
     * @param proveedor el objeto Categoria a actualizar
     */
    @Override
    public void update(ProveedorDTO proveedor) {
        Proveedor prov = proveedorRepository.findById(proveedor.getId())
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado con ID: " + proveedor.getId()));
        prov.setNombre(proveedor.getNombre());
        prov.setContacto(proveedor.getContacto());
        prov.setTelefono(proveedor.getTelefono());
        prov.setDireccion(proveedor.getDireccion());
        prov.setEmail(proveedor.getEmail());
        prov.setEstado(proveedor.getEstado());
        //TODO: Validar el usuario que actualiza
        prov.setUpdateBy(proveedor.getUpdatedBy());
        proveedorRepository.save(prov);
    }

    /**
     * Elimina un proveedor por su ID.
     *
     * @param id el identificador de la categoria a eliminar
     */
    @Override
    public void delete(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new EntityNotFoundException("Proveedor no encontrado con ID: " + id);
        }
        proveedorRepository.deleteById(id);
    }
}