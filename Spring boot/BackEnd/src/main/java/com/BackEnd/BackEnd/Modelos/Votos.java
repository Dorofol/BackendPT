package com.BackEnd.BackEnd.Modelos;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "votos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(VotosId.class)
public class Votos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Voto")
    private int idVoto;
    @Id
    @Column(name = "ID_Usuario")
    private int idUsuario;
    @Id
    @Column(name = "ID_Candidato")
    private int idCandidato;

    @Column(name = "Timestamp")
    private LocalDateTime timestamp;

    @Column(name = "Transaccion_Hash", length = 66)
    private String transaccionHash;

}