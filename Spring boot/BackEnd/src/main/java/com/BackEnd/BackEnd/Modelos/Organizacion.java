package com.BackEnd.BackEnd.Modelos;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="organizaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Organizacion")
    private int idOrganizacion;
    
    @Column(name = "Nombre_Organizacion")
    private String nombreOrganizacion;

    @Column(name = "Descripcion")
    private String descripcion;
    
    @Column(name = "Fecha_Creacion")
    private Date fechaCreacion;
    
    @Column(name = "contrasena_administrador")
    private String contrasenaAdministrador;

    public Organizacion(String nombreOrganizacion, String descripcion, Date fechaCreacion, String contrasenaAdministrador) {
        this.nombreOrganizacion = nombreOrganizacion;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.contrasenaAdministrador = contrasenaAdministrador;
    }
}
