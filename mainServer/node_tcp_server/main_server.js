const net = require("net");

const port = 8081;

const server = net.createServer((socket) => {
  console.log("Client connected");

  socket.on("data", (data) => {
    console.log(`Client data: ${data.toString()}`);
    setTimeout(() => {
      console.log("5 seconds passed");
      socket.write("Hello from the main server after 5 secs!"); // 
    }, 5000);
    // socket.write("Hello from the server!");
  });

  socket.on("end", () => {
    console.log("Client disconnected");
  });
});

server.listen(port, () => {
  console.log(`Main Server listening on port ${port}`);
});
