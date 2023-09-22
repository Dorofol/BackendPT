package com.BackEnd.BackEnd.Controladores.blockchainObsoleto;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Version;
import com.BackEnd.BackEnd.Controladores.blockchainObsoleto.Votacion;
@Service
public class VotingService {

    @Autowired
    private EthereumService ethereumService;

    private String contractAddress = "YOUR_CONTRACT_ADDRESS"; // Cambia esto a la dirección de tu contrato desplegado.

    public void voteForCandidate(BigInteger candidateName) throws Exception {
        Votacion contract = Votacion.load(contractAddress, ethereumService.web3j(), ethereumService.credentials(), new StaticGasProvider(candidateName, candidateName));

        // Asumiendo que tu contrato tiene una función votar que toma un nombre de candidato
        TransactionReceipt receipt = contract.votar(candidateName).send();
    }
}