package requestHandler;
import lruCache.LRUcache;


import java.io.*;
import java.net.Socket;


public class TcpHandler implements Runnable{
    private final String destIP;
    private final int destPort;
    private final Socket clientSocket;
    private final LRUcache cache;

    public TcpHandler(String destIP,int destPort,Socket clientSocket,LRUcache cache){
        this.destIP=destIP;
        this.destPort=destPort;
        this.clientSocket=clientSocket;
        this.cache=cache;
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

            cacheData(data);

            sendDataToClient(data);

            destSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cacheData(String data){
        cache.put(clientSocket.getInetAddress().toString(),data);
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