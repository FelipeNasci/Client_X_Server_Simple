package Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Response implements Runnable {

    public Response(Socket client) {
        this.client = client;
        this.log = new Arquivos("src/LOG/");
        
    }

    private String method;
    private String protocol;
    private String fileName;
    private String status;

    private Arquivos log;
    private final Socket client;                //  Cliente
    private DataInputStream in;                 //  Captura mensagens do cliente
    private boolean auth;

    @Override
    public void run() {

        try {

            in = new DataInputStream(client.getInputStream());

            headerClient();                                                     //lendo e tratando o cabeçalho do cliente
            //System.err.println(headerClient());
            //System.err.println(fileName);
            
            switch (method) {
                
                case "GET":
                    //  Solicita um documento do servidor
                    //  O Server recebe um GET e retorna uma resposta
                    status = new File_Server(client, protocol, fileName, auth).Response();
                    logger();                                       //  Salva informacoes do servidor em um log 
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
            auth = false;

            //  Armazena o cabecalho da requisicao HTTP e recupera a primeira linha
            String headerClient = method + " " + fileName + " " + protocol + "\r\n";
            String control;                 //  Variavel para extrair cada linha da requisicao

            do {

                control = buffReader.readLine();
                headerClient += control + "\r\n";

            } while (!control.isEmpty());
            
            if (headerClient.contains("Authorization: Basic d2VibWFzdGVyOnpycW1hNHY=")) auth = true;  //se vier essa string no header
                                                                                                     //o client acertou a senha
            if (this.fileName.equals("/")) {
                this.fileName = "Site/index.html";
            }

            return headerClient;

        } catch (IOException err) {
            System.err.println(err);
        }
        return null;
    }
    
    public void logger() throws IOException{
        String logger;
        logger = date() + " | "
                + "Cliente com endereco " + client.getInetAddress().getHostName() + " | "
                + "Conectou-se com porta: " + client.getPort() + " | "
                + "Solicitando com metodo: " + method + " | "
                + "Arquivo: " + fileName + " | "
                + "Protocolo: " + protocol + " | "
                + "Resposta Servidor: " + status;
        
        //log.criarArquivo("log.txt");
        log.gravar("log", logger, ".txt");
        
    }
    
    private static String date() {

        try {

            SimpleDateFormat dataFormat = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss", Locale.ENGLISH);
            Date date = new Date();
            String dataAtual;

            dataFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            dataAtual = dataFormat.format(date);

            return dataAtual;
        } catch (Exception er) {
            System.err.println("Erro na data");
        }
        return null;
    }
    
}
