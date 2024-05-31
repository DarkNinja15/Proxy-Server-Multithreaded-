const net = require("net");

const port = 8081;

const server = net.createServer((socket) => {
  console.log("Client connected");

  socket.on("data", (data) => {
    console.log(`Client data: ${data.toString()}`);
    socket.write("Hello from the server!");
  });

  socket.on("end", () => {
    console.log("Client disconnected");
  });
});

server.listen(port, () => {
  console.log(`Server listening on port ${port}`);
});
