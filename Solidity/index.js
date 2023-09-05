const {express} = require('express');
const {Web3}  = require('web3')

const app = express();
const PORT = 3000;

let provider = new Web3.providers.HttpProvider("HTTP://127.0.0.1:7545");

let web3 = new Web3(provider);

console.log(web3);
app.get('/', (req, res) => {
    res.send('API de voto en línea funcionando!');
});

// Aquí es donde agregarías tus endpoints
app.get('/cuentas', async (req, res) => {
    try {
        const cuentas = await web3.eth.getAccounts();
        res.json(cuentas);
    } catch (error) {
        res.status(500).json({ error: 'Error al obtener las cuentas' });
    }
});

app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});