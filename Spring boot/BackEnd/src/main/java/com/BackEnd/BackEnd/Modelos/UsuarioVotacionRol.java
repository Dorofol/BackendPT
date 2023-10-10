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
@Table(name = "usuario_votacion_rol")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UsuarioVotacionRolId.class)
public class UsuarioVotacionRol {

    @Id
    @Column(name = "ID_Usuario")
    private int idUsuario;

    @Id
    @Column(name = "ID_Votacion")
    private int idVotacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "Rol")
    private Rol rol;

    public enum Rol {
        Admin, Usuario, Modificar, Lectura
    }
}