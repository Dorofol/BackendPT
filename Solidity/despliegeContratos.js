const {Web3} = require('web3');
const fs = require('fs');
let web3 = new Web3(new Web3.providers.HttpProvider("HTTP://127.0.0.1:7545")); 
const abi = JSON.parse(fs.readFileSync('./contractVotations2_sol_Votaciones.abi'));
const bytecode = '0x' + fs.readFileSync('./contractVotations2_sol_Votaciones.bin');then(async (accounts) => {
    const contrato = new web3.eth.Contract(abi);

    // Desplegar contrato
    const deployedContrato = await contrato.deploy({
        data: bytecode   , 
        arguments: [0, "cola", "descripcion", "fecha_inicio", "fecha_final", "contrasena"]
    }).send({
        from: accounts[0], 
        gas: 6721974
    });
    console.log('Contrato desplegado en la dirección:', deployedContrato.options.address);
});
