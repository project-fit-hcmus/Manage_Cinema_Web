/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package managecinema;
import data.*;
import java.util.*;
import data.handleData;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author User
 */
public class ManageCinema {
    private handleData handle = new handleData();
    private String dir = System.getProperty("user.dir"); 
    
    //data like: RESERVED/RAP02/XUAT02/A3,A4/2/0/Nguyễn Thị Như Ý /0796728944/AFC1028365
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
        pos = input.indexOf("/");
        int num = Integer.parseInt(input.substring(0,pos));
        input = input.substring(pos+1);
        
        // đọc tên 
        pos = input.indexOf("/");
        String name = input.substring(0,pos);
        input = input.substring(pos+1);
        
        // đọc số điện thoại
        pos = input.indexOf("/");
        String phone = input.substring(0,pos);
        input = input.substring(pos+1);
        
        //đọc mã đặt chổ 
        String code = input;
        
         // update detail booking 
        DetailBooking singleDetail = new DetailBooking();
        singleDetail.setCode(code);
        singleDetail.setUserName(name);
        singleDetail.setUserPhone(phone);
        singleDetail.setPositions(listPosition);  
        singleDetail.setAuditoriumId(auditodiumId);
        singleDetail.setShowtimeId(showtimeId);

        if(num != real)
            return "Vui long chon dung so ghe da chon !!!";
        if(num == 0)
            return "Vui long chon so luong ghe de tien hanh dat !!!";
        if(name.contains("Nhập tên của bạn"))
            return "Vui long nhap ten nguoi dat ve !!!"; 
        if(phone.contains("Nhập số điện thoại của bạn"))
            return "Vui long nhap so dien thoai lien lac !!!"; 
        
        //KIỂM TRA CÁC CHỔ CHỌN ĐẶT CÓ CÒN TRỐNG
//        java.util.List<Booking> listBooking = handle.readBookingStatus(dir+"/src/data/booking.txt");
        java.util.List<Booking> listBooking = handle.readBookingStatus(dir+"/booking.txt");

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
            if(listPosition.contains(single.getReserved()[i]))
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
        
//        List<DetailBooking> listDetail = handle.readDetailBooking(dir + "/src/data/detail.txt");
        List<DetailBooking> listDetail = handle.readDetailBooking(dir + "/detail/txt");
       
        listDetail.add(singleDetail);
//        handle.updateDetailBookingFile(dir+"/src/data/detail.txt", listDetail);
        handle.updateDetailBookingFile(dir + "/detail.txt", listDetail);
        
//        handle.updateBookingFile(dir + "/src/data/booking.txt", listBooking);
        handle.updateBookingFile(dir + "/booking.txt", listBooking);

       return "true";
    }
    
    // cấu hình rạp chiếu phim (Auditorium)
    // giả sử việc cấu hình sẽ được thực hiện khi chưa có ai đặt vé (file booking đang trống)
    public String setupAuditorium(String id, int row, int col, boolean  VIP, String fromVIP, String toVIP, long priceVIP, boolean couple, String fromCouple, String toCouple, long priceCouple){
        // kiểm tra thông tin muốn thay đổi 
        if(col > 20)
            return "So cot khong duoc lon hon 20!!!";
        if(VIP == true){
            // kiểm tra vị trí bắt đầu và kết thúc 
            char alpBegin = fromVIP.charAt(0);
            char alpEnd = toVIP.charAt(0);
            int numBegin = Integer.parseInt(fromVIP.substring(1));
            int numEnd = Integer.parseInt(toVIP.substring(1));
            
            if(alpBegin > alpEnd || numBegin > numEnd)
                return "VIP: vi tri bat dau khong duoc lon hon vi tri ket thuc";
            if((int)(alpBegin-65) >= row || (int)(alpEnd-65) >= row || numBegin > col || numEnd > col )
                return "VIP: Vi tri bat dau/ket thuc nam ngoai pham vi cua rap chieu phim";            
        }
        if(couple == true){
            char alpBegin = fromCouple.charAt(0);
            char alpEnd = toCouple.charAt(0);
            int numBegin = Integer.parseInt(fromCouple.substring(1));
            int numEnd = Integer.parseInt(toCouple.substring(1));
            
            if(alpBegin > alpEnd || numBegin > numEnd)
                return "COUPLE: Vi tri bat dau khong duoc lon hon vi tri ket thuc";
            if((int) (alpBegin - 65) >= row || (int)(alpEnd - 65) >= row || numBegin > col || numEnd > col)
                return "COUPLE: Vi tri bat dau/ket thuc nam ngoai pham vi cua rap chieu phim";
        }

        //đọc thông tin rạp các rạp chiếu mặc định
        List<auditorium> raw = handle.readAuditoriumFile(dir + "/src/data/auditorium.txt");
//        List<auditorium> raw = handle.readAuditoriumFile(dir + "/auditorium.txt");

        //tìm thông tin id rạp 
        int i;
        for( i = 0 ; i < raw.size(); ++i)
            if(raw.get(i).getID().contains(id))
                break;
        // trường hợp người dùng muốn update thông tin một rạp đã có 
        if(i < raw.size()){
            raw.get(i).setRow(row);
            raw.get(i).setCol(col);
            if(VIP == true){
                raw.get(i).getVIP().setStart(fromVIP);
                raw.get(i).getVIP().setEnd(toVIP);
                raw.get(i).getVIP().setPrice(priceVIP);
            }
            if(couple == true){
                raw.get(i).getCouple().setStart(fromCouple);
                raw.get(i).getCouple().setEnd(toCouple);
                raw.get(i).getCouple().setPrice(priceCouple);
            }
        }else{              // trường hợp người dùng muốn tạo thông tin một rạp mới 
            auditorium single = new auditorium();
            single.setID(id);
            single.setRow(row);
            single.setCol(col);
            
            kind vipKind = new kind();
            vipKind.setName("VIP");
            vipKind.setStart(fromVIP);
            vipKind.setEnd(toVIP);
            vipKind.setPrice(priceVIP);
            single.setVIP(vipKind);
            
            kind coupleKind = new kind();
            coupleKind.setName("COUPLE");
            coupleKind.setStart(fromCouple);
            coupleKind.setEnd(toCouple);
            coupleKind.setPrice(priceCouple);
            single.setCouple(coupleKind);
            raw.add(single);
        }
        
        handle.updateAuditoriumFile(dir + "/src/data/auditorium.txt", raw);
//        handle.updateAuditoriumFile(dir + "/auditorium.txt", raw);

        return "true";
        
    }

    // thêm xuất chiếu phim 
    public String setupShowtime(String title, String imgUrl, int duration, double rating, String hour, String AudiId ){
        String id = randomId();
        //đọc danh sách các film hiện có 
//        List<Showtime> raw = handle.readShowtimeFile(dir + "/src/data/showtime.txt");
        List<Showtime> raw = handle.readShowtimeFile(dir + "/showtime.txt");
        
        // kiểm tra xuất chiếu và rạp chiếu có hợp lệ không
        String endTime = PlusTime(hour, duration);
        for(int i = 0 ; i < raw.size(); ++i){
            if(raw.get(i).getAuditoriumId().contains(AudiId)){
                boolean check = true;
                if(compareTime(hour, raw.get(i).getStartTime()) == 1 && compareTime(hour, raw.get(i).getEndTime()) == -1 )
                    check = false;
                if(compareTime(endTime, raw.get(i).getStartTime()) == 1 && compareTime(endTime, raw.get(i).getEndTime()) == -1 )
                    check = false;
                if(compareTime(hour, raw.get(i).getStartTime()) == -1 && compareTime(endTime, raw.get(i).getEndTime()) == 1)
                    check = false;
                if(check == false){
                    return "Trung lich chieu"; 
                }
            }
        }
        
        
        
        Showtime single = new Showtime();
        single.setAuditoriumId(AudiId);
        single.setId(id);
//        single.setCoverImg(imgUrl);
        single.setDuration(duration);
        single.setEnd(endTime);
        single.setStart(hour);
        single.setRating(rating);
        single.setName(title);
        //check coverImg 
        ImageIcon check ;
        boolean Invalid = false;
        try{
            BufferedImage image = ImageIO.read(new File("image.jpg"));
            check = new ImageIcon(imgUrl);
        }catch(IOException errr) {
            System.out.println(errr);
            Invalid = true;
        }
        if(Invalid){
            System.out.println("is null");
            single.setCoverImg("UNKNOWN");
        }
        else single.setCoverImg(imgUrl);

        raw.add(single);
//        handle.updateShowtimeFile(dir + "/src/data/showtime.txt", raw);
        handle.updateShowtimeFile(dir + "/showtime.txt", raw);

//        List<Booking> listBooking = handle.readBookingStatus(dir + "/src/data/booking.txt");
        List<Booking> listBooking = handle.readBookingStatus(dir + "/booking.txt");

        Booking res = new Booking();
        res.setAuditoriumId(AudiId);
        res.setShowtimeId(id);
        res.setReserved(new String[0]);
        listBooking.add(res);
//        handle.updateBookingFile(dir + "/src/data/booking.txt", listBooking);
        handle.updateBookingFile(dir + "/booking.txt", listBooking);

        return "true";
    }
    
    // RANDOM ID XUẤT CHIẾU GỒM 4 KÝ TỰ IN HOA SAU ĐÓ LÀ 2 CHỮ SỐ 
    public static String randomId(){
        String character = "ABCDEFJHIJKLMNOPQRSTUVWXYZ";
        String number = "0123456789";
        StringBuilder sb = new StringBuilder(6);
        Random random = new Random();
        for(int i = 0; i < 6; ++i){
            int index;
            char randomChar;
            if( i <= 3 ){
                index = random.nextInt(character.length());
                randomChar = character.charAt(index);
            }
            else{
                index = random.nextInt(number.length());
                randomChar = number.charAt(index);
            }
            sb.append(randomChar);
        }
        return sb.toString();
    }
    
    // SO SÁNH 2 GIỜ
    public static int compareTime(String time1, String time2){
        int pos = time1.indexOf(":");
        int hour1 = Integer.parseInt(time1.substring(0,pos));
        int min1 = Integer.parseInt(time1.substring(pos+1));
        pos = time2.indexOf(":");
        int hour2 = Integer.parseInt(time2.substring(0,pos));
        int min2 = Integer.parseInt(time2.substring(pos+1));
        if(hour1 > hour2) return 1;
        else if(hour1 < hour2) return -1;
        else {
            if(min1 > min2) return 1;
            else if(min1 == min2) return 0;
            else return -1;
        }
        
    }
    
    public static String PlusTime(String time, int duration){
        String output = "";
        int pos = time.indexOf(":");
        int hour = Integer.parseInt(time.substring(0,pos));
        int min = Integer.parseInt(time.substring(pos+1));
        hour += (duration/60);
        min += duration%60;
        if(min > 60){
            hour += (min/60);
            min = min % 60;
        }
        String strHour = "";
        if(hour < 10) strHour += "0" ;
        strHour += String.valueOf(hour);
        String strMin = "";
        if(min < 10) strMin += "0";
        strMin += String.valueOf(min);
        
        output += strHour + ":" + strMin;
        return output;
    }

}
