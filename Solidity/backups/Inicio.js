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
//const abi = JSON.parse(fs.readFileSync('./contractVotations2_sol_crearVotacion.abi'));
//const abi = JSON.parse(fs.readFileSync('./__test_sol_SimpleStorage.abi'));
const contractAddress= '0x9a3a9e812BA9714f9ed54AA39EAe8674D2450231';

const contrato = new web3.eth.Contract(abi, contractAddress);

app.get('/', (req, res) => {
    res.send('API de voto en línea funcionando!');
});

// Crear una nueva votación
app.post('/crearVotacion', async (req, res) => {
    try {
        const cuentas = await web3.eth.getAccounts();
        const respuesta = await contrato.methods.crearVotacion().send({ from: cuentas[0] });
        console.log(respuesta)
        res.json({ votacionId: respuesta.transactionHash+"" });
    } catch (error) {
        res.status(500).json({ error: 'Error al crear votación'+error });
    }
});

// Agregar un candidato a una votación específica
/*app.post('/agregarCandidato', async (req, res) => {
    const { votacionId, nombre } = req.body;
    try {
        const cuentas = await web3.eth.getAccounts();
        const ag=await contrato.methods.agregarCandidato(votacionId, nombre).send({ from: cuentas[0] });
        console.log(ag);
        res.json({ success: true });
    } catch (error) {
        res.status(500).json({ error: 'Error al agregar candidato '+error });
    }
});*/

app.post('/testpo', async (req, res) => {
    const { votacionId, nombre } = req.body;
    try {
        const cuentas = await web3.eth.getAccounts();
        await contrato.methods.set(votacionId).send({ from: cuentas[1] });
        res.json({ success: true });
    } catch (error) {
        res.status(500).json({ error: 'Error al agregar dato'+error });
    }
});
app.get('/testpog', async (req, res) => {
    try{
        const votos  = await contrato.methods.get().call();
        console.log(votos.toString())
        res.send('API de voto en línea funcionando!'+votos);
    }catch(err){
        console.log(err)
        res.send('API de voto en línea funcionando!'+err);
    }
});


// Votar por un candidato en una votación específica
app.post('/votar', async (req, res) => {
    const { votacionId, candidatoId } = req.body;
    try {
        const cuentas = await web3.eth.getAccounts();
        const vot = await contrato.methods.votar(votacionId, candidatoId).send({ from: cuentas[0] });
        console.log(vot);
        res.json({ success: true});
    } catch (error) {
        res.status(500).json({ error: 'Error al votar'+error });
    }
});

// Obtener el número de votos de un candidato en una votación específica
app.get('/obtenerVotos/:votacionId/:candidatoId', async (req, res) => {
    const { votacionId, candidatoId } = req.params;
    try {
        const cuentas = await web3.eth.getAccounts();
        const votos = await contrato.methods.obtenerVotos(votacionId, candidatoId).call({ from: cuentas[0] });
        console.log("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        console.log(votos);
        res.json({ votos: votos });
    } catch (error) {
        //console.log(res);
        res.status(500).json({ error: 'Error al obtener votos'+error});
    }
});
// Obtener todos los candidatos de una votación específica
app.get('/obtenerCandidatos/:votacionId', async (req, res) => {
    const { votacionId } = req.params;
    try {
        const candidatos = await contrato.methods.obtenerCandidatos(votacionId).call();
        console.log(candidatos);
        res.json({ candidatos });
    } catch (error) {
        res.status(500).json({ error: 'Error al obtener candidatos: ' + error });
    }
});
// Obtener todos los IDs de votaciones
app.get('/obtenerIDsVotaciones', async (req, res) => {
    try {
        const cuentas = await web3.eth.getAccounts();
        const idsVotaciones = await contrato.methods.obtenerIDsVotaciones().call();
        console.log(idsVotaciones.toString())
        res.json(idsVotaciones);
    } catch (error) {
        res.status(500).json({ error: 'Error al obtener IDs de votaciones: ' + error });
    }
});
app.post('/crearElection', async (req, res) => {
    const {
        id, nombre, descripcion, fecha_inicio, fecha_final, contrasena
    } = req.body;

    try {
        const cuentas = await web3.eth.getAccounts();
        const resultado = await contrato.methods.createElection(id, nombre, descripcion, fecha_inicio, fecha_final, contrasena).send({ from: cuentas[1] });
        console.log(resultado);
        res.json({ success: true });
    } catch (error) {
        res.status(500).json({ error: 'Error al crear elección: ' + error.message });
    }
});
app.get('/getDeployedElection/:id', async (req, res) => {
    const id = req.params.id;
    try {
        const detallesVotacion = await contrato.methods.getDeployedElection(id).call();
        console.log(detallesVotacion);
        res.json(detallesVotacion);
    } catch (error) {
        res.status(500).json({ error: 'Error al obtener detalles de la votación: ' + error.message });
    }
});
// 1. POST /crearElection
app.post('/crearElection', async (req, res) => {
    const {
        id, nombre, descripcion, fecha_inicio, fecha_final, contrasena
    } = req.body;

    try {
        const cuentas = await web3.eth.getAccounts();
        const resultado = await contrato.methods.createElection(id, nombre, descripcion, fecha_inicio, fecha_final, contrasena).send({ from: cuentas[0] });
        console.log(resultado);
        res.json({ success: true });
    } catch (error) {
        res.status(500).json({ error: 'Error al crear elección: ' + error.message });
    }
});

// 2. POST /agregarCandidato
app.post('/agregarCandidato', async (req, res) => {
    const { id_candidato, nombre_candidato, email_candidato } = req.body;
    try {
        const cuentas = await web3.eth.getAccounts();
        console.log(id_candidato, nombre_candidato, email_candidato);
        const resultado = await contrato.methods.agregarCandidato(id_candidato, nombre_candidato, email_candidato).send({ from: cuentas[1],gas: 6721974 });
        console.log(resultado);
        res.json({ success: true });
    } catch (error) {
        console.log(error);
        res.status(500).json({ error: 'Error al agregar candidato: ' + error.message });
    }
});

// 3. GET /getNumeroCandidatos
app.get('/getNumeroCandidatos', async (req, res) => {
    try {
        const numero = await contrato.methods.getNumeroCandidatos().call();
        console.log(numero);
        res.json({ numCandidatos: numero });
    } catch (error) {
        res.status(500).json({ error: 'Error al obtener el número de candidatos: ' + error.message });
    }
});

// 4. GET /getNumOfVoters
app.get('/getNumOfVoters', async (req, res) => {
    try {
        const numero = await contrato.methods.getNumOfVoters().call();
        console.log(numero);
        res.json({ numVotantes: numero });
    } catch (error) {
        res.status(500).json({ error: 'Error al obtener el número de votantes: ' + error.message });
    }
});

// 5. GET /getCandidate/:id
app.get('/getCandidate/:id', async (req, res) => {
    const id = req.params.id;
    try {
        const candidato = await contrato.methods.getCandidate(id).call();
        console.log(candidato);
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

app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});
