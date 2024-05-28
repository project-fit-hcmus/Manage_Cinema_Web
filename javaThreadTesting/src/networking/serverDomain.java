/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package networking;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.*;

/**
 *
 * @author User
 */
public class serverDomain {
    public static void main(String args[]){
        String serverName = args[0];
        int port = Integer.parseInt(args[1]);
        try{
            //kết nối tới server với servername và port 
            Socket client = new Socket(serverName, port);
            OutputStream outStream = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outStream);
            
            // gửi data tới server 
           
        }catch(IOException e){
            e.printStackTrace();
        }
        
    } 
    
}
