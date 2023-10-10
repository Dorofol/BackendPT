package com.BackEnd.BackEnd.Repositorios;
import com.BackEnd.BackEnd.Modelos.Votaciones;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotacionesRepo extends JpaRepository<Votaciones, Integer> {
    List<Votaciones> findByIdOrganizacion(int idOrganizacion);
}