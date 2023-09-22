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
//const abi = JSON.parse(fs.readFileSync('./__test_sol_SimpleStorage.abi'));
const contractAddress= '0x7A41Ecd6D8607cef3F0579fd3b5f9c9190f8c2B5';

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
app.post('/agregarCandidato', async (req, res) => {
    const { votacionId, nombre } = req.body;
    try {
        const cuentas = await web3.eth.getAccounts();
        await contrato.methods.agregarCandidato(votacionId, nombre).send({ from: cuentas[0] });
        res.json({ success: true });
    } catch (error) {
        res.status(500).json({ error: 'Error al agregar candidato'+error });
    }
});

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
        console.log(idsVotaciones)
        res.json(idsVotaciones.toString);
    } catch (error) {
        res.status(500).json({ error: 'Error al obtener IDs de votaciones: ' + error });
    }
});

app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});
