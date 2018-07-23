package Server;

import java.io.*;
import java.net.Socket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
    private OutputStream response;              //  Envia resposta para o cliente     
    private BufferedReader buffReader;          //  Ler requisicao que cliente escreveu

    private File file;

    public void PUT() {

        try {

            file = new File(address);                   //  Arquivo .html

            InputStream inStream = new BufferedInputStream(
                                   new FileInputStream(file));
            
            OutputStream out = client.getOutputStream();
            
            String str = "";
            int data = 0;

            if(!file.exists()){
                System.out.println("Arquivo ausente");
            }

            do {
                data = inStream.read();
                str += (char) data;
            } while (data > -1);

           content = str.getBytes();
            
           //content = Files.readAllBytes(file.toPath());       //Tambem pode ser usado
           
            out.write(headerResponse().getBytes());
            out.write(content);
            
            out.flush();
            out.close();

        } catch (FileNotFoundException e) {
        } catch (IOException er) {
        }
    }

    public String headerResponse(){
        
        try{
            
            String resp = protocol + "200 OK\r\n"
                + "Location: http://localhost:5555/\r\n"
                + "Date: " + date() + "\r\n"
                + "Server: Server/1.0\r\n"
                + "Content-Type: text/html\r\n"
                + "Content-Length: " + String.valueOf(content.length) + "\r\n"
                + "\r\n";
                
                return resp;
            
        }catch(Exception err){
            System.err.println("Erro no metodo headerResponse");
        }
        return null;
    }
  
    private String date(){
        
        try{
        
        SimpleDateFormat dataFormat = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss", Locale.ENGLISH);
        Date date = new Date();
        String dataAtual;

        dataFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        dataAtual = dataFormat.format(date) + " GMT";
        
        return dataAtual;
        }catch(Exception er){System.err.println("Erro na data");}
        return null;
    }
    
    private String defineFile(String fileName){
        if(fileName.equals("/")){
            return "index.html";
        }else{
            return "erro.html";
        }
            
    }

}
