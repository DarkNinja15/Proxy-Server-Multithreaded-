const express = require('express');
const app = express();
const PORT = 3000;

app.use(express.json());

app.get('/', (req, res) => {
    console.log("GET request received");
    console.log(req.headers);
    console.log(req.query);
    console.log(req.body);
    res.status(200).json({"message":"Hello World!"});
});

app.post('/', (req, res) => {
    console.log("POST request received");
    console.log(req.headers);
    console.log(req.query);
    console.log(req.body);
    res.status(200).json({"message":"Hello World!"});
});

app.listen(PORT, () => {
    console.log(`Express server running on port ${PORT}`);
});