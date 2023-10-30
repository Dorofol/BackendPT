package com.BackEnd.BackEnd.Modelos;

import java.io.Serializable;

public class UsuariosVotacionId implements Serializable {
    private int idUsuario;
    private int idVotacion;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdVotacion() {
        return idVotacion;
    }

    public void setIdVotacion(int idVotacion) {
        this.idVotacion = idVotacion;
    }
}

