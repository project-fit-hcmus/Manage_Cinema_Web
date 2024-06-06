/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author User
 */
public class DetailBooking {
    private String userName;
    private String userPhone;
    private String code;
    private String positions;
    
    public void setUserName(String name){this.userName = name;}
    public void setUserPhone(String phone){this.userPhone = phone;}
    public void setCode(String code){ this.code = code;}
    public void setPositions(String positions){this.positions = positions;}
    
    public String getUserName(){return this.userName;}
    public String getUserPhone(){return this.userPhone;}
    public String getCode(){return this.code;}
    public String getPositions(){return this.positions;}
    
    //input like 
    //{
    //QLQ8518170,
    //nguyen thi nhu y,
    //0796728944,
    //A1,A2,A3
    //}
    public void ReadInfo(String input){
        int pos = input.indexOf(",");
        this.code = input.substring(2,pos);
        input = input.substring(pos+2);
        
        pos = input.indexOf(",");
        this.userName = input.substring(0,pos);
        input = input.substring(pos+2);
        
        pos = input.indexOf(",");
        this.userPhone = input.substring(0,pos);
        input = input.substring(pos+2);
        
        pos = input.indexOf("}");
        this.positions = input.substring(0,pos-1);
        
    }
    
    public void PrintInfo(){
        System.out.println("Code: " + this.code);
        System.out.println("User Name: " + this.userName);
        System.out.println("User Phone: " + this.userPhone);
        System.out.println("Positions: " + this.positions);
    }
//    
//    public static void main(String[] args){
//        DetailBooking d = new DetailBooking();
//        d.ReadInfo("{\n" +
//"QLQ8518170,\n" +
//"nguyen thi nhu y,\n" +
//"0796728944,\n" +
//"A1,A2,A3\n" +
//"}");
//        d.PrintInfo();
//    }
    
}
