package com.BackEnd.BackEnd.Repositorios;

import com.BackEnd.BackEnd.Modelos.UsuariosVotacion;
import com.BackEnd.BackEnd.Modelos.UsuariosVotacionId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosVotacionRepo extends JpaRepository<UsuariosVotacion, UsuariosVotacionId> {
}
