package com.BackEnd.BackEnd.Repositorios;

import com.BackEnd.BackEnd.Modelos.UsuarioOrganizacionRol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioOrganizacionRolRepo extends JpaRepository<UsuarioOrganizacionRol, Integer> {
}
