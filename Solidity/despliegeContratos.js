const {Web3} = require('web3');
const fs = require('fs');

// Inicializar Web3
let web3 = new Web3(new Web3.providers.HttpProvider("HTTP://127.0.0.1:7545")); // Asume que estás usando Ganache

// Leer el ABI y el bytecode
const abi = JSON.parse(fs.readFileSync('./contractVotations_sol_Votacion.abi', 'utf-8'));
const bytecode = '0x' + fs.readFileSync('./contractVotations_sol_Votacion.bin', 'utf-8');

// Obtener cuentas
web3.eth.getAccounts().then(async (accounts) => {
    const contrato = new web3.eth.Contract(abi);

    // Desplegar contrato
    const deployedContrato = await contrato.deploy({
        data: bytecode
    }).send({
        from: accounts[0], 
        gas: 1500000
    });

    console.log('Contrato desplegado en la dirección:', deployedContrato.options.address);
});
