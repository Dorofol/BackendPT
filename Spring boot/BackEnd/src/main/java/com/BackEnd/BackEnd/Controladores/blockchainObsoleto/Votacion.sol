// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract Votacion {

    struct Candidato {
        uint id;
        string nombre;
        uint conteoVotos;
    }

    mapping(uint => Candidato) public candidatos;
    mapping(address => bool) public votantes;

    uint public candidatosConteo = 0;

    event votoEvento (
        uint candidatoId
    );

    function agregarCandidato(string memory _nombre) public {
        candidatosConteo ++;
        candidatos[candidatosConteo] = Candidato(candidatosConteo, _nombre, 0);
    }

    function votar(uint _candidatoId) public {
        // Asegurar que el votante no haya votado antes
        require(!votantes[msg.sender], "Ya has votado.");

        // Asegurar que el voto sea para un candidato válido
        require(_candidatoId > 0 && _candidatoId <= candidatosConteo, "Candidato no valido.");

        // Registrar al votante como votado
        votantes[msg.sender] = true;

        // Actualizar el conteo de votos
        candidatos[_candidatoId].conteoVotos ++;

        // Disparar el evento de voto
        emit votoEvento(_candidatoId);
    }
}