const express = require('express');
const app = express();
const PORT = 3000;

app.use(express.text({ type: '*/*' }));

app.all('*', (req, res) => {
    console.log('Received request:', req.method, req.url);
    console.log('Headers:', req.headers);
    console.log('Body:', req.body);
    res.send('Response from Express server');
});

app.listen(PORT, () => {
    console.log(`Express server running on port ${PORT}`);
});