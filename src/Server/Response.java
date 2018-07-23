package Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Response implements Runnable {

    public Response(Socket client) {
        this.client = client;

    }

    private String method;
    private String protocol;
    private String fileName;

    private final Socket client;                //  Cliente
    private DataInputStream in;                 //  Captura mensagens do cliente
    private DataOutputStream out;               //  Envia mensagens para o cliente

    @Override
    public void run() {

        try {

            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());

            headerClient();                     //  Ler o Cabecalho do cliente

            switch (method) {

                case "GET":
                    //  Solicita um documento do servidor
                    //  O Server recebe um GET e retorna um PUT
                    new File_Server(client, protocol, fileName).PUT();
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
        }

    }

    //***********************************************
    //***   LE E RETORNA O CABECALHO DO CLIENTE ***** 
    //***********************************************   

    private String headerClient() {
        try {

            BufferedReader buffReader = new BufferedReader(new InputStreamReader(in));

            String[] str = new String[4];
            
            str = buffReader.readLine().split(" ");

            this.method = str[0];
            this.fileName = str[1];
            this.protocol = str[2];

            String headerClient = "";       //  Armazena o cabecalho da requisicao HTTP
            String control;                 //  Variavel para extrair cada linha da requisicao
            
            try {
                do {

                    control = buffReader.readLine();
                    headerClient += control + "\n";

                } while (!control.isEmpty());
            } catch (Exception erro) {
                System.err.println("erro em string headerClient");
            }
            return headerClient;

        } catch (IOException err) {
            System.err.println(err);
        }
        return null;
    }

}
