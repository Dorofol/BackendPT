package com.BackEnd.BackEnd.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BackEnd.BackEnd.Modelos.Usuario;

@Repository
public interface UserRepo extends JpaRepository<Usuario,Integer>  {
    Usuario findByEmail(String email);
    Usuario findByIdUsuario(int id);
}
