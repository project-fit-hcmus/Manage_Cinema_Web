/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managecinema;
import data.*;
import java.util.*;
import javax.swing.JToggleButton;


/**
 *
 * @author User
 */
public class supportFunc {
    
    //checked -- success
    //N: normal V: VIP C: couple 
    public char[][] createKindMatrix(auditorium audi, int row, int col){
        char[][] output = new char[row][col];
        kind vip = audi.getVIP();
        kind couple = audi.getCouple();
        int startIV = vip.getStart().charAt(0) - 65;
        int startJV = Integer.parseInt(vip.getStart().substring(1)) - 1;
        int endIV = vip.getEnd().charAt(0) - 65;
        int endJV = Integer.parseInt(vip.getEnd().substring(1)) - 1;

        int startIC = couple.getStart().charAt(0) - 65;
        int startJC = Integer.parseInt(couple.getStart().substring(1)) - 1;
        int endIC = couple.getEnd().charAt(0) - 65;
        int endJC = Integer.parseInt(couple.getEnd().substring(1)) - 1;
        for(int i = 0; i < row; ++i)
            for(int j = 0; j < col; ++j){
                if(startIV <= i  && i<= endIV && startJV <= j && j <= endJV)
                    output[i][j] = 'V';
                else if(startIC <= i && i <= endIC && startJC <= j && j <= endJC){
                    output[i][j] = 'C';
                }else 
                    output[i][j] = 'N';
            }
        return output;
    }
    
    //checked -- success
    // true: is taken | false: is empty
    public boolean[][] createStatusMatrix(Booking reserved, int row, int col){

        boolean[][] matrix = new boolean[row][col];
        for(int i = 0; i < row; ++i )
            for(int j = 0; j < col; j++)
                matrix[i][j] = false;
        for(int i = 0; i < reserved.getReserved().length; ++i){
            int x = (int) (reserved.getReserved()[i].charAt(0)- 65);
            int y = Integer.parseInt(reserved.getReserved()[i].substring(1)) - 1;
            matrix[x][y] = true;
        }
        return matrix;
    }

    // TẠO DANH SÁCH ĐẶT CHỔ TỪ DANH SÁCH JTOGGLEBUTTON
    public String createListOfPosition(JToggleButton[][] choosen, int row, int col){
        String output = "";
        for(int i = 0; i < row; ++i)
            for(int j = 0; j < col; ++j)
                if(choosen[i][j].isSelected()){
                    if(!output.isEmpty())
                        output += ",";
                    output += (char)(i+65) + String.valueOf(j+1);
                }
        return output;
    }
    
    //TẠO CHUỖI RANDOM ĐỂ LÀM MÃ ĐẶT CHỔ
    public static String generateRandomString(int length) {
        String character = "ABCDEFJHIJKLMNOPQRSTUVWXYZ";
        String number = "0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; ++i) {
            int index;
            char randomChar;
            if (i < 3) {
                index = random.nextInt(character.length());
                randomChar = character.charAt(index);
            } else {
                index = random.nextInt(number.length());
                randomChar = number.charAt(index);
            }
            sb.append(randomChar);
        }
        return sb.toString();
    }
    
   
}
