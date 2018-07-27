package Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Response implements Runnable {

    public Response(Socket client) {
        this.client = client;

    }

    private String method;
    private String protocol;
    private String fileName;

    private final Socket client;                //  Cliente
    private DataInputStream in;                 //  Captura mensagens do cliente

    @Override
    public void run() {

        try {

            in = new DataInputStream(client.getInputStream());
            
            headerClient();
            //System.err.println(headerClient());                     //  Ler o Cabecalho do client
            
            switch (method) {

                case "GET":
                    //  Solicita um documento do servidor
                    //  O Server recebe um GET e retorna um PUT
                    new File_Server(client, protocol, fileName).Response();
                    break;

                case "HEAD":
                    //  Solicita informacoes sobre um documento, mas nao o documento em si
                    break;

                case "POST":
                    //  Envia informacoes do cliente para o servidor
                    break;

                case "TRACE":
                    break;

                case "OPTION":
                    break;
                    
                default:
                    break;

            }

        } catch (IOException e) {
        } catch (InterruptedException ex) {
            Logger.getLogger(Response.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //***********************************************
    //***   LE E RETORNA O CABECALHO DO CLIENTE ***** 
    //***********************************************   

    private String headerClient() throws InterruptedException {
        try {

            BufferedReader buffReader = new BufferedReader(new InputStreamReader(in));

            String[] str = buffReader.readLine().split(" ");

            this.method = str[0];
            this.fileName = str[1];
            this.protocol = str[2];
            
            //  Armazena o cabecalho da requisicao HTTP e recupera a primeira linha
            String headerClient = method + " " + fileName + " " + protocol + "\r\n";
            String control;                 //  Variavel para extrair cada linha da requisicao
            
                do {

                    control = buffReader.readLine();
                    headerClient += control + "\r\n";

                } while (!control.isEmpty());
      
            return headerClient;

        } catch (IOException err) {
            System.err.println(err);
        }
        return null;
    }
    
}
