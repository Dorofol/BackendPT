// SPDX-License-Identifier: MIT 
pragma solidity ^0.8.0;

contract Votaciones {

    struct Candidato {
        uint id;
        string nombre;
        uint contadorVotos;
    }

    struct Votacion {
        uint id;
        mapping(uint => Candidato) candidatos;
        mapping(address => bool) votantes;
        uint candidatosCount;
    }

    mapping(uint => Votacion) public votaciones;
    uint public votacionesCount = 0;
    Votacion[] votacioness;
    

    function crearVotacion() public returns (uint) {
        votacionesCount++;

        votacioness[votacionesCount].id = votacionesCount;
        votacioness[votacionesCount].candidatosCount = 0;

        return votacionesCount;
    }

    function agregarCandidato(uint _votacionId, string memory _nombre) public {
        votaciones[_votacionId].candidatosCount=votaciones[_votacionId].candidatosCount+1;

        votaciones[_votacionId].candidatos[votaciones[_votacionId].candidatosCount].id = votaciones[_votacionId].candidatosCount;
        votaciones[_votacionId].candidatos[votaciones[_votacionId].candidatosCount].nombre = _nombre;
        votaciones[_votacionId].candidatos[votaciones[_votacionId].candidatosCount].contadorVotos = 0;
    }
    

    function votar(uint _votacionId, uint _candidatoId) public {
        Votacion storage votacion = votaciones[_votacionId];

        // Asegura que el votante no ha votado antes en esta votación
        require(!votacion.votantes[msg.sender], "Usted ya ha votado en esta votacion.");

        // Asegura que el candidato es válido
        require(_candidatoId > 0 && _candidatoId <= votacion.candidatosCount, "Candidato no valido.");

        // Registra el voto
        votacion.candidatos[_candidatoId].contadorVotos++;

        // Marca al votante como que ya ha votado en esta votación
        votacion.votantes[msg.sender] = true;
    }

    function obtenerVotos(uint _votacionId, uint _candidatoId) public view returns (uint) {
        Votacion storage votacion = votaciones[_votacionId];


        return votacion.candidatos[_candidatoId].contadorVotos;
    }
    function obtenerCandidatos(uint _votacionId) public view returns (Candidato[] memory) {
    Votacion storage votacion = votaciones[_votacionId];
    Candidato[] memory candidatosArray = new Candidato[](votacion.candidatosCount);

    for (uint i = 1; i <= votacion.candidatosCount; i++) {
        candidatosArray[i-1] = votacion.candidatos[i];
    }

    return candidatosArray;
}
function obtenerIDsVotaciones() public view returns (uint[] memory) {
    uint[] memory ids = new uint[](votacionesCount);
   /* for (uint i = 1; i <= votacionesCount; i++) {
        ids[i-1] = i;
    }*/
    return ids;
}
function obtenerIDsVotaciones2() public view returns (uint[] memory) {
    uint[] memory ids;
    for (uint i = 1; i <= votacionesCount; i++) {
        ids[i-1] = votacioness[i].id;
    }
    return ids;
}


}
