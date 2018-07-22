package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Response implements Runnable {
    
    public Response(Socket client) {
        this.client = client;

    }

    Socket client;              //  Cliente
    DataInputStream in;         //  Captura mensagens do cliente
    DataOutputStream out;       //  Envia mensagens para o cliente
    
    String message;
    

    @Override
    public void run() {

        try {
            
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
            
            File_Server f;
                
            while (true) {
                
                
                f = new File_Server(in, out);
                client.close();
                
                
                
                /*
                message = in.readUTF();
                System.out.println(message);
                out.writeUTF("Hello World");
                */
            }

        } catch (IOException e) {
        }

    }

}
