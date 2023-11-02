package com.BackEnd.BackEnd.Controladores;
import com.BackEnd.BackEnd.Modelos.Candidato;
import com.BackEnd.BackEnd.Modelos.UsuarioOrganizacionRol;
import com.BackEnd.BackEnd.Modelos.UsuariosVotacion;
import com.BackEnd.BackEnd.Repositorios.CandidatoRepo;
import com.BackEnd.BackEnd.Repositorios.UsuariosVotacionRepo;
import com.BackEnd.BackEnd.Repositorios.UsuarioOrganizacionRolRepo;
import com.BackEnd.BackEnd.Modelos.Votaciones;
import com.BackEnd.BackEnd.Modelos.Votaciones.EstatusVotacion;
import com.BackEnd.BackEnd.Modelos.Votos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.BackEnd.BackEnd.Repositorios.VotacionesRepo;
import com.BackEnd.BackEnd.Repositorios.VotosRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
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
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private VotosRepo votosRepo; 

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
    int idBlockchain = 1;  
    for (UsuarioOrganizacionRol usuarioOrgRol : usuarios) {
        
        UsuariosVotacion usuarioVotacion = new UsuariosVotacion();
        Map<String, Object> body = new HashMap<>();
        body.put("id_votante", usuarioVotacion.getIdUsuario());
        body.put("contrasena", "contrasenaSegura123");  
        body.put("direccionHash", votacion.getTransaccionHash());  

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        // Realizar la solicitud POST
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:3000/agregarVotante", HttpMethod.POST, entity, String.class);

        // Imprimir la respuesta
        System.out.println(response.getBody());
        String responseBody = response.getBody();
        String key = "\"transactionHash\":\"";
        int startIndex = responseBody.indexOf(key) + key.length();
        int endIndex = responseBody.indexOf("\"", startIndex);
        String transactionHash = responseBody.substring(startIndex, endIndex);


        usuarioVotacion.setIdUsuario(usuarioOrgRol.getIdUsuario());
        usuarioVotacion.setIdVotacion(votacion.getIdVotacion());
        usuarioVotacion.setIdBlockchain(idBlockchain);
        usuarioVotacion.setTransaccionHash(transactionHash);        
        usuariosVotacionRepo.save(usuarioVotacion);
        idBlockchain++;
    }
        

    return new ResponseEntity<>(votacion, HttpStatus.OK);
}

    @PostMapping("/votar")
    public ResponseEntity<Map<String, String>> votar(Integer candidato_ID, Integer votante_id, Integer votacion_ID, String direccionHash) {
        // Construye el cuerpo de la solicitud con los parámetros requeridos
        System.out.println(candidato_ID+""+votante_id+""+direccionHash);
        Map<String, Object> body = new HashMap<>();
        body.put("candidato_ID", candidato_ID);
        body.put("votante_id", votante_id);
        body.put("contrasena_votante", "contrasenaSegura123");
        body.put("direccionHash", direccionHash);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        // Realiza la solicitud POST al endpoint '/votar'
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:3000/votar", HttpMethod.POST, entity, String.class);

        // Asumiendo que la respuesta contiene el campo "transactionHash"
        String transactionHash="";
        System.out.println("aaaaaaaaaaaaaa"+response);
        try {
            transactionHash = objectMapper.readTree(response.getBody()).get("transactionHash").asText();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Votos voto = new Votos();
        voto.setIdUsuario(votante_id);
        voto.setIdVotaciones(votacion_ID);
        voto.setTimestamp(LocalDateTime.now());
        voto.setTransaccionHash(transactionHash);
        votosRepo.save(voto);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("transactionHash", transactionHash);
        return ResponseEntity.ok(responseBody);
    }

@GetMapping("/obtenerGanador")
public ResponseEntity<String> obtenerGanador(String direccionHash) {
    String url = "http://localhost:3000/obtenerGanador?direccionHash=" + direccionHash;
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    
    return ResponseEntity.ok(response.getBody());
}
@GetMapping("/porIdVotaciones/{idVotaciones}")
public ResponseEntity<List<Votos>> obtenerVotosPorIdVotaciones(@PathVariable int idVotaciones) {
    List<Votos> votos = votosRepo.findByIdVotaciones(idVotaciones);
    return ResponseEntity.ok(votos);
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
