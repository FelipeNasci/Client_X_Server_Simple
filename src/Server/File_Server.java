package Server;

import java.io.*;
import java.net.Socket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class File_Server {

    public File_Server(Socket client, String protocol, String fileName, boolean didAuthenticate) {
        defineFile(fileName);           //  Inicia o file
        this.client = client;
        this.protocol = protocol;
        this.didAuthenticate = didAuthenticate;
    }

    private String address;
    private final String protocol;
    private String status;                      //  o status de retorno da resposta do servidor
    
    private byte[] content;

    private final Socket client;
    private File file;
    private boolean didAuthenticate;

    public String Response() {

        try {

            try (OutputStream out = client.getOutputStream()) {

                //  Ler todo o arquivo e serializar
                content = Files.readAllBytes(file.toPath());

                //  Escreve para o cliente o Header e o body
                String str = headerResponse(needAuthenticate());

                //  System.err.println(str);

                out.write(str.getBytes());
                out.write(content);

                out.flush();
                return status;
            }

        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.err.println("Arquivo ausente");
        } catch (IOException er) {
            System.err.println(er);
        }
        return null;
    }
    
    //header da resposta do servidor
    public String headerResponse(boolean auth) {
        
        //  Autorizacao indica o final do cabecalho
        String authorization = "\r\n";              
        
        //  Se a requisicao precisar de autorizacao, a string  eh modificada
        if(auth)
            authorization = "WWW-Authenticate: Basic realm=\"System Administrator\"" + "\r\n" + "\r\n";
        
        return this.protocol + " " + this.status + "\r\n"
            + "Location: http://localhost:5555/\r\n"
            + "Server: Server/1.0\r\n"
            + "Content-Type: text/html\r\n"
            + "Content-Length: " + String.valueOf(content.length) + "\r\n"
            + authorization;

        }
        
    private boolean needAuthenticate() {

        //  Se for necessario autenticar, o codigo de status
        //  deve mudar
        if (address.contains("Protegido") && (!didAuthenticate)) {              //se o diretorio for protegido e o cliente
            status = "401 Unauthorized";                                        //nao autenticou, entao manda autenticar
            return true;
        }
        return false;
    }
    
    private void defineFile(String fileName) {

        this.address = "src/" + fileName;          //  Define o endereco do arquivo
        this.file = new File(address);                  //  Procura o arquivo
        this.status = "200 OK";                         //  Arquivo encontrado | tudo ok

        if (!file.exists()) {                           //  Se o arquivo nao existe
            this.address = "src/" + "Site/erro.html";   //  Localiza pg de erro 
            this.file = new File(address);              //  Identifica o arquivo de erro
            this.status = "404 Not Found";              //  Bad request
        }
    }
}
