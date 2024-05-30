/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managecinema;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import data.*;

/**
 *
 * @author User
 */

//lỗi khi bấm vào button thoát thì không thể tăt booking screen được
public class DialogInform extends JDialog{
    
    private Color bgColor = new Color(48, 48, 48);
    private Color blur = new Color(21, 19, 23, 110);
    private Color White = new Color(255, 255, 255);
    private Font bigFilmTitle = new Font("Arial", Font.BOLD, 16);
    private Font title26 = new Font("Arial", Font.BOLD, 26);
    private Font filmTitle = new Font("Arial", Font.BOLD, 16);
    private Color btnColor = new Color(223, 33, 68);
    private supportFunc support = new supportFunc();

    public DialogInform(JFrame parent, String title, boolean  model, String username, String userphone, Showtime showtime,int totalPrice, String listChoosen){
        super(parent,title,model);
        // cấu hình các thành phần trong dialog 
        setSize(600,500);
        setLocation(400,100);
        setLayout(new GridLayout(11,1));
       
        //MÃ ĐẶT CHỔ(RANDOM)
        JLabel code = new JLabel();
        code.setFont(filmTitle);
        code.setText("Mã xuất vé: " + support.generateRandomString(10)); // ham tạo một string random
        
        //THÔNG TIN TÊN
        JLabel name = new JLabel();
        name.setFont(filmTitle);
        name.setText("Tên người đặt vé: " + username ); 

        // THÔNG TIN SDT
        JLabel phone = new JLabel();
        phone.setFont(filmTitle);
        phone.setText("Số điện thoại liên lạc: " + userphone); 
        
        //THÔNG TIN TÊN PHIM
        JLabel filmName = new JLabel();
        filmName.setFont(filmTitle);
        filmName.setText("Tên phim: " + showtime.getName());

        //THÔNG TIN XUẤT CHIẾU
        JLabel time = new JLabel();
        time.setFont(filmTitle);
        time.setText("Xuất chiếu: " + showtime.getStartTime() + '-' + showtime.getEndTime());

        //THÔNG TIN THỜI LƯỢNG
        JLabel duration = new JLabel();
        duration.setFont(filmTitle);
        String convertTime = "";
        if(showtime.getDuration() < 60)
            convertTime += showtime.getDuration() + "m";
        else 
            convertTime += showtime.getDuration()/60 + "h " + showtime.getDuration()%60 + "m";
        duration.setText("Thời lượng: " + convertTime);

        //THÔNG TIN RẠP CHIẾU PHIM
        JLabel room = new JLabel();
        room.setFont(filmTitle);
        room.setText("Rạp: " + showtime.getAuditoriumId());
        
        //THÔNG TIN CÁC CHỔ ĐÃ ĐẶT
        JLabel positions = new JLabel();
        positions.setFont(filmTitle);
        positions.setText("Chổ đã chọn: " + listChoosen);
        
        //THÔNG TIN TỔNG TIỀN 
        JLabel price  = new JLabel();
        price.setFont(filmTitle);
        price.setText("Tổng tiền: " + totalPrice + " VNĐ" );

        
        JLabel warning = new JLabel();
        warning.setForeground(btnColor);
        warning.setFont(filmTitle);
        warning.setText("(Vui lòng lưu lại thông tin mã xuất vé để in vé tại rạp phim)");
        
        JButton btnComplete = new JButton("Complete");
        btnComplete.setPreferredSize(new Dimension(300, 40));
        btnComplete.setMinimumSize(new Dimension(300, 40));
        btnComplete.setMaximumSize(new Dimension(300, 40));
        btnComplete.setBackground(btnColor);
        btnComplete.setForeground(White);
        btnComplete.setFont(filmTitle);
        
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
