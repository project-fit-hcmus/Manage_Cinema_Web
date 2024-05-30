/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package managecinema;
import data.*;

/**
 *
 * @author User
 */
public class ManageCinema {
    private handleData handle = new handleData();
    private String dir = System.getProperty("user.dir"); 
    
    //data like: RESERVED/RAP02/XUAT02/A3,A4/2/0
    public String solveBooking(String input){
        // bỏ tín hiệu RESERVED
        int pos = input.indexOf("/");
        input = input.substring(pos+1);
        
        // ĐỌC THÔNG TIN RẠP CHIẾU
        pos = input.indexOf("/");
        String auditodiumId = input.substring(0,pos);
        input = input.substring(pos + 1);
        
        // ĐỌC THÔNG TIN XUẤT CHIẾU
        pos = input.indexOf("/");
        String showtimeId = input.substring(0,pos);
        input = input.substring(pos+1);

        
        // ĐỌC DANH SÁCH CÁC GHẾ ĐÃ CHỌN
        pos = input.indexOf("/");
        String listPosition = input.substring(0,pos);
        input = input.substring(pos+1);

        
        // ĐỌC SỐ LƯỢNG CHỌN THỰC TẾ
        pos = input.indexOf("/");
        int real = Integer.parseInt(input.substring(0,pos));
        input = input.substring(pos+1);

        
        //ĐỌC SỐ LƯỢNG CHỌN ĐẦU VÀO 
        int num = Integer.parseInt(input);

        if(num != real)
            return "Vui long chon dung so ghe da chon";
        
        //KIỂM TRA CÁC CHỔ CHỌN ĐẶT CÓ CÒN TRỐNG
        java.util.List<Booking> listBooking = handle.readBookingStatus(dir+"/src/data/booking.txt");
        Booking single = null;
        int index = 0;
        for(int i = 0; i < listBooking.size(); ++i){
            if(listBooking.get(i).getAuditoriumId().contains(auditodiumId) && listBooking.get(i).getShowtimeId().contains(showtimeId)){
                single = listBooking.get(i);
                index = i;
                break;
            }
        }
        
        for(int i = 0; i < single.getReserved().length; ++i){
            if(listBooking.contains(single.getReserved()[i]))
                return "Cho da duoc dat tu truoc!!!";
        }
        
        //CẬP NHẬT LẠI DANH SÁCH CHỔ ĐẶT TRONG FILE BOOKING.TXT
        String[] newReserved = new String[single.getReserved().length + real];
        int i;
        // CẬP NHẬT DANH SÁCH ĐẶT CHỔ CŨ
        for(i = 0 ; i < single.getReserved().length; ++i){
            newReserved[i] = single.getReserved()[i];
        }
        // THÊM DANH SÁCH CHỔ ĐẶT MỚI
        while(!listPosition.isEmpty()){
            pos = listPosition.indexOf(",");
            if(pos != -1){
                newReserved[i] = listPosition.substring(0,pos);
                ++i;
                listPosition = listPosition.substring(pos+1);
            }else {
                newReserved[i] = listPosition;
                listPosition = "";
                
            }
        }
        listBooking.get(index).setReserved(newReserved);
        
        handle.updateBookingFile(dir + "/src/data/booking.txt", listBooking);
       return "true";
    }
    
}
