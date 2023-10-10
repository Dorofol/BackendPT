package com.BackEnd.BackEnd.Controladores;

import com.BackEnd.BackEnd.Modelos.Organizacion;
import com.BackEnd.BackEnd.Modelos.OrganizacionResponseDTO;
import com.BackEnd.BackEnd.Modelos.UsuarioOrganizacionRol;
import com.BackEnd.BackEnd.Repositorios.OrganizacionRepo;
import com.BackEnd.BackEnd.Repositorios.UsuarioOrganizacionRolRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/organizaciones")
public class OrganizacionControlador {
    @Autowired
    private UsuarioOrganizacionRolRepo repo;

    @Autowired
    private OrganizacionRepo organizacionRepository;
    @GetMapping("/obtenerPorIdUsuario/{id}")
 public ResponseEntity<List<OrganizacionResponseDTO>> obtenerPorIdUsuario(@PathVariable Integer id) {
    
    List<UsuarioOrganizacionRol> usuarioOrganizacionRols = repo.findByIdUsuario(id);
    
    List<OrganizacionResponseDTO> responseDTOs = new ArrayList<>();
    
    for(UsuarioOrganizacionRol usuarioOrganizacionRol : usuarioOrganizacionRols) {
        
        Optional<Organizacion> optionalOrganizacion = 
                organizacionRepository.findById(usuarioOrganizacionRol.getIdOrganizacion());
        
        if(optionalOrganizacion.isPresent()) {
            Organizacion organizacion = optionalOrganizacion.get();
            
            OrganizacionResponseDTO dto = new OrganizacionResponseDTO();
            dto.setIdOrganizacion(organizacion.getIdOrganizacion());
            dto.setNombreOrganizacion(organizacion.getNombreOrganizacion());
            dto.setDescripcion(organizacion.getDescripcion());
            dto.setFechaCreacion(organizacion.getFechaCreacion());
            dto.setRol(usuarioOrganizacionRol.getRol());  // estableciendo el rol
            
            responseDTOs.add(dto);
        }
    }
    
    return ResponseEntity.ok(responseDTOs);
}

    


    public OrganizacionResponseDTO mapToDTO(Organizacion organizacion) {
        OrganizacionResponseDTO dto = new OrganizacionResponseDTO();
        dto.setIdOrganizacion(organizacion.getIdOrganizacion());
        dto.setNombreOrganizacion(organizacion.getNombreOrganizacion());
        dto.setDescripcion(organizacion.getDescripcion());
        dto.setFechaCreacion(organizacion.getFechaCreacion());
        return dto;
    }
    @GetMapping
    public List<Organizacion> getAllOrganizaciones() {
        return organizacionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organizacion> getOrganizacionById(@PathVariable Integer id) {
        Optional<Organizacion> organizacion = organizacionRepository.findById(id);

        if (organizacion.isPresent()) {
            return ResponseEntity.ok(organizacion.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/crearOrganizacion")
    public Organizacion crearOrganizacion(@RequestBody Organizacion organizacion) {
        organizacion.setFechaCreacion(new Date());
        return organizacionRepository.save(organizacion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarOrganizacion(@PathVariable Integer id) {
        if (organizacionRepository.existsById(id)) {
            organizacionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
