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
/*     @GetMapping("/prueba")
    public ResponseEntity<User> getAlgo(){
        User users = new User();

        return ResponseEntity.ok( users );
    }   */ 
    
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






    @GetMapping("/getuser/{id}")
    public ResponseEntity<?> getuser(@PathVariable String id){
        System.out.println("Funcionando, Data del usuario recibido de angular:");
        
        Usuario user= repo.findByEmail(id);
         if(user!=null){
            System.out.println("Funcionando 2, Data del usuario recibido de mysql:"+ user);
            return ResponseEntity.ok(user);
         }
        //return (ResponseEntity<?>) ResponseEntity.internalServerError() ;
        System.out.println("No se encontro el usuario o fallo el inicio de sesion ");
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
    }

    //borrar usuario recordar que se tiene que mandar como delete no como get o post
    @DeleteMapping("/Usuario/{id}")  
    private void deleteBook(@PathVariable String id)   
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
                // Quitar "Bearer " si el token viene de esa forma
                if (token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

                // Si llega hasta aquí, el token es válido
                System.out.println(claims);
                return ResponseEntity.ok("Token válido!");

            } catch (Exception e) {
                // Esto maneja cualquier excepción relacionada con la descompresión del token, como un token expirado o una firma incorrecta.
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
