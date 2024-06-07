/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

//VD: nội dung của một auditorium như sau
//{
//RAP01,
//10,
//20,
//[VIP|D5|H16|60000],
//[COUPLE|K1|K20|15000],
//}
public class auditorium {
    private String id;
    private int row;
    private int col;
    private kind vip;
    private kind couple;

    public void readInfo(String input) {
        String temp = input;
        int pos = temp.indexOf(",");
        id = temp.substring(2, pos);
        temp = temp.substring(pos + 2);

        pos = temp.indexOf(",");
        row = Integer.parseInt(temp.substring(0, pos));
        temp = temp.substring(pos + 2);

        pos = temp.indexOf(",");
        col = Integer.parseInt(temp.substring(0, pos));
        temp = temp.substring(pos + 2);

        pos = temp.indexOf(",");
        vip = new kind();
        vip.readInfo(temp.substring(0, pos));
        temp = temp.substring(pos + 2);

        pos = temp.indexOf(",");
        couple = new kind();
        couple.readInfo(temp.substring(0, pos));
        temp = temp.substring(pos + 3);
    }

    public void printInfo() {
        System.out.println("ID: " + id);
        System.out.println("Row: " + row);
        System.out.println("Column: " + col);
        vip.PrintInfo();
        couple.PrintInfo();
    }
    
    public String getID(){return this.id;}
    public int getRow(){return this.row;}
    public int getCol(){return this.col;}
    public kind getVIP(){return this.vip;}
    public kind getCouple(){return this.couple;}
    
    public void setID(String value){this.id = value;}
    public void setRow(int value){this.row = value;}
    public void setCol(int value){this.col = value;}
    public void setVIP(kind value){this.vip = value;}
    public void setCouple(kind value){this.couple = value;}
}
