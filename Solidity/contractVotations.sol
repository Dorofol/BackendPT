pragma solidity ^0.8.0;

contract Votacion {
    struct Candidato {
        uint id;
        string nombre;
        uint votos;
    }

    mapping(uint => Candidato) public candidatos;
    mapping(address => bool) public votantes;

    uint public candidatosCount = 0;

    function agregarCandidato(string memory _nombre) public {
        candidatosCount ++;
        candidatos[candidatosCount] = Candidato(candidatosCount, _nombre, 0);
    }

    function votar(uint _candidatoId) public {
        require(!votantes[msg.sender], "Ya has votado.");
        require(_candidatoId > 0 && _candidatoId <= candidatosCount, "Candidato no valido.");

        votantes[msg.sender] = true;
        candidatos[_candidatoId].votos ++;
    }
}