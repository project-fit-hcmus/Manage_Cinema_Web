/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managecinema;
import data.*;
import data.handleData;
import client.supportFunc;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import javax.swing.plaf.basic.BasicButtonUI;

public class Server extends JFrame{
    //layout setup 
    private JFrame mainFrame, detailFrame;
    private JPanel headerPanel, contentPanel;
    private Image origin, resize;
    private CardLayout card;

    //STYLE 
    Style style = new Style();
    private supportFunc support = new supportFunc();
    private final int CHAIR_WIDTH = 55;
    private final int CHAIR_HEIGHT = 30;
    
    //NETWORKING SETUP 
    private ServerSocket serverSocket;
    private static final int PORT = 8080;
    private String dir = "";
    private ManageCinema manageServer = new ManageCinema();
    private handleData handleServer = new handleData();
    
    public JPanel createContentPanel() {
        JPanel output = new JPanel();
        card = new CardLayout();
        output.setLayout(card);
        output.add("home",createHomeScreen());        
        output.add("stage", createStageSetting());
        output.add("auditorium", createShowtimeSetting());
        return output;
    }
    
    // tạo màn hình xem danh sách phim đang chiếu
    public JScrollPane createHomeScreen(){     
        //prepare data
//        java.util.List<Showtime> raw = handleServer.readShowtimeFile(dir + "//src//data//showtime.txt");
        java.util.List<Showtime> raw = handleServer.readShowtimeFile(dir + "//showtime.txt");

        JPanel content = new JPanel();
        content.setLayout(new FlowLayout(FlowLayout.LEFT));
        content.setBackground(style.bgColor);
        //set preference 
        int temp = (int)(raw.size()/4);
        if(raw.size()%4 != 0 )
            temp ++;
        content.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
            (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2)*(temp+1)));
        
        for(int i = 0; i < raw.size(); ++i){
            content.add(createPosterFilm(raw.get(i)));
        }
        
        JScrollPane home = new JScrollPane(content);
        home.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        home.setBackground(style.bgColor);
        home.getVerticalScrollBar().setUnitIncrement(16);
        home.getVerticalScrollBar().setBlockIncrement(64);   
        return home;
    }
    public JPanel createPosterFilm(Showtime data){
        
        JPanel output = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        output.setLayout(layout);
        output.setBackground(style.transparentColor);
        output.setPreferredSize(new Dimension(300,420));
        output.setMinimumSize(new Dimension(300,420));
        output.setMaximumSize(new Dimension(300,420));


        JLabel filmArea = new JLabel();
        ImageIcon filmImg;
        if(data.getCoverImg().equals("UNKNOWN"))
            filmImg = setScale(222, 312, new ImageIcon(getClass().getResource("/media/unknown.jpg")));
        else
            filmImg= setScale(222, 312, new ImageIcon(data.getCoverImg()));
        filmArea.setIcon(filmImg);

        JLabel titleArea = new JLabel();
        titleArea.setText(data.getName());
        titleArea.setForeground(style.White);
        titleArea.setFont(style.title16);

        JLabel ratingArea = new JLabel();
        ImageIcon ratingImg = setScale(20, 20, new ImageIcon(getClass().getResource("/media/rating.png")));
        ratingArea.setFont(style.title18);
        ratingArea.setForeground(style.subTextColor);
        ratingArea.setIconTextGap(10);
        ratingArea.setIcon(ratingImg);
        ratingArea.setText(data.getRating().toString() + "/10");

        JLabel durationArea = new JLabel();
        ImageIcon durationImg = setScale(20, 20, new ImageIcon(getClass().getResource("/media/duration.png")));
        durationArea.setFont(style.title18);
        durationArea.setForeground(style.subTextColor);
        durationArea.setIconTextGap(10);
        

        durationArea.setIcon(durationImg);
        String duration ;
        if(data.getDuration() < 60){
            duration = String.valueOf(data.getDuration()) + "m";
        }else{
            duration = String.valueOf(data.getDuration()/60) + "h " + String.valueOf(data.getDuration()%60) + "m" ;
        }
        durationArea.setText(duration);
        
        JPanel wholeArea = new JPanel();
        wholeArea.setLayout(new FlowLayout(FlowLayout.CENTER));
        wholeArea.setPreferredSize(style.singlefilmDimen);
        wholeArea.setBackground(style.transparentColor);
        wholeArea.add(ratingArea);
        wholeArea.add(Box.createRigidArea(new Dimension(20, 0)));
        wholeArea.add(durationArea);

        JButton btnchoose = new JButton();
        btnchoose.setText(data.getStartTime() + " - " + data.getEndTime());
        btnchoose.setPreferredSize(style.singlefilmDimen);
        btnchoose.setMinimumSize(style.singlefilmDimen);
        btnchoose.setMaximumSize(style.singlefilmDimen);
        btnchoose.setBackground(style.btnColor2);
        btnchoose.setForeground(style.White);
        btnchoose.setFont(style.title16);

        btnchoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDetailScreen(data);
            }
        });
        
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.gridx = 0; cs.gridy = 0;
        output.add(filmArea,cs);
        cs.gridy = 1;
        output.add(wholeArea,cs);
        cs.gridy = 2;
        output.add(titleArea,cs);
        cs.gridy = 3;
        output.add(btnchoose,cs);
        return output;
    }
    
    public JPanel PosterForDetail(Showtime input){
        JPanel output = new JPanel();
        output.setBackground(style.bgColor);
        output.setPreferredSize(style.BigPosterDimen);
        output.setMinimumSize(style.BigPosterDimen);
        output.setMaximumSize(style.BigPosterDimen);
        
        JPanel poster = new JPanel();
        poster.setBackground(style.bgColor);
        GridBagLayout layout = new GridBagLayout();
        poster.setLayout(layout);
        GridBagConstraints cs = new GridBagConstraints();
        
        JLabel avatarArea = new JLabel();
        ImageIcon mainImg;
        if(input.getCoverImg().equals("UNKNOWN"))
            mainImg = setScale(320, 523, new ImageIcon(getClass().getResource("/media/unknown.jpg")));
        else 
            mainImg = setScale(320, 523, new ImageIcon(input.getCoverImg()));
        avatarArea.setIcon(mainImg);

        JLabel ratingArea = new JLabel();
        ratingArea.setFont(style.title18);
        ratingArea.setForeground(style.subTextColor);
        ImageIcon ratingImg = setScale(36, 32, new ImageIcon(getClass().getResource("/media/rating.png")));
        ratingArea.setIcon(ratingImg);
        ratingArea.setText(input.getRating() + "/10");

        JLabel durationArea = new JLabel();
        durationArea.setFont(style.title18);
        durationArea.setForeground(style.subTextColor);
        ImageIcon durationImg = setScale(28, 28, new ImageIcon(getClass().getResource("/media/duration.png")));
        durationArea.setIcon(durationImg);
        String duration;
        if(input.getDuration() < 60){
            duration = input.getDuration() + "m";
        }else{
            duration = input.getDuration()/60 + "h " + input.getDuration()%60 + "m" ;
        }
        durationArea.setText(duration);
        
        JLabel groupAudi = new JLabel();
        ImageIcon audiIcon = setScale(28,28, new ImageIcon(getClass().getResource("/media/auditorium.png")));
        groupAudi.setFont(style.filmTitle);
        groupAudi.setForeground(style.subTextColor);
        groupAudi.setIcon(audiIcon);
        groupAudi.setText(input.getAuditoriumId());

        JPanel group = new JPanel();
        group.setLayout(new FlowLayout(FlowLayout.CENTER));
        group.setBackground(style.bgColor);
        group.setPreferredSize(style.bigPoster);
        group.add(ratingArea);
        group.add(Box.createRigidArea(new Dimension(5, 0)));
        group.add(durationArea);
        group.add(Box.createRigidArea(new Dimension(5, 0)));
        group.add(groupAudi);
        
        JLabel title = new JLabel();
        title.setForeground(style.White);
        title.setFont(style.title20);
        title.setLayout(new FlowLayout(FlowLayout.CENTER));
        title.setPreferredSize(style.bigPoster);
        title.setMinimumSize(style.bigPoster);
        title.setMaximumSize(style.bigPoster);
        title.setText(input.getName());

        JButton time = new JButton(input.getStartTime() + " - " + input.getEndTime());
        time.setPreferredSize(style.bigPoster);
        time.setMinimumSize(style.bigPoster);
        time.setMaximumSize(style.bigPoster);
        time.setBackground(style.btnColor2);
        time.setForeground(style.White);
        time.setFont(style.filmTitle);
        
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.gridx = 0; cs.gridy = 0;
        poster.add(Box.createRigidArea(new Dimension(0,100)));
        cs.gridy = 1;
        poster.add(avatarArea,cs);
        cs.gridy = 2;
        poster.add(group,cs);
        cs.gridy = 3;
        poster.add(title,cs);
        cs.gridy = 4;
        poster.add(time,cs);


        output.add(poster);
        revalidate();
        repaint();
        return output;
    }
    
    public JPanel BookingStatus(Showtime data, java.util.List<Booking> listBooking, java.util.List<auditorium> listAudi){
        System.err.println("in booking status create");
        JPanel screen = new JPanel();
        screen.setLayout(new BoxLayout(screen,BoxLayout.Y_AXIS));
        screen.setPreferredSize(style.contentpart2);
        screen.setMinimumSize(style.contentpart2);
        screen.setMaximumSize(style.contentpart2);
        screen.setBackground(style.bgColor);
        
        //PREPARE DATA 
        int row = 0, col = 0;
        char[][] kindOfChair = null;
        boolean[][] statusBooking = null;
        
        long Pvip = 0;
        long Pcouple = 0;
        
        for(int i = 0 ; i < listAudi.size(); ++i){
            if(data.getAuditoriumId().contains(listAudi.get(i).getID())){
                row = listAudi.get(i).getRow();
                col = listAudi.get(i).getCol();
                kindOfChair = support.createKindMatrix(listAudi.get(i), row, col);
                Pvip = listAudi.get(i).getVIP().getPrice();
                Pcouple = listAudi.get(i).getCouple().getPrice();
                break;
            }
        }
        
        
       
        
        for(int i = 0; i < listBooking.size(); ++i){
            if(data.getAuditoriumId().contains(listBooking.get(i).getAuditoriumId()) && data.getShowtimeId().contains(listBooking.get(i).getShowtimeId())){
                statusBooking = support.createStatusMatrix(listBooking.get(i), row, col);
                break;
            }
        }
        
        // LIST SEAT 
        JPanel listSeat = new JPanel();
        listSeat.setPreferredSize(new Dimension((col) * CHAIR_WIDTH, (row) * CHAIR_HEIGHT));
        listSeat.setMinimumSize(new Dimension((col) * CHAIR_WIDTH, (row) * CHAIR_HEIGHT));
        listSeat.setMaximumSize(new Dimension((col) * CHAIR_WIDTH, (row) * CHAIR_HEIGHT));
        listSeat.setBackground(style.transparentColor);
        listSeat.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        
        for (int i = 0; i < row; ++i) {
          
            for (int j = 0; j < col; ++j) {
                String color = "";
                if(statusBooking[i][j] == true)
                    color = "gray";
                else if(kindOfChair[i][j] == 'N')
                    color = "blue";
                else if(kindOfChair[i][j] == 'V')
                    color = "red";
                else if(kindOfChair[i][j] == 'C')
                    color = "pink";
                            
                listSeat.add(printChair(color));
                if (j >= col){
                    listSeat.add(new JPanel());
                }
            }


        }

        // note seat
        JLabel note = new JLabel();
        note.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 2 / 5, 120));
        note.setMinimumSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 2 / 5, 120));
        note.setMaximumSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 2 / 5, 120));
        ImageIcon noteImg = setScale(340, 100, new ImageIcon(getClass().getResource("/media/note.png")));
        note.setIcon(noteImg);

        //CALCULATE BOX RIGDID 
        int box = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - row*CHAIR_HEIGHT - 120)/2;
        screen.add(Box.createRigidArea(new Dimension(0,box)));
        screen.add(listSeat);
        screen.add(note);
             
        return screen;
    }
    
    //color maybe "red","gray","blue","pink"
    public JButton printChair(String color){
        JButton output = new JButton();
        output.setUI(new BasicButtonUI());
        output.setBackground(style.bgColor);
        
        if (color.equals("red")) {
            ImageIcon chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("/media/VIP_seat.png")));
            output.setIcon(chairImg);
        } else if (color.equals("pink")) {
            ImageIcon chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("/media/couple_seat.png")));
            output.setIcon(chairImg);
        } else if (color.equals("blue")) {
            ImageIcon chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("/media/standard_seat.png")));
            output.setIcon(chairImg);
        } else if (color.equals("gray")) {
            ImageIcon chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("/media/reserved_seat.png")));
            output.setIcon(chairImg);
        }
        return output;
    }
    
    
    public void showDetailScreen(Showtime data){
        //prepare data 
//        java.util.List<Booking> listBooking = handleServer.readBookingStatus(dir + "//src//data//booking.txt");
//        java.util.List<auditorium> listAudi = handleServer.readAuditoriumFile(dir + "//src//data/auditorium.txt");
        
        java.util.List<Booking> listBooking = handleServer.readBookingStatus(dir + "//booking.txt");
        java.util.List<auditorium> listAudi = handleServer.readAuditoriumFile(dir + "//auditorium.txt");

        detailFrame = new JFrame();
        detailFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        detailFrame.setBackground(style.bgColor);
        detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailFrame.getContentPane().setLayout(new BoxLayout(detailFrame.getContentPane(),BoxLayout.PAGE_AXIS));
        detailFrame.setLayout(new GridBagLayout());
        
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.gridx = 0; cs.gridy = 0;
        detailFrame.add(PosterForDetail(data),cs);
        cs.gridx = 1;
        detailFrame.add(BookingStatus(data, listBooking, listAudi),cs);
        detailFrame.setFocusable(true);
        detailFrame.setVisible(true);
    }
    

    //tạo màn hình cài đặt sân khấu
    public JPanel createStageSetting() {
        JPanel output = new JPanel();
        output.setLayout(new BoxLayout(output, BoxLayout.Y_AXIS));
        output.setBackground(style.bgColor);

        JLabel title = new JLabel();
        title.setText("Cấu hình sân khấu");
        title.setFont(style.headerTitle);
        title.setForeground(style.White);
        
        JTextField audiCombo = new JTextField("Chọn rạp muốn cấu hình");
        audiCombo.setFont(style.title18);
        audiCombo.setForeground(style.lightGray);
        audiCombo.setPreferredSize(style.textFieldDimen);
        audiCombo.setMinimumSize(style.textFieldDimen);
        audiCombo.setMaximumSize(style.textFieldDimen);
        audiCombo.setBackground(style.bgLighGray);

        JPanel groupColRow = new JPanel();
        groupColRow.setLayout(new BoxLayout(groupColRow, BoxLayout.X_AXIS));
        groupColRow.setBackground(style.bgColor);
        groupColRow.setPreferredSize(style.textFieldDimen);
        groupColRow.setMinimumSize(style.textFieldDimen);
        groupColRow.setMaximumSize(style.textFieldDimen);

        JTextField txtRow = new JTextField("Số lượng hàng");
        txtRow.setFont(style.title18);
        txtRow.setForeground(style.lightGray);
        txtRow.setBackground(style.bgLighGray);

        JTextField txtCol = new JTextField("Số lượng cột");
        txtCol.setFont(style.title18);
        txtCol.setForeground(style.lightGray);
        txtCol.setBackground(style.bgLighGray);

        groupColRow.add(txtRow);
        groupColRow.add(Box.createRigidArea(new Dimension(40, 0)));
        groupColRow.add(txtCol);

        JPanel groupVIP = new JPanel();
        groupVIP.setLayout(new GridBagLayout());
        groupVIP.setBackground(style.bgColor);
        groupVIP.setPreferredSize(new Dimension(800,80));
        groupVIP.setMaximumSize(new Dimension(800,80));
        groupVIP.setMinimumSize(new Dimension(800,80));


        JCheckBox VIPCheck = new JCheckBox("Thực hiện cấu hình cho khu vực VIP");
        VIPCheck.setMnemonic(KeyEvent.VK_C);
        VIPCheck.setBackground(style.bgColor);
        VIPCheck.setFont(style.title18);
        VIPCheck.setForeground(Color.RED);

        JPanel VIPContent = new JPanel();
        VIPContent.setLayout(new BoxLayout(VIPContent, BoxLayout.X_AXIS));
        VIPContent.setPreferredSize(style.textFieldDimen);
        VIPContent.setMaximumSize(style.textFieldDimen);
        VIPContent.setMinimumSize(style.textFieldDimen);
        VIPContent.setBackground(style.bgColor);

        // TEMP
        JTextField txtVIPFrom = new JTextField("Từ");
        JTextField txtVIPTo = new JTextField("Đến");
        JTextField txtVIPPrice = new JTextField("Giá vé (VNĐ)");
        
        txtVIPFrom.setFont(style.title18);
        txtVIPFrom.setForeground(style.lightGray);
        txtVIPFrom.setEnabled(false);
        txtVIPFrom.setBackground(Color.GRAY);
 
        txtVIPTo.setFont(style.title18);
        txtVIPTo.setForeground(style.lightGray);
        txtVIPTo.setEnabled(false);
        txtVIPTo.setBackground(Color.GRAY);

        txtVIPPrice.setFont(style.title18);
        txtVIPPrice.setForeground(style.lightGray);
        txtVIPPrice.setEnabled(false);
        txtVIPPrice.setBackground(Color.GRAY);

        VIPContent.add(txtVIPFrom);
        VIPContent.add(Box.createRigidArea(new Dimension(10, 0)));
        VIPContent.add(txtVIPTo);
        VIPContent.add(Box.createRigidArea(new Dimension(60, 0)));
        VIPContent.add(txtVIPPrice);
        
        // listen check event of VIP Area
        VIPCheck.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if(e.getStateChange() == 1){        // check
                    txtVIPFrom.setEnabled(true);
                    txtVIPTo.setEnabled(true);
                    txtVIPPrice.setEnabled(true);
                    txtVIPFrom.setBackground(style.bgLighGray);
                    txtVIPTo.setBackground(style.bgLighGray);
                    txtVIPPrice.setBackground(style.bgLighGray);

                }else{          // uncheck
                    txtVIPFrom.setEnabled(false);
                    txtVIPTo.setEnabled(false);
                    txtVIPPrice.setEnabled(false);
                    txtVIPFrom.setBackground(Color.GRAY);
                    txtVIPTo.setBackground(Color.GRAY);
                    txtVIPPrice.setBackground(Color.GRAY);
                }
            }
        });
        
        GridBagConstraints csGroupVIp = new GridBagConstraints();
        csGroupVIp.fill = GridBagConstraints.HORIZONTAL;
        csGroupVIp.gridx = 0; csGroupVIp.gridy = 0;
        groupVIP.add(VIPCheck,csGroupVIp);
        csGroupVIp.gridy = 1;
        groupVIP.add(VIPContent,csGroupVIp);

        //SET UP for couple area
        JPanel groupCouple = new JPanel();
        groupCouple.setLayout(new GridBagLayout());
        groupCouple.setBackground(style.bgColor);
        groupCouple.setPreferredSize(new Dimension(800,80));
        groupCouple.setMaximumSize(new Dimension(800,80));
        groupCouple.setMinimumSize(new Dimension(800,80));


        JCheckBox CoupleCheck = new JCheckBox("Thực hiện cấu hình cho khu vực Couple");
        CoupleCheck.setMnemonic(KeyEvent.VK_C);
        CoupleCheck.setBackground(style.bgColor);
        CoupleCheck.setFont(style.title18);
        CoupleCheck.setForeground(Color.RED);

        JPanel CoupleContent = new JPanel();
        CoupleContent.setLayout(new BoxLayout(CoupleContent, BoxLayout.X_AXIS));
        CoupleContent.setPreferredSize(style.textFieldDimen);
        CoupleContent.setMaximumSize(style.textFieldDimen);
        CoupleContent.setMinimumSize(style.textFieldDimen);
        CoupleContent.setBackground(style.bgColor);

        // TEMP
        JTextField txtCoupleFrom = new JTextField("Từ");
        JTextField txtCoupleTo = new JTextField("Đến");
        JTextField txtCouplePrice = new JTextField("Giá vé (VNĐ)");
        txtCoupleFrom.setFont(style.title18);
        txtCoupleFrom.setForeground(style.lightGray);
        txtCoupleFrom.setBackground(Color.GRAY);
        txtCoupleFrom.setEnabled(false);
        
        txtCoupleTo.setFont(style.title18);
        txtCoupleTo.setForeground(style.lightGray);
        txtCoupleTo.setBackground(Color.GRAY);
        txtCoupleTo.setEnabled(false);
        
        txtCouplePrice.setFont(style.title18);
        txtCouplePrice.setForeground(style.lightGray);
        txtCouplePrice.setBackground(Color.GRAY);
        txtCouplePrice.setEnabled(false);
        
        CoupleContent.add(txtCoupleFrom);
        CoupleContent.add(Box.createRigidArea(new Dimension(10, 0)));
        CoupleContent.add(txtCoupleTo);
        CoupleContent.add(Box.createRigidArea(new Dimension(60, 0)));
        CoupleContent.add(txtCouplePrice);
        
        CoupleCheck.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if(e.getStateChange() == 1){        //check 
                    txtCoupleFrom.setEnabled(true);
                    txtCoupleTo.setEnabled(true);
                    txtCouplePrice.setEnabled(true);
                    txtCoupleFrom.setBackground(style.bgLighGray);
                    txtCoupleTo.setBackground(style.bgLighGray);
                    txtCouplePrice.setBackground(style.bgLighGray);
                }else{      //uncheck
                    txtCoupleFrom.setEnabled(false);
                    txtCoupleTo.setEnabled(false);
                    txtCouplePrice.setEnabled(false);
                    txtCoupleFrom.setBackground(Color.GRAY);
                    txtCoupleTo.setBackground(Color.GRAY);
                    txtCouplePrice.setBackground(Color.GRAY);
                }
            }
        });
        
        GridBagConstraints csGroupCouple = new GridBagConstraints();
        csGroupCouple.fill = GridBagConstraints.HORIZONTAL;
        csGroupCouple.gridx = 0; csGroupCouple.gridy = 0;
        groupCouple.add(CoupleCheck,csGroupCouple);
        csGroupCouple.gridy = 1;
        groupCouple.add(CoupleContent,csGroupCouple);
        

        JButton btnComplete = new JButton("Hoàn Thành");
        btnComplete.setBackground(style.btnColor);
        btnComplete.setFont(style.title20);
        btnComplete.setForeground(style.White);
        
        //bnt Complete click action 
        btnComplete.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //get data 
                String Id="", fromVIP="", toVIP="", fromCouple="", toCouple="";
                int col = 0, row = 0;
                long priceVIP =0, priceCouple=0;
                boolean VIP=false, Couple=false;
                
                Id = audiCombo.getText();
                try{
                    row = Integer.parseInt(txtRow.getText());
                    col = Integer.parseInt(txtCol.getText());
                }catch(NumberFormatException err){
                    JOptionPane.showMessageDialog(null,"Hàng và cột phải là các số tự nhiên","Notificaion",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(Id.contains("Chọn rạp muốn cấu hình")){
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin","Notification",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(VIPCheck.isSelected()){
                    VIP = true;
                    fromVIP = txtVIPFrom.getText();
                    toVIP = txtVIPTo.getText();
                    if(fromVIP.contains("Từ") || toVIP.contains("Đến")){
                        JOptionPane.showMessageDialog(null,"Vui lòng nhập đủ thông tin","Notificaion",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    try{
                        priceVIP = Long.parseLong(txtVIPPrice.getText());
                    }catch(NumberFormatException error){
                        JOptionPane.showMessageDialog(null,"Giá tiền phải là một số nguyên","Notificaion",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                if(CoupleCheck.isSelected()){
                    Couple = true;
                    fromCouple = txtCoupleFrom.getText();
                    toCouple = txtCoupleTo.getText();
                    if(fromCouple.contains("Từ") || toCouple.contains("Đến")){
                        JOptionPane.showMessageDialog(null,"Vui lòng nhập đủ thông tin","Notificaion",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    try{
                        priceCouple = Long.parseLong(txtCouplePrice.getText());
                    }catch(NumberFormatException error){
                        JOptionPane.showMessageDialog(null,"Giá tiền phải là một số nguyên","Notificaion",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                
                String response = manageServer.setupAuditorium(Id,row,col,VIP,fromVIP,toVIP,priceVIP,Couple,fromCouple,toCouple,priceCouple);
                if(response.contains("true")){
                    System.out.println("SUCCESS: CAU HINH THONG TIN RAP PHIM " + Id + " THANH CONG");
                    JOptionPane.showMessageDialog(null, "Cấu hình rạp phim thành công!!!","Notification",JOptionPane.WARNING_MESSAGE);
                    audiCombo.setText("Chọn rạp muốn cấu hình");
                    txtCol.setText("Nhập số cột");
                    txtRow.setText("Nhập số hàng");
                    VIPCheck.setSelected(false);
                    txtVIPFrom.setText("Từ");
                    txtVIPTo.setText("Đến");
                    txtVIPPrice.setText("Giá vé(VNĐ)");
                    CoupleCheck.setSelected(false);
                    txtCoupleFrom.setText("Từ");
                    txtCoupleTo.setText("Đến");
                    txtCouplePrice.setText("Giá vé(VNĐ)");
                    
                    mainFrame.getContentPane().removeAll();
                    ShowHomeScreen();
                    setVisible(true);
                    mainFrame.setFocusable(true);
                    
                }
                else{
                    System.err.println("ERROR =>  " + response);
                    JOptionPane.showMessageDialog(null, response,"Notification",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        output.add(title);
        output.add(Box.createRigidArea(new Dimension(0, 60)));
        output.add(audiCombo);
        output.add(Box.createRigidArea(new Dimension(0, 10)));
        output.add(groupColRow);
        output.add(Box.createRigidArea(new Dimension(0, 10)));
        output.add(groupVIP);
        output.add(Box.createRigidArea(new Dimension(0, 10)));
        output.add(groupCouple);
        output.add(Box.createRigidArea(new Dimension(0, 60)));
        output.add(btnComplete);

        return output;
    }

    // tạo màn hình cài đặt xuất chiếu
    public JPanel createShowtimeSetting() {
        JPanel output = new JPanel();
        output.setLayout(new BoxLayout(output, BoxLayout.Y_AXIS));
        output.setBackground(style.bgColor);
        
        JLabel linkImg = new JLabel();

        JLabel title = new JLabel();
        title.setText("Thêm xuất chiếu");
        title.setFont(style.headerTitle);
        title.setForeground(style.White);

        JTextField txtName = new JTextField("Nhập tên film");
        txtName.setPreferredSize(style.textFieldDimen);
        txtName.setMinimumSize(style.textFieldDimen);
        txtName.setMaximumSize(style.textFieldDimen);
        txtName.setFont(style.title20);
        txtName.setForeground(style.lightGray);
        txtName.setBackground(style.bgLighGray);

        JPanel groupfilm = new JPanel();
        groupfilm.setLayout(new BoxLayout(groupfilm, BoxLayout.X_AXIS));
        groupfilm.setPreferredSize(style.textFieldDimen);
        groupfilm.setMaximumSize(style.textFieldDimen);
        groupfilm.setMinimumSize(style.textFieldDimen);
        groupfilm.setBackground(style.bgColor);

        
        JButton btnAddImg = new JButton();
        ImageIcon icon = setScale(40, 30, new ImageIcon(getClass().getResource("/media/addImg.png")));
        btnAddImg.setIcon(icon);
        btnAddImg.setText("Thêm hình ảnh");
        btnAddImg.setFont(style.title18);
        btnAddImg.setForeground(style.lightGray);
        btnAddImg.setBackground(style.bgLighGray);
        
        final JFileChooser fileChooser = new JFileChooser();
        btnAddImg.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int returnVal = fileChooser.showOpenDialog(mainFrame);
                if( returnVal == JFileChooser.APPROVE_OPTION){
                    java.io.File file = fileChooser.getSelectedFile();
                    btnAddImg.setText(file.getName());
                    linkImg.setText(file.toString());
                }
                else{
                    System.out.println("Action is canceled!!");
                }
            }
        });

        JTextField txtTime = new JTextField("Thời lượng (phút)");
        txtTime.setFont(style.title18);
        txtTime.setForeground(style.lightGray);
        txtTime.setBackground(style.bgLighGray);

        JTextField txtRating = new JTextField("Đánh giá");
        txtRating.setFont(style.title18);
        txtRating.setForeground(style.lightGray);
        txtRating.setBackground(style.bgLighGray);

        groupfilm.add(btnAddImg);
        groupfilm.add(Box.createRigidArea(new Dimension(10, 0)));
        groupfilm.add(txtTime);
        groupfilm.add(Box.createRigidArea(new Dimension(10, 0)));
        groupfilm.add(txtRating);


        //create data for combo box 
        DefaultComboBoxModel modelHour = new DefaultComboBoxModel();
        for(int i = 1; i <= 24; ++i )
            if(i < 10)
                modelHour.addElement("0" + i + "h");
            else 
                modelHour.addElement(i + "h");
        DefaultComboBoxModel modelMinute = new DefaultComboBoxModel();
        for(int i = 0; i < 60; i = i + 15)
            if(i == 0 )
                modelMinute.addElement("0" + i);
            else
                modelMinute.addElement(i);

        JPanel startTime = new JPanel();
        startTime.setLayout(new BoxLayout(startTime,BoxLayout.X_AXIS));
        startTime.setPreferredSize(style.textFieldDimen);
        startTime.setMinimumSize(style.textFieldDimen);
        startTime.setMaximumSize(style.textFieldDimen);
        startTime.setBackground(style.bgColor);

        JComboBox hourCombo = new JComboBox(modelHour);
        hourCombo.setSelectedItem(0);
        hourCombo.setFont(style.title18);
        hourCombo.setBackground(style.bgLighGray);
        hourCombo.setForeground(style.lightGray);
        
        JComboBox minuteCombo = new JComboBox(modelMinute);
        minuteCombo.setSelectedItem(0);
        minuteCombo.setFont(style.title18);
        minuteCombo.setBackground(style.bgLighGray);
        minuteCombo.setForeground(style.lightGray);
        
        JLabel txtStartTime = new JLabel("Chọn thời gian xuất chiếu: ");
        txtStartTime.setFont(style.title18);
        txtStartTime.setForeground(style.White);
        
        startTime.add(txtStartTime);
        startTime.add(hourCombo);
        startTime.add(Box.createRigidArea(new Dimension(10,0)));
        startTime.add(minuteCombo);
        
        //get list auditorium 
//        java.util.List<auditorium> raw = handleServer.readAuditoriumFile(dir + "/src/data/auditorium.txt");
        java.util.List<auditorium> raw = handleServer.readAuditoriumFile(dir + "/auditorium.txt");

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for(int i = 0; i < raw.size(); ++i)
            model.addElement(raw.get(i).getID());

        JComboBox audiCombo = new JComboBox(model);
        audiCombo.setSelectedItem(0);
        audiCombo.setFont(style.title18);
        audiCombo.setForeground(style.lightGray);
        audiCombo.setPreferredSize(style.textFieldDimen);
        audiCombo.setMinimumSize(style.textFieldDimen);
        audiCombo.setMaximumSize(style.textFieldDimen);
        audiCombo.setBackground(style.bgLighGray);


        JButton btnComplete = new JButton("Hoàn Thành");
        btnComplete.setBackground(style.btnColor);
        btnComplete.setFont(style.title20);
        btnComplete.setForeground(style.White);
        
        btnComplete.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String filmTitle = txtName.getText();
                String avatarUrl = linkImg.getText();
                int duration = 0;
                double rating = 0.0;
                if(filmTitle.contains("Nhập tên film")){
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin","Notification",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try{
                    duration = Integer.parseInt(txtTime.getText());
                }catch(NumberFormatException err){
                    JOptionPane.showMessageDialog(null, "Thời lượng chiếu phải là một số nguyên","Notification",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try{
                    rating = Double.parseDouble(txtRating.getText());
                }catch(NumberFormatException err){
                    JOptionPane.showMessageDialog(null, "Rating phải là một số thực","Notification",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                String temp = hourCombo.getSelectedItem().toString();
                int pos = temp.indexOf("h");
                String auditoriumID = audiCombo.getSelectedItem().toString();
                String time = temp.substring(0,pos) + ":" + minuteCombo.getSelectedItem().toString();
                String result = manageServer.setupShowtime(filmTitle,avatarUrl,duration,rating,time,auditoriumID);
                
                if(result.contains("true")){
                     JOptionPane.showMessageDialog(null, "Cấu hình xuất chiếu thành công!!!","Notification",JOptionPane.WARNING_MESSAGE);
                     txtName.setText("Nhập tên film");
                     btnAddImg.setText("Thêm hình ảnh");
                     txtTime.setText("Thời lượng(Phút)");
                     txtRating.setText("Đánh giá");
                     
                     // setup home screen again
                    mainFrame.getContentPane().removeAll();
                    ShowHomeScreen();
                    setVisible(true);
                    mainFrame.setFocusable(true);
                             
                }else{
                    JOptionPane.showMessageDialog(null, result,"Notification",JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        output.add(title);
        output.add(Box.createRigidArea(new Dimension(0, 60)));
        output.add(txtName);
        output.add(Box.createRigidArea(new Dimension(0, 10)));
        output.add(groupfilm);
        output.add(Box.createRigidArea(new Dimension(0, 10)));
        output.add(startTime);
        output.add(Box.createRigidArea(new Dimension(0, 10)));
        output.add(audiCombo);
        output.add(Box.createRigidArea(new Dimension(0, 60)));
        output.add(btnComplete);
        return output;
    }

    // tạo header của trang
    public JPanel createHeaderPanel() {
        JPanel output = new JPanel();
        output.setLayout(new BoxLayout(output, BoxLayout.X_AXIS));
        output.setBackground(style.headerColor);
        output.setPreferredSize(style.headerDimen);
        output.setMinimumSize(style.headerDimen);
        output.setMaximumSize(style.headerDimen);
        
        ImageIcon headerLogo = setScale(158, 88, new ImageIcon(getClass().getResource("/media/logo.png")));
        JLabel headerLogoArea = new JLabel();
        headerLogoArea.setIcon(headerLogo);
        headerLogoArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // move to main screen of server(using card layout to display )
                card.show(contentPanel, "home");
            }
        });

        JButton btnStage = new JButton("Cấu hình sân khấu");
        btnStage.setBackground(style.btnColor);
        btnStage.setFont(style.title20);
        btnStage.setForeground(style.White);
        btnStage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(contentPanel, "stage");
            }
        });
        
        JButton btnAuditorium = new JButton("Cấu hình xuất chiếu");
        btnAuditorium.setBackground(style.btnColor);
        btnAuditorium.setFont(style.title20);
        btnAuditorium.setForeground(style.White);
        btnAuditorium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(contentPanel, "auditorium");

            }
        });

        output.add(Box.createRigidArea(new Dimension(20, 0)));
        output.add(headerLogoArea);
        output.add(Box.createRigidArea(new Dimension(800, 0)));
        output.add(btnStage);
        output.add(Box.createRigidArea(new Dimension(20, 0)));
        output.add(btnAuditorium);
        return output;
    }
    

    // scale một ảnh với kích thước đầu vào (width,height)
    public ImageIcon setScale(int width, int height, ImageIcon input) {
        origin = input.getImage();
        resize = origin.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resize);
    }

    //show layout của server 
    public void ShowHomeScreen(){
        mainFrame = this;
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(style.bgColor);
        getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        headerPanel = createHeaderPanel();
        contentPanel = createContentPanel();
        mainFrame.add(headerPanel);
        mainFrame.add(contentPanel);
    }
    
    public Server(int port) throws IOException{
        dir = System.getProperty("user.dir");
        serverSocket = new ServerSocket(port);
        System.out.println("server address: " + InetAddress.getLocalHost().getHostAddress());
        serverSocket.setSoTimeout(300000);
          
    }
    
    public void start(){
        System.out.println("Server started. Waiting for client connect ... ");       
        // DISPLAY LAYOUT SERVER
        ShowHomeScreen();
        setVisible(true);
        try{
            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                //TẠO LUỒNG XỬ LÝ CHO TỪNG CLIENT 
                ClientHandle clientHandle = new ClientHandle(clientSocket);
                clientHandle.start();
                
            }//end while
        }catch(IOException e){
            e.printStackTrace();
        }
        finally{
            stop();
        }//end try-catch
    }// end start function
    
    public void stop(){
        try{
            serverSocket.close();
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public class ClientHandle extends Thread{
        private Socket clientSocket;
        private DataInputStream input;
        private DataOutputStream output;
        private ManageCinema manage = new ManageCinema();
        private handleData handle = new handleData();

        public ClientHandle(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        @Override
        public void run(){
            try{
                // KHỞI TẠO ĐỐI TƯỢNG NHẬN DỮ LIỆU TỪ CLIENT
                input = new DataInputStream(clientSocket.getInputStream());
                // KHỞI TẠO ĐỐI TƯỢNG TRẢ DỮ LIỆU CHO CLIENT
                output = new DataOutputStream(clientSocket.getOutputStream());
                String mess = "";
                String resp = "";
                while(true){
                    //NHẬN THÔNG TIN TỪ CLIENT
                    mess = input.readUTF();
                    System.out.println(clientSocket.getLocalSocketAddress() + " : " + mess);
                    if(mess.contains("GET")){
                        if(mess.contains("ListShowtime")){
//                            resp = dir + "\\src\\data\\showtime.txt";
                            resp = dir + "\\showtime.txt";

                        }
                        if(mess.contains("ListBooking")){
//                            resp = dir +  "\\src\\data\\booking.txt|";
                            resp = dir +  "\\booking.txt|";
//                            resp += dir + "\\src\\data\\auditorium.txt";
                            resp += dir + "\\auditorium.txt";

                        }
                        if(mess.contains("ListAuditorium")){
//                            resp = dir + "\\src\\data\\auditorium.txt";
                            resp = dir + "\\auditorium.txt";

                        }
                    }else
                    if(mess.contains("RESERVED")){
                        String result = manage.solveBooking(mess);
                        if(result.contains("true")){
                            resp = "SUCCESS";
                        }
                        else {
                            resp = "FAILED: " + result;
                        }
                    }
                    
                    // PHẢN HỒI LẠI CHO CLIENT
                    output.writeUTF(resp);
                    System.err.println("response: " + resp);
                }
            }catch(IOException e){
                e.printStackTrace();
            }finally{
                try{
                    if(input == null)
                        input.close();
                    if(output == null)
                        output.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String args[]){
        try{
            Server server = new Server(PORT);
            server.start();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
