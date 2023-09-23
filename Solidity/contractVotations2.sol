// SPDX-License-Identifier: MIT 
pragma solidity ^0.8.0;

contract Votaciones {

    struct Candidato {
        uint id;
        string nombre;
        string email;
        uint contadorVotos;
    }
    mapping(uint=>Candidato) public candidatos;

    struct Votante {
        uint id;
        bool voted;
        uint candidatoID;
        string contrasena;
    }

    mapping(uint=>Votante) votantes;

    uint public idVotacion;
    string public nombre_votacion;
    string public description_votacion;
    uint num_candidatos=0;
    uint num_votantes=0;
    bool estatus=true;
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
    function agregarCandidato(uint id_candidato, string memory nombre_candidato, string memory email_candidato,string memory contrasena) public {
        require(estatus, "La votacion esta inactiva.");
          require(keccak256(bytes(contrasena)) == keccak256(bytes(contrasena_administrador)), "Contrasena adadministradormin incorrecta.");
        num_candidatos++; 
        candidatos[num_candidatos] = Candidato(id_candidato,nombre_candidato,email_candidato,0); 
    }    
    function agregarVotante(uint id_votante, string memory contrasena) public {
        require(estatus, "La votacion esta inactiva.");
          require(keccak256(bytes(contrasena)) == keccak256(bytes(contrasena_administrador)), "Contrasena administrador incorrecta.");
        num_votantes++; 
        votantes[num_votantes] = Votante(id_votante, false, 0, contrasena); 
    }
   function votar(uint candidato_ID,uint votante_id,string memory contrasena_votante) public {
        require(estatus, "La votacion esta inactiva.");
        require(keccak256(bytes(votantes[candidato_ID].contrasena)) == keccak256(bytes(contrasena_votante)), "Contrasena usuario incorrecta.");
        require(!votantes[votante_id].voted, "El usuario ya ha votado por alguien.");
        
        votantes[candidato_ID].candidatoID=candidato_ID;
        votantes[candidato_ID].voted=true ;
        candidatos[candidato_ID].contadorVotos++;
    }
    function getNumeroCandidatos() public view returns(uint) {
        return num_candidatos;
    }
    function getNumeroVotadores() public view returns(uint) {
        return num_votantes;
    }
    function getCandidate(uint id_candidato, string memory contrasena_votante ) public view returns (uint, string memory , string memory ,uint) {
        require(keccak256(bytes(votantes[id_candidato].contrasena)) == keccak256(bytes(contrasena_votante)), "Contrasena usuario incorrecta.");
        return (candidatos[id_candidato].id, candidatos[id_candidato].nombre, candidatos[id_candidato].email, candidatos[id_candidato].contadorVotos);
    } 
    
    function getVotante(uint id_votante) public view returns (uint, bool, uint) {
        return (votantes[id_votante].id, votantes[id_votante].voted, votantes[id_votante].candidatoID);
    }
    function candidatoGanador() public view  returns (uint) {
    require(!estatus, "La votacion sigue activa.");
    uint masVotos = candidatos[0].contadorVotos;
    uint id_candidatoGanador;
    for(uint i = 1;i<num_candidatos;i++) {
        if(masVotos < candidatos[i].contadorVotos) {
            masVotos = candidatos[i].contadorVotos;
            id_candidatoGanador = i;
        }
    }
    return (id_candidatoGanador);
    }
}
