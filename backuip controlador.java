package com.BackEnd.BackEnd.Controladores;
import com.BackEnd.BackEnd.Modelos.Candidato;
import com.BackEnd.BackEnd.Modelos.UsuarioOrganizacionRol;
import com.BackEnd.BackEnd.Modelos.UsuariosVotacion;
import com.BackEnd.BackEnd.Repositorios.CandidatoRepo;
import com.BackEnd.BackEnd.Repositorios.UsuariosVotacionRepo;
import com.BackEnd.BackEnd.Repositorios.UsuarioOrganizacionRolRepo;
import com.BackEnd.BackEnd.Modelos.Votaciones;
import com.BackEnd.BackEnd.Modelos.Votaciones.EstatusVotacion;
import com.BackEnd.BackEnd.Repositorios.VotacionesRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Ajusta el origen según tu frontend
@RequestMapping("/api/votaciones")
public class VotacionesControlador {

    public VotacionesControlador(){
    this.restTemplate = new RestTemplate();}
    private final RestTemplate restTemplate;

    @Autowired
    private VotacionesRepo votacionesRepo;
        @Autowired
    private CandidatoRepo candidatoRepo;
        @Autowired
    private UsuarioOrganizacionRolRepo usuarioOrganizacionRolRepo;
        @Autowired
    private UsuariosVotacionRepo usuariosVotacionRepo;

    @PutMapping("/estatus/{idVotacion}/{idOrganizacion}")
    public ResponseEntity<Votaciones> updateVotacionEstatus(@PathVariable int idVotacion, @PathVariable int idOrganizacion, @RequestBody String estatusString) {
        Optional<Votaciones> votacionOptional = votacionesRepo.findByIdVotacionAndIdOrganizacion(idVotacion, idOrganizacion);
        System.out.println(idVotacion + estatusString + idOrganizacion);
        
        if (!votacionOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
    
        Votaciones votacion = votacionOptional.get();
    
        try {
            EstatusVotacion estatus = EstatusVotacion.valueOf(estatusString);
            votacion.setEstatus(estatus);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(null);
        }
    
        votacionesRepo.save(votacion);
        return ResponseEntity.ok(votacion);
    }
    
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
    votacion = votacionesRepo.save(votacion);

    // 1. Obtener el ID de la organización de la votación
    int organizacionId = votacion.getIdOrganizacion();

    System.out.println(organizacionId);
    // 2. Buscar todos los usuarios que pertenecen a esa organización
    List<UsuarioOrganizacionRol> usuarios = usuarioOrganizacionRolRepo.findByIdOrganizacion(organizacionId);
    System.out.println(usuarios);
    
    // 3. Agregar esos usuarios a UsuariosVotacion
    for (UsuarioOrganizacionRol usuarioOrgRol : usuarios) {
        UsuariosVotacion usuarioVotacion = new UsuariosVotacion();
        Map<String, Object> body = new HashMap<>();
        body.put("id_votante", usuarioOrgRol.getIdUsuario());
        body.put("contrasena", "contrasenaSegura123");  // Asegúrate de obtener o definir la contraseña
        body.put("direccionHash", "0x032d8249feA7f21bbBb6dBF560b4c4d75c125693");  // Asegúrate de obtener o definir la dirección hash

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        // Realizar la solicitud POST
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:3000/agregarVotante", HttpMethod.POST, entity, String.class);

        // Parsear el JSON
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String,String>>(){});

        // Imprimir la respuesta y obtener el transactionHash
        String transactionHash = responseMap.get("transactionHash");
        System.out.println(transactionHash);

        usuarioVotacion.setIdUsuario(usuarioOrgRol.getIdUsuario());
        usuarioVotacion.setIdVotacion(votacion.getIdVotacion());
        usuarioVotacion.setIdBlockchain(1);
        usuarioVotacion.setTransaccionHash(transactionHash);        
        usuariosVotacionRepo.save(usuarioVotacion);
    }
        
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
