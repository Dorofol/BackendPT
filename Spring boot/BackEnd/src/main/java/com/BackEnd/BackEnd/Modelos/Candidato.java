package com.BackEnd.BackEnd.Modelos;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "candidatos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CandidatoId.class)
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Candidato")
    private Integer idCandidato;

    @Column(name = "Nombre_Candidato")
    private String nombreCandidato;

    @Column(name = "Descripcion_Perfil")
    private String descripcionPerfil;

    @Id
    @Column(name = "ID_Votacion")
    private int idVotacion;

    @Column(name = "Transaccion_Hash", length = 66)
    private String transaccionHash;

}