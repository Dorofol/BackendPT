// SPDX-License-Identifier: MIT 
pragma solidity ^0.8.0;

contract Votaciones {

    struct Candidato {
        uint id;
        string nombre;
        uint contadorVotos;
    }
    mapping(uint256=>Candidato) public candidatos;

    struct Votante {
        uint id;
        bool voted;
        uint candidatoID;
    }

    mapping(string=>Votante) Votantes;
    mapping(uint256=>string) public User;
    
    uint public votacionesCount = 0;
    uint public idVotacion;
    string public nombre_votacion;
    string public description_votacion;

    constructor( uint id, string nombre, string descripcion) public {
        idVotacion=id;
        nombre_votacion=nombre;
        description_votacion=descripcion;
    }
        function adduser(uint256 _Id,string memory _name)public {
    		User[_Id]=_name;
    }
    function user(uint256 _Id)public view returns(string memory)
	{
  		return User[_Id];
  }

    function agregarCandidato(uint _votacionId, string memory _nombre) public {
        Votacion storage votacion = votaciones[_votacionId];
        votacion.candidatosCount++;

        votacion.candidatos[votacion.candidatosCount].id = votacion.candidatosCount;
        votacion.candidatos[votacion.candidatosCount].nombre = _nombre;
        votacion.candidatos[votacion.candidatosCount].contadorVotos = 0;
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
    for (uint i = 1; i <= votacionesCount; i++) {
        ids[i-1] = i;
    }
    return ids;
}


}
