package com.BackEnd.BackEnd.Controladores;

import com.BackEnd.BackEnd.Modelos.Organizacion;
import com.BackEnd.BackEnd.Modelos.OrganizacionResponseDTO;
import com.BackEnd.BackEnd.Modelos.Usuario;
import com.BackEnd.BackEnd.Modelos.UsuarioOrganizacionRol;
import com.BackEnd.BackEnd.Repositorios.OrganizacionRepo;
import com.BackEnd.BackEnd.Repositorios.UserRepo;
import com.BackEnd.BackEnd.Repositorios.UsuarioOrganizacionRolRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private UserRepo userRepo;

@GetMapping("/porOrganizacion/{idOrganizacion}")
public List<Map<String, String>> getEmailsByOrganizacion(@PathVariable int idOrganizacion) {
    List<UsuarioOrganizacionRol> usuariosOrganizacion = repo.findByIdOrganizacion(idOrganizacion);
    
    return usuariosOrganizacion.stream()
            .map(rol -> {
                Map<String, String> emailAndRole = new HashMap<>();
                Usuario user = userRepo.findById(rol.getIdUsuario()).orElse(new Usuario());
                emailAndRole.put("email", user.getEmail());
                emailAndRole.put("rol", rol.getRol());
                return emailAndRole;
            })
            .collect(Collectors.toList());
}


@PutMapping("/editarOrg/{id}")
public ResponseEntity<?> actualizarOrganizacion(@PathVariable Integer id, 
                                               @RequestBody Organizacion organizacionActualizada,
                                               @RequestParam String contrasenaAdmin) {
    return organizacionRepository.findById(id).map(organizacion -> {
        if (!organizacion.getContrasenaAdministrador().equals(contrasenaAdmin)) {
            return new ResponseEntity<>("Contraseña de administrador incorrecta", HttpStatus.FORBIDDEN);
        }
        organizacion.setNombreOrganizacion(organizacionActualizada.getNombreOrganizacion());
        organizacion.setDescripcion(organizacionActualizada.getDescripcion());
        organizacionRepository.save(organizacion);
        return new ResponseEntity<>(organizacion, HttpStatus.OK);
    }).orElse(new ResponseEntity<>("Organización no encontrada", HttpStatus.NOT_FOUND));
}


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

@PostMapping("/agregarUsuarioOrganizacion")
public ResponseEntity<Map<String, String>> agregarUsuarioOrganizacion(@RequestParam("idOrganizacion") Integer idOrganizacion, @RequestParam("idUsuario") Integer idUsuario, @RequestParam("rol") String rol) {
    System.out.println("asdasdasdasdasdsad");
    UsuarioOrganizacionRol usuarioOrgRol = new UsuarioOrganizacionRol();
    usuarioOrgRol.setIdUsuario(idUsuario);
    usuarioOrgRol.setIdOrganizacion(idOrganizacion);
    usuarioOrgRol.setRol(rol);

    repo.save(usuarioOrgRol);
    
    Map<String, String> response = new HashMap<>();
    response.put("message", "Usuario agregado a la organización con éxito");

    return ResponseEntity.ok(response);
}
@DeleteMapping("/eliminarUsuarioOrganizacion")
public ResponseEntity<Map<String, String>> eliminarUsuarioOrganizaciones(@RequestParam("idOrganizacion") Integer idOrganizacion, @RequestParam("idUsuario") Integer idUsuario) {
    // Busca la relación en la base de datos
    List<UsuarioOrganizacionRol> relaciones = repo.findByIdOrganizacionAndIdUsuario(idOrganizacion, idUsuario);

    if (relaciones.isEmpty()) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "No se encontró la relación entre el usuario y la organización");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Elimina todas las coincidencias (aunque debería ser solo una)
    for (UsuarioOrganizacionRol relacion : relaciones) {
        repo.delete(relacion);
    }

    Map<String, String> response = new HashMap<>();
    response.put("message", "Usuario eliminado de la organización con éxito");

    return ResponseEntity.ok(response);
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
    public Organizacion crearOrganizacion(@RequestBody Organizacion organizacion, @RequestParam("idUsuario") Integer idUsuario) {
        organizacion.setFechaCreacion(new Date());
        Organizacion orgGuardada = organizacionRepository.save(organizacion);

        UsuarioOrganizacionRol usuarioOrgRol = new UsuarioOrganizacionRol();
        usuarioOrgRol.setIdUsuario(idUsuario);
        usuarioOrgRol.setIdOrganizacion(orgGuardada.getId());
        usuarioOrgRol.setRol("Administrador");
    
        repo.save(usuarioOrgRol);
    
        return orgGuardada;
    }

    @PutMapping("/editarOrganizacion/{id}")
    public ResponseEntity<?> editarOrganizacion(@PathVariable(value = "id") Integer idOrganizacion,
                                                           @RequestBody Organizacion detallesOrganizacion) {
        return organizacionRepository.findById(idOrganizacion).map(organizacion -> {
            organizacion.setNombreOrganizacion(detallesOrganizacion.getNombreOrganizacion());
            organizacion.setDescripcion(detallesOrganizacion.getDescripcion());
            organizacion.setContrasenaAdministrador(detallesOrganizacion.getContrasenaAdministrador());
            Organizacion organizacionActualizada = organizacionRepository.save(organizacion);
            return ResponseEntity.ok(organizacionActualizada);
        }).orElse(ResponseEntity.notFound().build());
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
