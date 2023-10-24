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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios_votacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UsuariosVotacionId.class)
public class UsuariosVotacion  {

    @Id
    @Column(name = "id_Usuario")
    private int idUsuario;

    @Id
    @Column(name = "id_Votacion")
    private int idVotacion;

    @Column(name = "id_blockchain")
    private int idBlockchain;

    @Column(name = "transaccion_hash")
    private String transaccionHash;

}