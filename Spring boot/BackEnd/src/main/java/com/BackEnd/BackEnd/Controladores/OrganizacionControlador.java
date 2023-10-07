package com.BackEnd.BackEnd.Controladores;

import com.BackEnd.BackEnd.Modelos.Organizacion;
import com.BackEnd.BackEnd.Repositorios.OrganizacionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/organizaciones")
public class OrganizacionControlador {

    @Autowired
    private OrganizacionRepo organizacionRepository;

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
