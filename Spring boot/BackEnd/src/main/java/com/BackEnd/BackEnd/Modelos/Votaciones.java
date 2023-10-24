package com.BackEnd.BackEnd.Modelos;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "votaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(VotacionesId.class)
public class Votaciones {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_Votacion")
    private int idVotacion;

    @Column(name = "Titulo_Votacion")
    private String tituloVotacion;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_Inicio")
    private Date fechaInicio;

    @Column(name = "fecha_Cierre")
    private Date fechaCierre;

    @Id 
    @Column(name = "ID_Organizacion")
    private int idOrganizacion; 

    @Column(name = "transaccion_hash")
    private String transaccionHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus")
    private EstatusVotacion estatus;

    public enum EstatusVotacion {
        Activa,
        Inactiva,
        Finalizada
    }

}
