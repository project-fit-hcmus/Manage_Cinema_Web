/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;
import managecinema.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import data.*;


public class DialogInform extends JDialog{
    
    private Style style = new Style();
    
    public DialogInform(JFrame parent, String title, boolean  model, String username, String userphone, Showtime showtime,int totalPrice, String listChoosen, String bookingCode){
        super(parent,title,model);
        // cấu hình các thành phần trong dialog 
        setSize(600,500);
        setLocation(400,100);
        setLayout(new GridLayout(11,1));
       
        //MÃ ĐẶT CHỔ(RANDOM)
        JLabel code = new JLabel();
        code.setFont(style.filmTitle);
        code.setText("Mã xuất vé: " + bookingCode); // hàm tạo một string random
        
        //THÔNG TIN TÊN
        JLabel name = new JLabel();
        name.setFont(style.filmTitle);
        name.setText("Tên người đặt vé: " + username ); 

        // THÔNG TIN SDT
        JLabel phone = new JLabel();
        phone.setFont(style.filmTitle);
        phone.setText("Số điện thoại liên lạc: " + userphone); 
        
        //THÔNG TIN TÊN PHIM
        JLabel filmName = new JLabel();
        filmName.setFont(style.filmTitle);
        filmName.setText("Tên phim: " + showtime.getName());

        //THÔNG TIN XUẤT CHIẾU
        JLabel time = new JLabel();
        time.setFont(style.filmTitle);
        time.setText("Xuất chiếu: " + showtime.getStartTime() + '-' + showtime.getEndTime());

        //THÔNG TIN THỜI LƯỢNG
        JLabel duration = new JLabel();
        duration.setFont(style.filmTitle);
        String convertTime = "";
        if(showtime.getDuration() < 60)
            convertTime += showtime.getDuration() + "m";
        else 
            convertTime += showtime.getDuration()/60 + "h " + showtime.getDuration()%60 + "m";
        duration.setText("Thời lượng: " + convertTime);

        //THÔNG TIN RẠP CHIẾU PHIM
        JLabel room = new JLabel();
        room.setFont(style.filmTitle);
        room.setText("Rạp: " + showtime.getAuditoriumId());
        
        //THÔNG TIN CÁC CHỔ ĐÃ ĐẶT
        JLabel positions = new JLabel();
        positions.setFont(style.filmTitle);
        positions.setText("Chổ đã chọn: " + listChoosen);
        
        //THÔNG TIN TỔNG TIỀN 
        JLabel price  = new JLabel();
        price.setFont(style.filmTitle);
        price.setText("Tổng tiền: " + totalPrice + " VNĐ" );

        
        JLabel warning = new JLabel();
        warning.setForeground(style.btnColor);
        warning.setFont(style.filmTitle);
        warning.setText("(Vui lòng lưu lại thông tin mã xuất vé để in vé tại rạp phim)");
        
        JButton btnComplete = new JButton("Complete");
        btnComplete.setPreferredSize(new Dimension(300, 40));
        btnComplete.setMinimumSize(new Dimension(300, 40));
        btnComplete.setMaximumSize(new Dimension(300, 40));
        btnComplete.setBackground(style.btnColor);
        btnComplete.setForeground(style.White);
        btnComplete.setFont(style.filmTitle);
        
        btnComplete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                parent.dispose();
            }
        });

        add(code);
        add(name);
        add(phone);
        add(filmName);
        add(time);
        add(duration);
        add(room);
        add(positions);
        add(price);
        add(warning);
        add(btnComplete);

    }
}
