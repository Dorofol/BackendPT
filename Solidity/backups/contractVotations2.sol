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
    uint public candidatoGan=9999;
    string public nombre_votacion;
    string public description_votacion;
    string public fecha_inicio_votacion;
    string public fecha_termino_votacion;
    string public contrasena_administrador;
    uint num_candidatos=0;
    uint num_votantes=0;
    bool estatus=true;

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
    function terminarVotacion(string memory contrasena) public {
        require(estatus, "La votacion ya esta inactiva.");
          require(keccak256(bytes(contrasena)) == keccak256(bytes(contrasena_administrador)), "Contrasena adadministradormin incorrecta.");
         estatus=false;
    }    
    function agregarVotante(uint id_votante, string memory contrasena) public {
        require(estatus, "La votacion esta inactiva.");
        require(keccak256(bytes(contrasena)) == keccak256(bytes(contrasena_administrador)), "Contrasena administrador incorrecta.");
        num_votantes++;
        votantes[num_votantes] = Votante(id_votante, false, 0, contrasena); 
    }    
    function cambiarContrasenaVot(uint id_votante, string memory contrasena, string memory nueva_contrasena) public {
        require(estatus, "La votacion esta inactiva.");
          require(keccak256(bytes(contrasena)) == keccak256(bytes(votantes[id_votante].contrasena)), "Contrasena usuario incorrecta.");
        votantes[id_votante].contrasena = nueva_contrasena; 
    }
   function votar(uint candidato_ID,uint votante_id,string memory contrasena_votante) public {
        require(estatus, "La votacion esta inactiva.");
        require(keccak256(bytes(votantes[candidato_ID].contrasena)) == keccak256(bytes(contrasena_votante)), "Contrasena usuario incorrecta.");
        require(!votantes[votante_id].voted, "El usuario ya ha votado por alguien.");
        
        votantes[votante_id].candidatoID=candidato_ID;
        votantes[votante_id].voted=true ;
        candidatos[candidato_ID].contadorVotos++;
    }
    function getNumeroCandidatos() public view returns(uint) {
        return num_candidatos;
    }
    function getNumeroVotadores() public view returns(uint) {
        return num_votantes;
    }
    function getCandidate(uint id_candidato, string memory contrasena_admin ) public view returns (uint, string memory , string memory ,uint) {
        require(keccak256(bytes(contrasena_administrador)) == keccak256(bytes(contrasena_admin)), "Contrasena usuario incorrecta.");
        return (candidatos[id_candidato].id, candidatos[id_candidato].nombre, candidatos[id_candidato].email, candidatos[id_candidato].contadorVotos);
    } 
    
    function getVotante(uint id_votante) public view returns (uint, bool, uint) {
        return (votantes[id_votante].id, votantes[id_votante].voted, votantes[id_votante].candidatoID);
    }
    function candidatoGanador() public view  returns (uint) {
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
    function getInfo(string memory contrasena) public view returns (uint, string memory, string memory, string memory, string memory, bool,uint) {
        require(keccak256(bytes(contrasena)) == keccak256(bytes(contrasena_administrador)), "Contrasena administrador incorrecta.");
        return (
            idVotacion,
            nombre_votacion,
            description_votacion,
            fecha_inicio_votacion,
            fecha_termino_votacion,
            estatus,
            candidatoGan
        );
    }
}