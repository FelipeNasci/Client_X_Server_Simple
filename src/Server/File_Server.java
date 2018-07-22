package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class File_Server {
    
    public File_Server(DataInputStream in, DataOutputStream out)
    {
        inRead = new InputStreamReader(in);
    }
    
    InputStreamReader inRead;
    
    
}
