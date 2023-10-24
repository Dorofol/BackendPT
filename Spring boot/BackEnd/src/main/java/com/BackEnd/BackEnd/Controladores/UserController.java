package com.BackEnd.BackEnd.Controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.bouncycastle.jcajce.BCFKSLoadStoreParameter.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;

import com.BackEnd.BackEnd.Modelos.Usuario;
import com.BackEnd.BackEnd.Modelos.Usuario.RolSistema;
import com.BackEnd.BackEnd.Repositorios.UserRepo;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    
    @Autowired
    private UserRepo repo;

	private final String SECRET_KEY = "firmadellavesecretaunica"; // Es recomendable tener esto en un archivo de configuración, no directamente en el código


    @GetMapping("/listarusuarios")
    public List<Usuario> obtenerUsuarios()   
{  
    
    List<Usuario> usuarios = new ArrayList<Usuario>();  
    repo.findAll().forEach(books1 -> usuarios.add(books1));  
    return usuarios;  
}  
@PutMapping("/updateUsuario/{id}")
public ResponseEntity<?> updateUsuario(@PathVariable int id, @RequestBody Usuario updatedUserData) {
    try {
        Usuario existingUser = repo.findById(id).orElse(null);
        
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
        
        // Aquí puedes actualizar todos los campos que necesites del usuario.
        existingUser.setNombreCompleto(updatedUserData.getNombreCompleto());
        existingUser.setEmail(updatedUserData.getEmail());
        existingUser.setContrasena(updatedUserData.getContrasena()); 
        existingUser.setIdBlockchain(updatedUserData.getIdBlockchain()); 
        existingUser.setFechaNacimiento(updatedUserData.getFechaNacimiento()); 
        repo.save(existingUser);  
        return ResponseEntity.ok(existingUser);
        
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar usuario: " + e.getMessage());
    }
}
    
    @PostMapping("/crearUsuario")  
    private ResponseEntity<?> crearusuario(@RequestBody Usuario user)   {
        try {
            System.out.println("Funcionando 2, Data del usuario recibido de mysql:"+ user);
            user.setRolSistema(RolSistema.Usuario);
            user.setIdBlockchain("0xpoq9012jhgnuasdb8b2bewqed");
            repo.save(user);  
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error.");
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error.");
        }

    }
    //borrarusuario .deleteById(id);  






   @GetMapping("/getUsuario/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        System.out.println("Obteniendo usuario. ID recibido: " + id);
        
        Usuario optionalUser = repo.findByIdUsuario(id);
        if (optionalUser != null) {
            System.out.println("Usuario encontrado en la base de datos: " + optionalUser);
            System.out.println(optionalUser.toString());
                Map<String, String> usuarioresp = new HashMap<>();
                usuarioresp.put("usuario", optionalUser.toString());
            return ResponseEntity.ok(usuarioresp);
        } else {
            System.out.println("No se encontró el usuario con el ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con el ID: " + id);
        }
    }
    
   @GetMapping("/getUsuarioEmail/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        System.out.println("Obteniendo usuario. Email recibido: " + email);
        
        Usuario optionalUser = repo.findByEmail(email); // Asume que tienes este método en el repositorio
        if (optionalUser != null) {
            System.out.println("Usuario encontrado en la base de datos: " + optionalUser);
            System.out.println(optionalUser.toString());
            Map<String, String> usuarioresp = new HashMap<>();
            usuarioresp.put("id", optionalUser.getIdUsuario()+"");
            return ResponseEntity.ok(usuarioresp);
        } else {
            System.out.println("No se encontró el usuario con el email: " + email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con el email: " + email);
        }
    }

    //borrar usuario recordar que se tiene que mandar como delete no como get o post
    @DeleteMapping("/borrarUsuario/{id}")  
    private void deleteUsuarioPorId(@PathVariable int id)   
    {  
    repo.deleteById(id);  
    }  
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Usuario userData){
        System.out.println("Funcionando, Data del usuario recibido de angular:"+ userData);
        
         Usuario user= repo.findByEmail(userData.getEmail());
         if(user!=null){
            System.out.println("Funcionando 2, Data del usuario recibido de mysql:"+ user);
         if(user.getContrasena().equals(userData.getContrasena())){
            return ResponseEntity.ok(user);
         }}
        //return (ResponseEntity<?>) ResponseEntity.internalServerError() ;
        System.out.println("No se encontro el usuario o fallo el inicio de sesion "+ userData);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
    }
    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
            try {
                if (token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
                System.out.println(claims);
                Map<String, String> tokenResponse = new HashMap<>();
                tokenResponse.put("mensaje", "Token válido"+token.toString());
                return ResponseEntity.ok(tokenResponse);

            } catch (Exception e) {
                System.out.println("no valido"+e.toString());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no válido");
            }
        }
    @PostMapping("/loginCookie")
    public ResponseEntity<?> loginCookie(@RequestBody Usuario userData) {
        // ... (autenticación)
        // System.out.println(userData);
         Usuario user= repo.findByEmail(userData.getEmail());
         //System.out.println(user);
        if (user.getContrasena().equals(userData.getContrasena())) {
            String token = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("idUsuario", user.getIdUsuario())
                .claim("fechaNacimiento", user.getFechaNacimiento().toString())
                .claim("nombreCompleto", user.getNombreCompleto())
                .claim("rolSistema", user.getRolSistema())
                .claim("idBlockchain", user.getIdBlockchain())
                .claim("email", user.getEmail())
                .signWith(SignatureAlgorithm.HS512, "firmadellavesecretaunica")
                .compact();
                Map<String, String> tokenResponse = new HashMap<>();
                tokenResponse.put("token", token);
                //tokenResponse.put("user", user.toString());
                return ResponseEntity.ok(tokenResponse);
            //return ResponseEntity.ok(token.toString());  // Envía el JWT al cliente
        }
        
            System.out.println(user);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        // ...
    }
}
