const express = require('express');
const {Web3}  = require('web3')
const fs = require('fs');
const bodyParser = require('body-parser');

// Configura la app para usar bodyParser


const app = express();
const PORT = 3000;
const abi = JSON.parse(fs.readFileSync('./contractVotations_sol_Votacion.abi', 'utf-8'));
const contractAddress= '0x55224de443A6E7B1EAd6550B49Ca6b8918E38dCd';
let provider = new Web3.providers.HttpProvider("HTTP://127.0.0.1:7545");

let web3 = new Web3(provider);

//console.log(web3);
app.get('/', (req, res) => {
    res.send('API de voto en línea funcionando!');
});

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
// Aquí es donde agregarías tus endpoints
app.get('/cuentas', async (req, res) => {
    try {
        const cuentas = await web3.eth.getAccounts();
        res.json(cuentas);
    } catch (error) {
        res.status(500).json({ error: 'Error al obtener las cuentas'+error });
    }
});
app.post('/agregarCandidato', async (req, res) => {
    console.log(req.body);
    const nombre = req.body.nombre;
    const contrato = new web3.eth.Contract(abi, contractAddress);
    try {
        const result = await contrato.methods.agregarCandidato(nombre).send({ from: '0xa257bB0773a3AF787fd40516ba584D00a17104Ae' });
        //console.log(result)
        //res.json(result);
    } catch (error) {
        res.status(500).json({ error: 'Error al agregar candidato'+error });
    }
});
app.post('/votar', async (req, res) => {
    const candidatoId = req.body.candidatoId;
    const contrato = new web3.eth.Contract(abi, contractAddress);
    try {
        const result = await contrato.methods.votar(candidatoId).send({ from: '0xa257bB0773a3AF787fd40516ba584D00a17104Ae' });
        res.json(result);
    } catch (error) {
        res.status(500).json({ error: 'Error al votar'+error });
    }
});
app.get('/candidato/:id', async (req, res) => {
    const id = req.params.id;
    console.log(id);
    const contrato = new web3.eth.Contract(abi, contractAddress);
    try {
        const candidato = await contrato.methods.candidatos(id).call();
        res.json(candidato);
    } catch (error) {
        res.status(500).json({ error: 'Error al obtener candidato'+error });
    }
});
app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});