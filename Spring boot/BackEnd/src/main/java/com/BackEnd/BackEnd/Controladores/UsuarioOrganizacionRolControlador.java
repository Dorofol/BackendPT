package com.BackEnd.BackEnd.Controladores;

import com.BackEnd.BackEnd.Modelos.UsuarioOrganizacionRol;
import com.BackEnd.BackEnd.Repositorios.UsuarioOrganizacionRolRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/organizacionesUsuarios")
public class UsuarioOrganizacionRolControlador {

    @Autowired
    private UsuarioOrganizacionRolRepo usuarioOrganizacionRolRepo; 

    @GetMapping
    public List<UsuarioOrganizacionRol> getAllOrganizaciones() {
        return usuarioOrganizacionRolRepo.findAll();
    }



    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuarioOrganizacion(@RequestBody UsuarioOrganizacionRol usuarioOrganizacionRolRequest) {
        System.out.println(usuarioOrganizacionRolRequest);
        try {
            UsuarioOrganizacionRol result = usuarioOrganizacionRolRepo.save(usuarioOrganizacionRolRequest);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
