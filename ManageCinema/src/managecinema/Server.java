/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managecinema;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;
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
        // DISPLAY LAYOUT SERVER
        layout = new serverLayout();
        layout.setVisible(true);
        try{
            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                //TẠO LUỒNG XỬ LÝ CHO TỪNG CLIENT 
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
        private ManageCinema manage = new ManageCinema();
        private handleData handle = new handleData();

        public ClientHandle(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        @Override
        public void run(){
            try{
                // KHỞI TẠO ĐỐI TƯỢNG NHẬN DỮ LIỆU TỪ CLIENT
                input = new DataInputStream(clientSocket.getInputStream());
                // KHỞI TẠO ĐỐI TƯỢNG TRẢ DỮ LIỆU CHO CLIENT
                output = new DataOutputStream(clientSocket.getOutputStream());
                String mess = "";
                String resp = "";
                while(true){
                    //NHẬN THÔNG TIN TỪ CLIENT
                    mess = input.readUTF();
                    System.out.println(clientSocket.getLocalSocketAddress() + " : " + mess);
                    if(mess.contains("GET")){
                        if(mess.contains("ListShowtime")){
                            resp = dir + "\\src\\data\\showtime.txt";
                        }
                        if(mess.contains("ListBooking")){
                            resp = dir +  "\\src\\data\\booking.txt|";
                            resp += dir + "\\src\\data\\auditorium.txt";
                        }
                        if(mess.contains("ListAuditorium")){
                            resp = dir + "\\src\\data\\auditorium.txt";
                        }
                    }else
                    if(mess.contains("RESERVED")){
                        String result = manage.solveBooking(mess);
                        if(result.contains("true")){
                            resp = "SUCCESS";
                        }
                        else {
                            resp = "FAILED" + result;
                        }
                    }
                    
                    // PHẢN HỒI LẠI CHO CLIENT
                    output.writeUTF(resp);
                    System.err.println("response: " + resp);
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
