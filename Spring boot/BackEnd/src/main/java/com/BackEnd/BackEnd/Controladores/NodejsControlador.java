package com.BackEnd.BackEnd.Controladores;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/proxy")
public class NodejsControlador {

    private final String NODE_API_URL = "http://localhost:3000";
    private final RestTemplate restTemplate;

    public NodejsControlador() {
        this.restTemplate = new RestTemplate();
    }
    @GetMapping("/proxyGet/{endpoint}")
    public ResponseEntity<String> proxyGet(@PathVariable String endpoint) {
        ResponseEntity<String> response = restTemplate.getForEntity(NODE_API_URL + "/" + endpoint, String.class);
        
        // Construir encabezados de respuesta
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        
        // Retorna la respuesta obtenida de la API Node.js
        return new ResponseEntity<>(response.getBody(), responseHeaders, response.getStatusCode());
    }
    @PostMapping("/proxyPost/{endpoint}")
    public ResponseEntity<String> proxyPost(@PathVariable String endpoint, @RequestBody Map<String, Object> payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
    
        // Llamada a la API Node.js
        ResponseEntity<String> response = restTemplate.postForEntity(NODE_API_URL + "/" + endpoint, entity, String.class);
    
        // Construir encabezados de respuesta
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
    
        // Retorna la respuesta obtenida de la API Node.js
        return new ResponseEntity<>(response.getBody(), responseHeaders, response.getStatusCode());
    }
    

}
