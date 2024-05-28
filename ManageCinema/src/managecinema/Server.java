/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managecinema;
import data.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;
import java.util.*;
/**
 *
 * @author User
 */
public class Server {
    private ServerSocket serverSocket;
    private static final int PORT = 8080;
    private String dir = "";
    private serverLayout layout;
    
    public Server(int port) throws IOException{
        dir = System.getProperty("user.dir");
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(300000);
          
    }
    
    public void start(){
        System.out.println("Server started. Waiting for client connect ... ");
        // prepare data 
        handleData data = new handleData();
        List<auditorium> listAuditorium;
        List<Showtime> listShowtime;
        List<Booking> listBooking;
        listAuditorium = data.readAuditoriumFile(dir + "/src/data/auditorium.txt");
        listShowtime = data.readShowtimeFile(dir + "/src/data/showtime.txt");
        listBooking = data.readBookingStatus(dir + "/src/data/booking.txt");
        System.out.println("number of auditorium:" + listAuditorium.size());
        System.out.println("number of showtime: " + listShowtime.size());
        System.out.println("number of booking: " + listBooking.size());        
        // display layour server
        layout = new serverLayout();
        layout.setVisible(true);
        try{
            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                // tạo luồng xử lý cho từng client 
                ClientHandle clientHandle = new ClientHandle(clientSocket);
                clientHandle.start();
                
            }//end while
        }catch(IOException e){
            e.printStackTrace();
        }
        finally{
            stop();
        }//end try-catch
    }// end start function
    
    public void stop(){
        try{
            serverSocket.close();
            layout.setVisible(false);
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public class ClientHandle extends Thread{
        private Socket clientSocket;
        private DataInputStream input;
        private DataOutputStream output;

        public ClientHandle(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        @Override
        public void run(){
            try{
                // khởi tạo đối tương nhận dữ liệu tử client 
                input = new DataInputStream(clientSocket.getInputStream());
                // khởi tạo đối tượng trả dữ liệu cho client 
                output = new DataOutputStream(clientSocket.getOutputStream());
                String mess = "";
                String resp = "";
                while(true){
                    //get message from client 
                    mess = input.readUTF();
                    System.out.print(clientSocket.getLocalSocketAddress() + " : " + mess);
                    if(mess.equals("GET/ListShowtime")){
                        resp = dir + "/src/data/showtime.txt";
                    }
                    // response client
                    output.writeUTF(resp);
                }
            }catch(IOException e){
                e.printStackTrace();
            }finally{
                try{
                    if(input == null)
                        input.close();
                    if(output == null)
                        output.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    
    public static void main(String args[]){
        try{
            Server server = new Server(PORT);
            server.start();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    
    
    
    
}
