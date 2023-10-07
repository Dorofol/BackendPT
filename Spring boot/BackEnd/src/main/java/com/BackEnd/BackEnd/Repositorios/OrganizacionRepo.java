package com.BackEnd.BackEnd.Repositorios;


import com.BackEnd.BackEnd.Modelos.Organizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizacionRepo extends JpaRepository<Organizacion, Integer> {
    
}
