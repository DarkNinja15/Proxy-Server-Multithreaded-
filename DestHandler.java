import java.io.*;
import java.net.Socket;

public class DestHandler implements Runnable{
    private final String destIP;
    private final int destPort;
    private final Socket clientSocket;

    DestHandler(String destIP,int destPort,Socket clientSocket){
        this.destIP=destIP;
        this.destPort=destPort;
        this.clientSocket=clientSocket;
    }

    @Override
    public void run() {
        try {
            Socket destSocket=new Socket(destIP,destPort);

            InputStream inFromDest=destSocket.getInputStream();
            OutputStream outToDest=destSocket.getOutputStream();

            writeToStream(outToDest, "Hello from proxy server");

            String data=readFromStream(inFromDest);
            System.out.println("Data from destination server: "+data);

            sendDataToClient(data);

            destSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendDataToClient(String data) throws IOException {
        OutputStream outToClient=clientSocket.getOutputStream();
        writeToStream(outToClient, data);
    }

    private String readFromStream(InputStream in) throws IOException {
        byte[] data=new byte[1024];
        in.read(data);
        return new String(data);
    }

    private void writeToStream(OutputStream out,String data) throws IOException {
        out.write(data.getBytes());
    }
    
}