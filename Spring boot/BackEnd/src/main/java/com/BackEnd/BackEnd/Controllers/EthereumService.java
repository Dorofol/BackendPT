package com.BackEnd.BackEnd.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Service
public class EthereumService {

    @Value("${ethereum.rpc.url}")
    private String rpcUrl;

    @Value("${ethereum.private.key}")
    private String privateKey;

    public Web3j web3j() {
        return Web3j.build(new HttpService(rpcUrl));
    }

    public Credentials credentials() {
        return Credentials.create(privateKey);
    }
}