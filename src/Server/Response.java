package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Response implements Runnable {

    public Response(Socket client) {
        this.client = client;

    }

    Socket client;
    DataInputStream in;
    DataOutputStream out;
    
    String message;
    

    @Override
    public void run() {

        try {
            
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
                
            while (true) {
                
                message = in.readUTF();
                System.out.println(message);
                out.writeUTF("Hello World");
                
            }

        } catch (IOException e) {
        }

    }

}
