package com.BackEnd.BackEnd.Modelos;

import java.text.SimpleDateFormat;
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
@Table(name="Usuarios")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Usuario")
    private int idUsuario;

    @Column(name = "Fecha_Nacimiento")
    private Date fechaNacimiento;

    @Column(name = "Nombre_Completo")
    private String nombreCompleto;

    @Column(name = "Contrasena")
    private String contrasena; 

    @Enumerated(EnumType.STRING)
    @Column(name = "Rol_Sistema")
    private RolSistema rolSistema;

    @Column(name = "ID_Blockchain")
    private String idBlockchain;

    @Column(name = "email", unique = true)
    private String email;

    public enum RolSistema {
        Admin, Usuario
    }
        @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "{" +
            "idUsuario=" + idUsuario +
            ", fechaNacimiento=" + (fechaNacimiento != null ? sdf.format(fechaNacimiento) : "null") +
            ", nombreCompleto='" + nombreCompleto + '\'' +
            ", contrasena='" + contrasena + '\'' +
            ", rolSistema=" + rolSistema +
            ", idBlockchain='" + idBlockchain + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}

