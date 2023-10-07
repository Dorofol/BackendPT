package com.BackEnd.BackEnd.Modelos;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Usuario_organizacion_rol")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UsuarioOrganizacionRolId.class)
public class UsuarioOrganizacionRol {
    @Id
    @Column(name = "ID_Usuario")
    private int idUsuario;

    @Id
    @Column(name = "ID_Organizacion")
    private int idOrganizacion;

    @Column(name = "Rol")
    private String rol;
}



