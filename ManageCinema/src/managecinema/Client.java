/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managecinema;
import java.net.Socket;
import java.io.*;
import data.*;
import java.util.*;

// mặc định địa chỉ của server là 192.168.75.1 
/**
 *
 * @author User
 */
public class Client {
    private static final int PORT = 8080;
    private static final String serverName = "192.168.75.1";
    private clientLayout layout;
    private DataOutputStream output;
    private DataInputStream input;
    
    public Client(){
        try{
            Socket client = new Socket(serverName,PORT);
            if(client.isConnected()){
                System.out.println("Connected to server with address " + client.getRemoteSocketAddress());
                // tạo đối tượng truyền dữ liệu giữa client và server 
                input = new DataInputStream(client.getInputStream());
                output = new DataOutputStream(client.getOutputStream());
                String mess = "GET/ListShowtime";
                output.writeUTF(mess);
                String resp = input.readUTF();

                System.out.println("response message: " + resp);
                handleData data = new handleData();
                List<Showtime> output = data.readShowtimeFile(resp);
                
                layout.setShowtime(output);                
                layout = new clientLayout();
                layout.setVisible(true);
                System.out.println("Number of data response: " + output.size());
       
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    public static void main(String args[]){
        Client client = new Client();
    }
    
}
