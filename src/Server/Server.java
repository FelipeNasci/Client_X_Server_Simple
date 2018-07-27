package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String args[]){
        
        int port = 5555;                            //  Porta de Conexao com o servidor
        
        /**********   IMPORTANTE  *********/
        /* Usar no browser localhost:port */
        /*  para conectar-se ao servidor  */
        /**********************************/
        
        /******************   GRUPO  *****************/
        /*  Diego Felipe Goncalves   20170171680     */
        /*  Felipe Tiago Santos de Melo 11311730     */
        /**********************************************/
        
        ExecutorService pool;
        ServerSocket server;
        Socket client;
        
        try{
            
            server = new ServerSocket(port);        //  Define o servidor
            pool = Executors.newCachedThreadPool(); //  Pool dinamico de threads
            
            System.out.println(date());
            System.out.println("Servidor Ativo");
            
            while(true)
            {
                client = server.accept();           //  Abre uma conexao para o cliente
                pool.execute(new Response(client)); //  Executa a resposta dentro de um poll
                
                System.out.println(date());
                System.out.println("\t**Cliente com endereco " + client.getInetAddress().getHostAddress() + 
                                   " Conectou-se com a porta " + client.getPort());
            }
            
        }catch(IOException e){System.err.println(e);}
        
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
