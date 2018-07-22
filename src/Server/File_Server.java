package Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class File_Server {
    
    public File_Server(DataInputStream in, DataOutputStream out)
    {
        inRead = new InputStreamReader(in);
        buffReader = new BufferedReader(inRead);
    }
    
    InputStreamReader inRead;           //  Ler o que o cliente escreveu
    BufferedReader buffReader;

    String fName;
    
    String address = "/site/estrutura_basica.html";
    File file;
    
    public void messageClient()
    {
        try{
        
            do{
                fName = buffReader.readLine();
                System.out.println(fName);
                
            } while(!fName.equals("null"));
            
        }catch(IOException e){}
        
    }
    
    public void response()
    {
        try{
            
            file = new File(address);
            
            
        }catch(Exception e){}
    }
    
}
