package Server;

import java.io.*;
import java.net.Socket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class File_Server {

    public File_Server(Socket client, String protocol, String fileName) {
        this.address = "src/Site/" + defineFile(fileName);
        this.client = client;
        this.protocol = protocol;
        
    }

    private final String address;
    private final String protocol;
    
    private byte[] content;

    private final Socket client;
    private String status;                      //  o status de retorno da resposta do servidor
    private OutputStream response;              //  Envia resposta para o cliente     
    private BufferedReader buffReader;          //  Ler requisicao que cliente escreveu

    private File file;

    public void PUT() {

        try {

            file = new File(address); //  Arquivo .html
            status = protocol + " 200 OK\r\n";
            
            if(!file.exists()){
                status = protocol + " 404 Not Found\r\n";
                file = new File("src/Site/erro.html");
            }

            InputStream inStream = new BufferedInputStream(
                                   new FileInputStream(file));
            
            OutputStream out = client.getOutputStream();
            
            String str = "";
            int data = 0;

            do {
                data = inStream.read();
                str += (char) data;
            } while (data > -1);

            content = str.getBytes();
            
            //content = Files.readAllBytes(file.toPath());       //Tambem pode ser usado
           
            //out.write(headerResponse(status, false).getBytes());
            authenticate(out);
            //out.write(content);
            
            out.flush();
            out.close();

        } catch (FileNotFoundException e) {
            System.err.println("Arquivo ausente");
        } catch (IOException er) {
        }
    }
    
    //header da resposta do servidor
    public String headerResponse(String s, boolean auth) {
        String resp;
        if (!auth){
            resp = s
                + "Server: Server/1.0\r\n"
                + "Location: http://localhost:5555/\r\n"
                + "Content-Type: text/html\r\n"
                + "Content-Length: " + String.valueOf(content.length) + "\r\n";
        }else{
            resp = protocol + " 401 Unauthorized\r\n"
                + "Server: Server/1.0\r\n"
                + "Location: http://localhost:5555/\r\n"
                + "Content-Type: text/html\r\n"
                + "WWW-Authenticate: Basic realm=\"System Administrator\"";
        }
        return resp;
    }
    
    private void authenticate(OutputStream o) throws IOException{
        System.out.println(address);
        if (address.equalsIgnoreCase("src/Site//site2.html")){
            o.write(headerResponse(status, true).getBytes());
        }else{
            o.write(headerResponse(status, false).getBytes());            
        }
        o.write(content);
    }
    
    private String defineFile(String fileName){
        if(fileName.equals("/")){
            return "index.html";
        }else{
            return fileName;
        }  
    }

}
