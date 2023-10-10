package com.BackEnd.BackEnd.Repositorios;

import com.BackEnd.BackEnd.Modelos.UsuarioOrganizacionRol;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioOrganizacionRolRepo extends JpaRepository<UsuarioOrganizacionRol, Integer> {
    List<UsuarioOrganizacionRol> findByIdUsuario(int idUsuario);

}
