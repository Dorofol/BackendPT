const express = require('express');
const {Web3}  = require('web3')
const fs = require('fs');
const bodyParser = require('body-parser');

const cors = require('cors');

const app = express();
const PORT = 3000;

app.use(bodyParser.json());  // Esto permite que express procese datos en formato JSON
app.use(cors());

let provider = new Web3.providers.HttpProvider("HTTP://127.0.0.1:7545");
let web3 = new Web3(provider);
const abi = JSON.parse(fs.readFileSync('./contractVotations2_sol_Votaciones.abi'));
const bytecode = '0x' + fs.readFileSync('./contractVotations2_sol_Votaciones.bin');
//const abi = JSON.parse(fs.readFileSync('./contractVotations2_sol_crearVotacion.abi'));
//const abi = JSON.parse(fs.readFileSync('./__test_sol_SimpleStorage.abi'));
//const contractAddress= '0xE85887C410D48D970dE2a1a4584C88556fFEE223';

//const contrato = new web3.eth.Contract(abi, contractAddress);
const direccion_cuenta='0x1230De2789568d0f4fCc69822914792d5f8654C7';
app.post('/desplegarContrato', async (req, res) => {
    const { id, nombre, descripcion, fecha_inicio, fecha_final, contrasena } = req.body;

    try {
        const accounts = await web3.eth.getAccounts();
        const contrato = new web3.eth.Contract(abi);

        const deployedContrato = await contrato.deploy({
            data: bytecode,
            arguments: [id, nombre, descripcion, fecha_inicio, fecha_final, ""]
        }).send({
            from: accounts[0],
            gas: 6721974
        });

        console.log('Contrato desplegado en la dirección:', deployedContrato.options.address);
        
         const contrato2 = new web3.eth.Contract(abi, deployedContrato.options.address);
        await contrato2.methods.agregarCandidato(0, "test", "test@gmail.com", "").send({ from: direccion_cuenta ,gas: 6721974});
        res.json({
            success: true,
            address: deployedContrato.options.address
        });
    } catch (error) {
        console.error(error);
        res.json({
            success: false,
            message: "Error al desplegar el contrato"
        });
    }
});
app.get('/', (req, res) => {
    res.send('API de voto en línea funcionando!');
});
app.post('/agregarCandidato', async (req, res) => {
    const { id_candidato, nombre_candidato, email_candidato, contrasena, direccionHash } = req.body;
    const contrato = new web3.eth.Contract(abi, direccionHash);
    try {
        const respuesta=await contrato.methods.agregarCandidato(id_candidato, nombre_candidato, email_candidato, "").send({ from: direccion_cuenta ,gas: 6721974});
        console.log(respuesta)
        res.json({ success: 'Candidato agregado con éxito',transaccionHash: respuesta.transactionHash  });
    } catch (error) {
        console.log(error)
        res.status(500).json({ error: 'Error al agregar el candidato: ' + error });
    }
});
app.post('/agregarVotante', async (req, res) => {
    const { id_votante, contrasena , direccionHash } = req.body;
    const contrato = new web3.eth.Contract(abi, direccionHash);
    console.log(direccionHash)

    try {
        const transactionResponse = await contrato.methods.agregarVotante(id_votante, "contrasenaSegura123").send({ from: direccion_cuenta ,gas: 6721974});
        console.log(transactionResponse.transactionHash);
        res.json({ 
            transactionHash: transactionResponse.transactionHash
        });

    } catch (error) {
        console.log(error);
        res.status(500).json({ error: 'Error al agregar el votante: ' + error.message });
    }
});

app.put('/cambiarPasswordVotante', async (req, res) => {
    const { id_votante, contrasena, nueva_contrasena , direccionHash} = req.body;
    try {
        await contrato.methods.cambiarContrasenaVot(id_votante, contrasena, nueva_contrasena).send({ from: direccion_cuenta,gas: 6721974 });
        res.json({ success: 'Contraseña cambiada con éxito' });
    } catch (error) {
        res.status(500).json({ error: 'Error al cambiar la contraseña: ' + error.message });
    }
});
app.post('/votar', async (req, res) => {
    const { candidato_ID, votante_id ,direccionHash} = req.body;
    const contrato = new web3.eth.Contract(abi, direccionHash);
    try {
        const transactionResponse = await contrato.methods.votar(candidato_ID, votante_id, "").send({ from: direccion_cuenta,gas: 6721974 });
        console.log("votando"+transactionResponse.transactionHash)
        res.json({ 
            transactionHash: transactionResponse.transactionHash});
    } catch (error) {
        console.log(error)
        res.status(500).json({ error: 'Error al votar: ' });
    }
});
app.get('/getNumCandidatos', async (req, res) => {
    try {
        
        const contrato = new web3.eth.Contract(abi, "0xC1e39269be1eC3a87a1756307E28C8A3371945D5");
        const numCandidatos = await contrato.methods.getNumeroCandidatos().call();
        res.json({ numCandidatos:numCandidatos.toString() });
    } catch (error) {
        res.status(500).json({ error: 'Error al obtener el número de candidatos: ' + error.message });
    }
});
app.get('/getNumVotadores', async (req, res) => {
    try {
        const numVotantes = await contrato.methods.getNumeroVotadores().call();
        res.json({ NumVotantes:numVotantes.toString() });
    } catch (error) {
        res.status(500).json({ error: 'Error al obtener el número de votantes: ' + error.message });
    }
});
app.get('/getCandidato/:id', async (req, res) => {
    const id = req.params.id;
    const contrasena_votante = "contrasenaSegura123"; // Asumiendo que pasas la contraseña en el cuerpo de la solicitud
    const contrato = new web3.eth.Contract(abi, "0xC1e39269be1eC3a87a1756307E28C8A3371945D5");
    try {
        const candidato = await contrato.methods.getCandidate(id, contrasena_votante).call();
        let candidatoSerializable = {
            id: candidato[0].toString(),
            nombre: candidato[1],
            email: candidato[2],
            contadorVotos: candidato[3].toString()
        };
        res.json(candidatoSerializable);
    } catch (error) {
        res.status(500).json({ error: 'Error al obtener detalles del candidato: ' + error.message });
    }
});
app.get('/getVotador/:id', async (req, res) => {
    const id = req.params.id;
    try {
        const votante = await contrato.methods.getVotante(id).call();
        let votanteSerializable = {
            id: votante[0].toString(),
            voted: votante[1],
            candidatoID: votante[2].toString()
        };
        res.json(votanteSerializable);
    } catch (error) {
        res.status(500).json({ error: 'Error al obtener detalles del votante: ' + error.message });
    }
});
app.get('/obtenerGanador', async (req, res) => {
    const direccionHash = req.query.direccionHash;

    if(!direccionHash) {
        return res.status(400).json({ error: 'Se requiere la direccionHash' });
    }

    const contrato = new web3.eth.Contract(abi, direccionHash);
    try {
        const id_candidatoGanador = await contrato.methods.candidatoGanador().call();
        res.json({id_candidatoGanador: id_candidatoGanador.toString()});
    } catch (error) {
        res.status(500).json({ error: 'Error al obtener el candidato ganador: ' + error.message });
    }
});

app.post('/terminarVotacion', async (req, res) => {
    try {
        const { contrasena,direccionHash} = req.body;

        const contrato = new web3.eth.Contract(abi, direccionHash);

        const txReceipt = await contrato.methods.terminarVotacion(contrasena).send({ from: direccion_cuenta,gas: 6721974 });
        
        res.send({ transactionHash: txReceipt.transactionHash });
    } catch (error) {
        res.status(500).send({ error: error.message });
    }
});
app.post('/crearEleccion', async (req, res) => {
    try {
        const { id,nombre ,descripcion,fecha_inicio,fecha_final,contrasena,direccionHash} = req.body;

        const contratoAdesp = new web3.eth.Contract(abi);

        web3.eth.getAccounts().then(async (accounts) => {
            const deployedContrato = await contratoAdesp.deploy({
                data: bytecode,
                arguments: [id, nombre, descripcion, fecha_inicio, fecha_final, contrasena]
            }).send({
                from: accounts[0],
                gas: 6721974
            });
            console.log(deployedContrato.options.address)
            res.send({ direccionHash: deployedContrato.options.address });
        });
    } catch (error) {
        res.status(500).send({ error: error.message });
    }});
    app.post('/getInfo', async (req, res) => {
        const {contrasena} = req.body;  // Asume que la contraseña se envía como parámetro de consulta
      
        try {
            // Llama a la función getInfo del contrato
            const result = await contrato.methods.getInfo(contrasena).call();
            console.log(result)
            res.json({
                idVotacion: result[0].toString(),
                nombre_votacion: result[1].toString(),
                description_votacion: result[2].toString(),
                fecha_inicio_votacion: result[3].toString(),
                fecha_termino_votacion: result[4].toString(),
                estatus: result[5].toString(),
                candidatoGan: result[6].toString()
            });
        } catch (error) {
            res.status(500).json({ error: 'Error al obtener la información: ' + error.message });
        }
    });
app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});
