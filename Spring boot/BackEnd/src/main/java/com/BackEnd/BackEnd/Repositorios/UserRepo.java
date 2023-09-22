package com.BackEnd.BackEnd.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BackEnd.BackEnd.Modelos.Usuario;

@Repository
public interface UserRepo extends JpaRepository<Usuario,String>  {

    Usuario findByEmail(String email);



    
}
