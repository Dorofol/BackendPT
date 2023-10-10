package com.BackEnd.BackEnd.Controladores;
import com.BackEnd.BackEnd.Modelos.Votaciones;
import com.BackEnd.BackEnd.Repositorios.VotacionesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Ajusta el origen según tu frontend
@RequestMapping("/api/votaciones")
public class VotacionesControlador {
    
    @Autowired
    private VotacionesRepo votacionesRepo;

    @GetMapping("/porOrganizacion/{idOrganizacion}")
    public List<Votaciones> obtenerVotacionesPorIdOrganizacion(@PathVariable int idOrganizacion) {
        return votacionesRepo.findByIdOrganizacion(idOrganizacion);
    }
}
