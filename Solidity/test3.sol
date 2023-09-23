// SPDX-License-Identifier: MIT 
pragma solidity ^0.8.0;
contract crearVotacion {
        struct Votacion {
        address direccionHash;
        string nombre_votacion;
        string descripcion_votacion;
        string fecha_inicio_votacion;
        string fecha_final_votacion;
        string contrasena;
    }
    mapping(uint=>Votacion) votaciones;
    function createElection(uint id, string memory nombre, string memory descripcion, string memory fecha_inicio, string memory fecha_final, string memory contrasena) public{
        
        Votaciones vot = new Votaciones(id , nombre, descripcion,fecha_inicio,fecha_final,contrasena);
        
        votaciones[id].direccionHash = address(vot);
        votaciones[id].nombre_votacion = nombre;
        votaciones[id].descripcion_votacion = descripcion;
        votaciones[id].fecha_inicio_votacion = fecha_inicio;
        votaciones[id].fecha_final_votacion = fecha_final;
        votaciones[id].contrasena = contrasena;
    }
    function getDeployedElection(uint id) public view returns (address, string memory,string memory,string memory,string memory,string memory) {
        address val = votaciones[id].direccionHash;
        if (val == address(0)) 
            return (address(0), "", "", "Create an election.", "", "");
        else
            return (votaciones[id].direccionHash, votaciones[id].nombre_votacion, votaciones[id].descripcion_votacion, votaciones[id].fecha_inicio_votacion, votaciones[id].fecha_final_votacion, votaciones[id].contrasena);
    }

}
contract Votaciones {

    struct Candidato {
        uint id;
        string nombre;
        string email;
        uint contadorVotos;
    }
    mapping(uint256=>Candidato) public candidatos;

    struct Votante {
        uint id;
        bool voted;
        uint candidatoID;
    }

    mapping(string=>Votante) Votantes;
    mapping(uint=>string) public User;

    uint public idVotacion;
    string public nombre_votacion;
    string public description_votacion;
    uint num_candidatos=0;
    uint num_votantes=0;
    bool estatus;
    string public fecha_inicio_votacion;
    string public fecha_termino_votacion;
    string public contrasena_administrador;

    constructor(uint id, string memory nombre, string memory descripcion, string memory fecha_inicio, string memory fecha_final, string memory contrasena) {
        idVotacion = id;
        nombre_votacion = nombre;
        description_votacion = descripcion;
        fecha_inicio_votacion = fecha_inicio;
        fecha_termino_votacion = fecha_final;
        contrasena_administrador = contrasena;
        estatus= true;
    }
    function agregarCandidato(uint id_candidato, string memory nombre_candidato, string memory email_candidato) public {
        num_candidatos++; 
        candidatos[num_candidatos] = Candidato(id_candidato,nombre_candidato,email_candidato,0); 
    }
    function getNumeroCandidatos() public view returns(uint) {
        return num_candidatos;
    }
    function getNumOfVoters() public view returns(uint) {
        return num_votantes;
    }
    function getCandidate(uint id_candidato) public view returns (uint, string memory , string memory ,uint) {
        return (candidatos[id_candidato].id, candidatos[id_candidato].nombre, candidatos[id_candidato].email, candidatos[id_candidato].contadorVotos);
    } 
}
