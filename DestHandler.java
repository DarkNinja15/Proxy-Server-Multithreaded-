import java.io.*;
import java.net.Socket;

public class DestHandler implements Runnable{
    private final String destIP;
    private final int destPort;

    DestHandler(String destIP,int destPort){
        this.destIP=destIP;
        this.destPort=destPort;
    }

    @Override
    public void run() {
        try {
            Socket destSocket=new Socket(destIP,destPort);
            // InputStream inFromClient=clientSocket.getInputStream();
            // OutputStream outToClient=clientSocket.getOutputStream();

            InputStream inFromDest=destSocket.getInputStream();
            OutputStream outToDest=destSocket.getOutputStream();

            outToDest.write("Hello from server".getBytes());

            inFromDest.read();

            System.out.println(inFromDest.toString());

            destSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}