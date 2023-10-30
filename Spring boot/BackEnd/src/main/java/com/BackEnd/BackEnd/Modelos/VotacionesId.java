package com.BackEnd.BackEnd.Modelos;
import java.io.Serializable;

public class VotacionesId implements Serializable {
    private int idVotacion;
    private int idOrganizacion;
    public VotacionesId() {}

    // Constructor con argumentos
    public VotacionesId(int idVotacion, int idOrganizacion) {
        this.idVotacion = idVotacion;
        this.idOrganizacion = idOrganizacion;
    }

    public int getIdVotacion() {
        return idVotacion;
    }

    public void setIdVotacion(int idVotacion) {
        this.idVotacion = idVotacion;
    }

    public int getIdOrganizacion() {
        return idOrganizacion;
    }

    public void setIdOrganizacion(int idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }
}