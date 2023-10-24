package com.BackEnd.BackEnd.Controladores;
import com.BackEnd.BackEnd.Modelos.Candidato;
import com.BackEnd.BackEnd.Repositorios.CandidatoRepo;
import com.BackEnd.BackEnd.Modelos.Votaciones;
import com.BackEnd.BackEnd.Repositorios.VotacionesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Ajusta el origen según tu frontend
@RequestMapping("/api/votaciones")
public class VotacionesControlador {
    
    @Autowired
    private VotacionesRepo votacionesRepo;
        @Autowired
    private CandidatoRepo candidatoRepo;

    
    @GetMapping("/porOrganizacion/{idOrganizacion}")
    public List<Votaciones> obtenerVotacionesPorIdOrganizacion(@PathVariable int idOrganizacion) {
        return votacionesRepo.findByIdOrganizacion(idOrganizacion);
    }

        @GetMapping("/votacion/{idVotacion}")
    public ResponseEntity<List<Candidato>> getCandidatosByIdVotacion(@PathVariable int idVotacion) {
        List<Candidato> candidatos = candidatoRepo.findByIdVotacion(idVotacion);
        return new ResponseEntity<>(candidatos, HttpStatus.OK);
    }

    @PostMapping("/agregar")
    public ResponseEntity<Votaciones> agregarVotacion(@RequestBody Votaciones votacion) {
        System.out.println(votacion);
         votacionesRepo.save(votacion);

        return new ResponseEntity<>(votacion, HttpStatus.OK);
    }
    @PostMapping("/agregarCandidato")
    public ResponseEntity<Candidato> agregarCandidato(@RequestBody Candidato candidato) {
        try {
            Candidato nuevoCandidato = candidatoRepo.save(candidato);
            return new ResponseEntity<>(nuevoCandidato, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
