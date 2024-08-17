const express = require('express');
const app = express();
const PORT = 3000;
const workerpool = require('workerpool');
const pool = workerpool.pool();

app.use(express.json());

app.get('/', (req, res) => {
    pool.exec(heavyTask, [])
        .then((message) => {
            console.log("GET request received in workerpool");
            res.status(200).json({ "message": message });
        })
        .catch((err) => {
            console.error(err);
            res.status(500).json({ "error": "Workerpool error" });
        });
});

function heavyTask() {
    for (let i = 0; i < 10000000000; i++) {}
    return "Hello World from Workerpool!";
}

app.post('/', (req, res) => {
    console.log("POST request received");
    console.log(req.headers);
    console.log(req.query);
    res.status(200).json({"message":"Hello World!"});
});

app.put('/', (req, res) => {
    console.log("PUT request received");
    console.log(req.headers);
    console.log(req.query);
    console.log(req.body);
    res.status(200).json({"message":"Hello World!"});
});

app.delete('/', (req, res) => {
    console.log("DELETE request received");
    console.log(req.headers);
    console.log(req.query);
    res.status(200).json({"message":"Hello World!"});
});

app.listen(PORT, () => {
    console.log(`Express server running on port ${PORT}`);
});