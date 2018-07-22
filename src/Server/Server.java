package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String args[]){
        
        int port = 5555;                            //  Porta de Conexao com o servidor
        
        /**********   IMPORTANTE  *********/
        /* Usar no browser localhost:port */
        /*  para conectar-se ao servidor  */
        /**********************************/
        
        ExecutorService pool;
        ServerSocket server;
        Socket client;
        
        try{
            
            server = new ServerSocket(port);        //  Define o servidor
            pool = Executors.newCachedThreadPool(); //  Pool dinamico de threads
            
            System.out.println("Servidor Ativo");
            
            while(true)
            {
                client = server.accept();           //  Abre uma conexao para o cliente
                pool.execute(new Response(client)); //  Executa a resposta dentro de um poll
                
                //System.out.println("\t**Cliente com endereco " + client.getInetAddress() + 
                 //                  " Conectou-se com a porta " + client.getPort());
            }
            
        }catch(IOException e){System.err.println(e);}
        
    }
}
