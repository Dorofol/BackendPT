package com.BackEnd.BackEnd.Modelos;

import java.util.Date;

public class OrganizacionResponseDTO {
    private int idOrganizacion;
    private String nombreOrganizacion;
    private String descripcion;
    private Date fechaCreacion;
    private String rol;

    // Asegúrate de tener getters para todas las propiedades

    public int getIdOrganizacion() {
        return idOrganizacion;
    }

    public String getNombreOrganizacion() {
        return nombreOrganizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }


    public String getRol() {
        return rol;
    }

    // Y setters si los necesitas

    public void setRol(String rol) {
        this.rol = rol;
    }
    public void setIdOrganizacion(int idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }

    public void setNombreOrganizacion(String nombreOrganizacion) {
        this.nombreOrganizacion = nombreOrganizacion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
