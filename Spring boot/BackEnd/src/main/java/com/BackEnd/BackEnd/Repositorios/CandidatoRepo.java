package com.BackEnd.BackEnd.Repositorios;
import com.BackEnd.BackEnd.Modelos.Candidato;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatoRepo extends JpaRepository<Candidato, Integer> {
    
    List<Candidato> findByIdVotacion(int idVotacion);
}