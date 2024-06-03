/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managecinema;

/**
 *
 * @author User
 */

import java.util.ArrayList;
import java.util.*;
import java.io.*;
import data.*;

// xây dựng các chức năng liên quan đến đọc và lưu trữ dữ liệu trong các file .txt 
public class handleData {
    //success
    public List<auditorium> readAuditoriumFile(String filename) {
        List<auditorium> output = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            String audi = "";
            while (line != null && !line.isEmpty()) {
                audi += line + '\n';
                if (line.charAt(0) == '}') {
                    auditorium single = new auditorium();
                    single.readInfo(audi);
                    output.add(single);
                    audi = "";
                }
                line = br.readLine();
            }
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
//            return output;
        }
        return output;
    }
    
    public List<Showtime> readShowtimeFile(String filename){
        List<Showtime> output = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            String show = "";
            while(line != null && !line.isEmpty()){
                System.err.println(line);
                show += line + '\n';
                if(line.charAt(0) == '}'){
                    Showtime single = new Showtime();
                    single.readInfo(show);
                    single.printInfo();
                    output.add(single);
                    show = "";
                }
                line = br.readLine();
            }
        }catch(Exception e){
//            e.printStackTrace();
            System.out.println(e.getMessage());
//            return output;
        }
        return output;
    }
    
    public List<Booking> readBookingStatus(String filename){
        List<Booking> output = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            String book = "";
            while(line != null && !line.isEmpty()){
                book += line + '\n';
                if(line.charAt(0) == '}'){
                    Booking single = new Booking();
                    single.ReadInfo(book);
                    output.add(single);
                    book = "";
                }
                line = br.readLine();
            }
        }catch(Exception e){
//            e.printStackTrace();
            System.out.println(e.getMessage());
//            return output;
        }
        return output;
    }
    
    public void updateAuditoriumFile(String filename, List<auditorium> data){
        System.out.println("Enter update auditorium file!!!");
        //mở file 
        try{
            BufferedWriter br = new BufferedWriter(new FileWriter(filename));
            for(int i = 0; i < data.size(); ++i){
                br.write("{\n");
                br.write(data.get(i).getID() + ",\n");
                br.write(data.get(i).getRow() + ",\n");
                br.write(data.get(i).getCol() + ",\n");
                br.write("[" + data.get(i).getVIP().getName() + "|" + data.get(i).getVIP().getStart() + "|");
                br.write(data.get(i).getVIP().getEnd() + "|" + data.get(i).getVIP().getPrice() + "],\n");
                br.write("[" + data.get(i).getCouple().getName() + "|" + data.get(i).getCouple().getStart() + "|");
                br.write(data.get(i).getCouple().getEnd() + "|" + data.get(i).getCouple().getPrice() + "],\n");
                br.write("}\n");
                System.out.println(i);
            }
            br.close();
        }catch(Exception e){
//            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    public void updateShowtimeFile(String filename, List<Showtime> data){
        //mở file 
        try{
            BufferedWriter br = new BufferedWriter(new FileWriter(filename));
            for(int i = 0; i < data.size(); ++i){
                br.write("{\n" + data.get(i).getShowtimeId() + ",\n");
                br.write(data.get(i).getName() + ",\n");
                br.write(data.get(i).getCoverImg() + ",\n");
                br.write(data.get(i).getRating()+ ",\n");
                br.write(data.get(i).getDuration()+ ",\n");
                br.write(data.get(i).getStartTime()+ ",\n");
                br.write(data.get(i).getEndTime()+ ",\n");
                br.write(data.get(i).getAuditoriumId()+ ",\n}\n");
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    public void updateBookingFile(String filename, List<Booking> data){
        //mở file 
        try{
            BufferedWriter br = new BufferedWriter(new FileWriter(filename));
            for(int i = 0; i < data.size() ; ++i){
                boolean first = true;
                br.write("{\n" + data.get(i).getAuditoriumId() + ",\n");
                br.write(data.get(i).getShowtimeId() + ",\n[");
                for(int j = 0; j < data.get(i).getReserved().length; ++j){
                    if(j != 0) br.write(",");
                    br.write(data.get(i).getReserved()[j]);
                    
                }
                  
                    
                br.write("]\n}\n");
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String args[]) {
        List<auditorium> output;
        List<Showtime> showList;
        List<Booking> listReserved;
        String dir = System.getProperty("user.dir");

        int pos = dir.lastIndexOf('\\');
        dir = dir.substring(0, pos);

        handleData a = new handleData();
//        output = a.readAuditoriumFile(dir + "\\ManageCinema\\src\\data\\auditorium.txt");
//        for(int i = 0 ; i < output.size(); ++i){
//            System.out.println("The data at " + i + " : ");
//            output.get(i).printInfo();
//            System.out.println("\n");
//        }
        
//        showList = a.readShowtimeFile(dir + "\\ManageCinema\\src\\data\\showtime.txt");
//        for(int i =0 ;i < showList.size(); ++i){
//            System.out.println("The data at " + i + " : ");
//            showList.get(i).printInfo();
//            System.out.println("\n");
//        }
        listReserved = a.readBookingStatus(dir + "\\ManageCinema\\src\\data\\booking.txt");
        for(int i = 0; i < listReserved.size() ; ++i){
            System.out.println("The data at " + i + " : ");
            listReserved.get(i).PrintInfo();
            System.out.println("");
        }
    }
}
