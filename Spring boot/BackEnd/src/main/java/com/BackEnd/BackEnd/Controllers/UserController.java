package com.BackEnd.BackEnd.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BackEnd.BackEnd.Model.User;
import com.BackEnd.BackEnd.Repositories.UserRepo;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    
    @Autowired
    private UserRepo repo;
	

    @GetMapping("/listarusuarios")
    public List<User> obtenerUsuarios()   
{  
    
    List<User> usuarios = new ArrayList<User>();  
    repo.findAll().forEach(books1 -> usuarios.add(books1));  
    return usuarios;  
}  
/*     @GetMapping("/prueba")
    public ResponseEntity<User> getAlgo(){
        User users = new User();

        return ResponseEntity.ok( users );
    }   */ 
    
    @PostMapping("/crearUsuario")  
    private ResponseEntity<?> crearusuario(@RequestBody User user)   
    {  
        try {
            System.out.println("Funcionando 2, Data del usuario recibido de mysql:"+ user);
            repo.save(user);  
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error.");
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error.");
        }

    }
    //borrarusuario .deleteById(id);  






    @GetMapping("/getuser/{id}")
    public ResponseEntity<?> getuser(@PathVariable("id") String id){
        System.out.println("Funcionando, Data del usuario recibido de angular:");
        
        User user= repo.findByUserId(id);
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
    private void deleteBook(@PathVariable("id") String id)   
    {  
    repo.deleteById(id);  
    }  
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User userData){
        System.out.println("Funcionando, Data del usuario recibido de angular:"+ userData);
        
         User user= repo.findByUserId(userData.getUserId());
         if(user!=null){
            System.out.println("Funcionando 2, Data del usuario recibido de mysql:"+ user);
         if(user.getPassword().equals(userData.getPassword())){
            return ResponseEntity.ok(user);
         }}
        //return (ResponseEntity<?>) ResponseEntity.internalServerError() ;
        System.out.println("No se encontro el usuario o fallo el inicio de sesion "+ userData);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
    }
}
