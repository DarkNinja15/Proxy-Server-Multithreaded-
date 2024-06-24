const net = require("net");

const SERVER_PORT = 8080;

const client = new net.Socket();

client.connect(SERVER_PORT, "localhost", () => {
  console.log("Connected to the server");
  let body = '{"username": "johndoe", "email": "johndoe@example.com"}';
  let httpGETRequest =
    "GET /?include=details&expand=all HTTP/1.1\r\n" +
    "Host: example.com\r\n" +
    "User-Agent: JavaClient/1.0\r\n" +
    "Content-Type: application/json\r\n" +
    "Content-Length: " +
    body.length +
    "\r\n" +
    "\r\n" +
    body;

  let httpPOSTRequest =
    "POST /?include=details&expand=all HTTP/1.1\r\n" +
    "Host: example.com\r\n" +
    "User-Agent: JavaClient/1.0\r\n" +
    "Content-Type: application/json\r\n" +
    "Content-Length: " +
    body.length() +
    "\r\n" +
    "\r\n" +
    body;

  client.write(httpGETRequest);
});

client.on("data", (data) => {
  console.log(`Server data: ${data.toString()}`);
  client.destroy();
});

client.on("close", () => {
  console.log("Connection closed");
});
