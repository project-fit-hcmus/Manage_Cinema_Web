/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author User
 */

//example data:
//{
//    RAP01,
//    XUAT01,
//    [B2,B3,C9,D1,D2,D3,D4]
//}
public class Booking {
    private String auditoriumId;
    private String showtimeId;
    private String[] reserved ;
    //true: is taken | false: is empty
   
    public void setAuditoriumId(String inp){
        this.auditoriumId = inp;
    }
    public void setShowtimeId(String inp){
        this.showtimeId = inp;
    }
    
    public String getShowtimeId(){return this.showtimeId;}
    public String getAuditoriumId(){return this.auditoriumId;}
    
    public String[] getReserved(){return this.reserved;}
    public void setReserved(String[] value){this.reserved = value;}
    public void ReadInfo(String input){
        
        int pos = input.indexOf(',');
        auditoriumId = input.substring(2,pos);
        input = input.substring(pos+2);
         
        pos = input.indexOf(',');
        showtimeId = input.substring(0,pos);
        input = input.substring(pos + 2);

        pos = input.indexOf('}');
        input = input.substring(0,pos);
        
        reserved = readMatrix(input);
        
        
        
        
    }
    public void PrintInfo(){
        System.out.println("Auditorium: " + auditoriumId + "\n");
        System.out.println("Showtime: " + showtimeId + "\n");
        System.out.println("Status: \n");
        for(int i = 0; i < this.reserved.length ; ++i){
            System.out.print(this.reserved[i] + " ");
        }
    }
    
    //input like: [B2,B3,C9,D1,D2,D3,D4]
    public String[] readMatrix(String input){
        if(input.charAt(1) == ']') {
            return new String[0];
        }
        int count = 1;
        for(int i =0; i < input.length(); ++i)
            if(input.charAt(i) == ',')
                ++count;
        String[] output = new String[count]; 
        int pos = input.indexOf(",");
        int i = 0;
        if(pos == -1){
            output[0] = input.substring(input.indexOf("[")+1,input.indexOf("]"));
        }else{
            input = input.substring(1,input.length());
            pos = input.indexOf(",");
            while(pos != -1){
                output[i] = input.substring(0,pos);
                input = input.substring(pos+1);
                ++i;
                pos = input.indexOf(",");
            }
            pos = input.indexOf("]");
            output[i] = input.substring(0,pos);
                    
        }
        

        return output;
    }
    
    public static void main(String args[]){
        Booking b = new Booking();
        
        String input = "{\n" +
"RAP01,\n" +
"XUAT01,\n" +
"[]\n" +
"}";
        b.ReadInfo(input);
        b.PrintInfo();
    }
    
}
