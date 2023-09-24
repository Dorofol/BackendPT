const {Web3} = require('web3');
const fs = require('fs');

// Inicializar Web3
let web3 = new Web3(new Web3.providers.HttpProvider("HTTP://127.0.0.1:7545")); // Asume que estás usando Ganache

// Leer el ABI y el bytecode
//const abi = JSON.parse(fs.readFileSync('./contractVotations2_sol_Votaciones.abi', 'utf-8'));


const abi = JSON.parse(fs.readFileSync('./contractVotations2_sol_Votaciones.abi'));
const bytecode = '0x' + fs.readFileSync('./contractVotations2_sol_Votaciones.bin');
/*cconst abi = JSON.parse(fs.readFileSync('./contractVotations2_sol_crearVotacion.abi'));
const bytecode = '0x' + fs.readFileSync('./contractVotations2_sol_crearVotacion.bin');*/


/*const abi = JSON.parse(fs.readFileSync('./__test_sol_SimpleStorage.abi'));
const bytecode = '0x' + fs.readFileSync('./__test_sol_SimpleStorage.bin');*/

// Obtener cuentas
web3.eth.getAccounts().then(async (accounts) => {
    const contrato = new web3.eth.Contract(abi);

    // Desplegar contrato
    const deployedContrato = await contrato.deploy({
        data: bytecode   , 
        arguments: [0, "cola", "descripcion", "fecha_inicio", "fecha_final", "contrasena"]
    }).send({
        from: accounts[0], 
        gas: 600000
    });
    console.log('Contrato desplegado en la dirección:', deployedContrato.options.address);
});
