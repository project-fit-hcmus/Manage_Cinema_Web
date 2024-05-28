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
    private int row = 11;
    private int col = 11;
    private boolean reserved[][];
    
    public void setRow(int row){
        this.row = row;
    }
    public void setCol(int col){
        this.col = col;
    }
    public void setAuditoriumId(String inp){
        this.auditoriumId = inp;
    }
    public void setShowtimeId(String inp){
        this.showtimeId = inp;
    }
    public String getShowtimeId(){return this.showtimeId;}
    public String getAuditoriumId(){return this.auditoriumId;}
    public int getRow(){return row;}
    public int getCol(){return col;}
    
    public void ReadInfo(String input){
        reserved = new boolean[row][col];
        int pos = input.indexOf(',');
        auditoriumId = input.substring(2,pos);
        input = input.substring(pos+2);
         
        pos = input.indexOf(',');
        showtimeId = input.substring(0,pos);
        input = input.substring(pos + 2);

        pos = input.indexOf('}');
        input = input.substring(0,pos);
        reserved = readMatrix(input,row, col);
        
        
        
        
    }
    public void PrintInfo(){
        System.out.println("Auditorium: " + auditoriumId + "\n");
        System.out.println("Showtime: " + showtimeId + "\n");
        System.out.println("[" + row + ", " + col + "]" + "\n");
        System.out.println("Status: \n");
        for(int i = 0; i < row; ++i){
            for(int j = 0; j < col; ++j){
                String temp = reserved[i][j] == true ? "reseved":"empty";
                System.out.print(temp + "  ");
            }
            System.out.println("\n");
        }
    }
    
    //input like: [B2,B3,C9,D1,D2,D3,D4]
    public boolean[][] readMatrix(String input, int row, int col){
        boolean[][] output = new boolean[row][col];
        for(int i = 0; i < row; ++i)
            for(int j = 0; j < col; ++j)
                output[i][j] = false;
        int pos = input.indexOf('[');
        input = input.substring(pos+1);     // bỏ qua ký tự "["
        pos = input.indexOf(',');
        while(!input.isEmpty()){
            String temp ;
            if(pos == -1 ){
                pos = input.indexOf(']');
                temp = input.substring(0,pos);
                
            }else{
                temp= input.substring(0,pos);                
            }
            output[(temp.charAt(0)-65)][temp.charAt(1)-48-1] = true;          // temp[1] - 48 - 1 vì cột bắt đầu từ vị trí 0
            input = input.substring(pos+1);
            if(!(input.charAt(0) >= 'A' && input.charAt(0) <= 'Z')) break;
        }
        return output;
    }
    
    public static void main(String args[]){
        Booking b = new Booking();
        b.setRow(11);
        b.setCol(11);
        String input = "{\n" +
"    RAP02,\n" +
"    XUAT02,\n" +
"    [A5]\n" +
"}";
        b.ReadInfo(input);
        b.PrintInfo();
    }
    
}
