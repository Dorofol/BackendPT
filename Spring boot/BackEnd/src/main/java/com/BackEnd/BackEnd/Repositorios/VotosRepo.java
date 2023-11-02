package com.BackEnd.BackEnd.Repositorios;

import com.BackEnd.BackEnd.Modelos.Votos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Repository;

@Repository
public interface VotosRepo extends JpaRepository<Votos, Integer> {
    
}
