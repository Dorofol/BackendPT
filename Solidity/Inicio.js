const express = require('express');
const {Web3}  = require('web3')
const fs = require('fs');
const bodyParser = require('body-parser');

const app = express();
const PORT = 3000;

app.use(bodyParser.json());  // Esto permite que express procese datos en formato JSON

let provider = new Web3.providers.HttpProvider("HTTP://127.0.0.1:7545");
let web3 = new Web3(provider);
const abi = JSON.parse(fs.readFileSync('./contractVotations2_sol_Votaciones.abi'));
const bytecode = '0x' + fs.readFileSync('./contractVotations2_sol_Votaciones.bin');
//const abi = JSON.parse(fs.readFileSync('./contractVotations2_sol_crearVotacion.abi'));
//const abi = JSON.parse(fs.readFileSync('./__test_sol_SimpleStorage.abi'));
const contractAddress= '0x52BBf496A4A56c3fa6cd8DC8BF45157d29072A05';

const contrato = new web3.eth.Contract(abi, contractAddress);
const direccion_cuenta='0x5e3CC189c1394f9535f5752D5D0FfE3B8F5A2967';
app.get('/', (req, res) => {
    res.send('API de voto en línea funcionando!');
});
app.post('/agregarCandidato', async (req, res) => {
    const { id_candidato, nombre_candidato, email_candidato, contrasena } = req.body;
    try {
        await contrato.methods.agregarCandidato(id_candidato, nombre_candidato, email_candidato, contrasena).send({ from: direccion_cuenta ,gas: 6721974});
        res.json({ success: 'Candidato agregado con éxito' });
    } catch (error) {
        console.log(error)
        res.status(500).json({ error: 'Error al agregar el candidato: ' + error });
    }
});
app.post('/agregarVotante', async (req, res) => {
    const { id_votante, contrasena } = req.body;
    try {
        await contrato.methods.agregarVotante(id_votante, contrasena).send({ from: direccion_cuenta ,gas: 6721974});
        res.json({ success: 'Votante agregado con éxito' });
    } catch (error) {
        res.status(500).json({ error: 'Error al agregar el votante: ' + error.message });
    }
});
app.put('/cambiarPasswordVotante', async (req, res) => {
    const { id_votante, contrasena, nueva_contrasena } = req.body;
    try {
        await contrato.methods.cambiarContrasenaVot(id_votante, contrasena, nueva_contrasena).send({ from: direccion_cuenta,gas: 6721974 });
        res.json({ success: 'Contraseña cambiada con éxito' });
    } catch (error) {
        res.status(500).json({ error: 'Error al cambiar la contraseña: ' + error.message });
    }
});
app.post('/votar', async (req, res) => {
    const { candidato_ID, votante_id, contrasena_votante } = req.body;
    try {
        await contrato.methods.votar(candidato_ID, votante_id, contrasena_votante).send({ from: direccion_cuenta,gas: 6721974 });
        res.json({ success: 'Votado con éxito' });
    } catch (error) {
        res.status(500).json({ error: 'Error al votar: ' + error.message });
    }
});
app.get('/getNumCandidatos', async (req, res) => {
    try {
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
    const contrasena_votante = "contrasena"; // Asumiendo que pasas la contraseña en el cuerpo de la solicitud
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
    try {
        const id_candidatoGanador = await contrato.methods.candidatoGanador().call();
        res.json({id_candidatoGanador:id_candidatoGanador.toString()});
    } catch (error) {
        res.status(500).json({ error: 'Error al obtener el candidato ganador: ' + error.message });
    }
});
app.post('/terminarVotacion', async (req, res) => {
    try {
        const { contrasena } = req.body;

        const contrato = new web3.eth.Contract(abi, contractAddress);

        const txReceipt = await contrato.methods.terminarVotacion(contrasena).send({ from: direccion_cuenta,gas: 6721974 });
        
        res.send({ transactionHash: txReceipt.transactionHash });
    } catch (error) {
        res.status(500).send({ error: error.message });
    }
});
app.post('/crearEleccion', async (req, res) => {
    try {
        const { id,nombre ,descripcion,fecha_inicio,fecha_final,contrasena} = req.body;

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
            res.send({ contractAddress: deployedContrato.options.address });
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
