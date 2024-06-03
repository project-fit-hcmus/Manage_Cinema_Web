/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author User
 */

//TEST SUCCESS
public class Showtime {
    private String id;
    private String name;
    private String coverImg;
    private Double rating;
    private int duration;
    private String start;
    private String end;
    private String auditoriumId;
    
    public String getShowtimeId(){return this.id;}
    public String getName(){return this.name;}
    public String getCoverImg(){return this.coverImg;}
    public Double getRating(){return this.rating;}
    public int getDuration() { return this.duration;}
    public String getStartTime(){return this.start;}
    public String getEndTime(){return this.end;}
    public String getAuditoriumId(){return this.auditoriumId;}
    
    public void setId(String value){this.id = value;}
    public void setName(String value){this.name = value;}
    public void setCoverImg(String value){this.coverImg = value;}
    public void setRating(Double value){this.rating = value;}
    public void setDuration(int value) { this.duration = value;}
    public void setStart(String value){this.start = value;}
    public void setEnd(String value){this.end = value;}
    public void setAuditoriumId(String value){this.auditoriumId = value;}
    

    public void readInfo(String input) {
        int pos = input.indexOf(",");
        id = input.substring(2, pos);
        input = input.substring(pos + 2);
        System.out.println(id);

        pos = input.indexOf(",");
        name = input.substring(0, pos);
        input = input.substring(pos + 2);
        System.out.println(name);

        
        pos = input.indexOf(",");
        coverImg = input.substring(0, pos);
        input = input.substring(pos + 2);
        System.out.println(coverImg);

        pos = input.indexOf(",");
        rating = Double.parseDouble(input.substring(0, pos));
        input = input.substring(pos + 2);
        System.out.println(rating);


        pos = input.indexOf(",");
        duration = Integer.parseInt(input.substring(0, pos));
        input = input.substring(pos + 2);
        System.out.println(duration);

        
        pos = input.indexOf(",");
        start = input.substring(0, pos);
        input = input.substring(pos + 2);
        System.out.println(start);

        pos = input.indexOf(",");
        end = input.substring(0, pos);
        input = input.substring(pos + 2);
        System.out.println(end);
        
        pos = input.indexOf(",");
        auditoriumId = input.substring(0, pos);
        System.out.println(auditoriumId);
    }

    public void printInfo() {
        System.out.println("Id: " + id);
        System.out.println("Name: " + name);
        System.out.println("CoverImage URL: " + coverImg);
        System.out.println("Rating: " + rating);
        System.out.println("Duration: " + duration);
        System.out.println("Start: " + start);
        System.out.println("End: " + end);
        System.out.println("Auditorium Id: " + auditoriumId);
    }
    public static void main(String args[]){
        Showtime t = new Showtime();
        String input = "{\n" +
"WFZK07,\n" +
"AVATAR The Way of Water 2022,\n" +
"D:\\1. HCMUS\\JAVA\\Practices\\Manage_Cinema_Web\\ManageCinema\\src\\media\\coverImg_Avatar.png,\n" +
"7.8,\n" +
"192,\n" +
"7:0,\n" +
"10:12,\n" +
"RAP01\n" +
"}";
        t.readInfo(input);
        t.printInfo();
        
    }
}
