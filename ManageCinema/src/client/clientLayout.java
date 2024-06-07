///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package client;
//
//import managecinema.*;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import data.*;
//import data.handleData;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.Socket;
//import javax.swing.plaf.basic.BasicButtonUI;
//
//
//public class clientLayout extends JFrame{
//    private JFrame mainFrame,bookingFrame;
//    private Image origin, resize;    
//    private Style style = new Style();
//    private supportFunc support = new supportFunc();
//
//    // size
//    private final int CHAIR_WIDHT = 55;
//    private final int CHAR_HEIGHT = 30;
//    
//    public JPanel createPosterFilm(Showtime data){
//        
//        JPanel output = new JPanel();
//        output.setLayout(new GridBagLayout());
//        GridBagConstraints cs = new GridBagConstraints();
//        output.setBackground(style.transparentColor);
//        output.setPreferredSize(new Dimension(350,420));
//        output.setMinimumSize(new Dimension(350,420));
//        output.setMaximumSize(new Dimension(350,420));
//        //COVER IMG
//        JLabel filmArea = new JLabel();
//        ImageIcon filmImg;
//        if(data.getCoverImg().equals("UNKNOWN"))
//            filmImg = setScale(222, 312, new ImageIcon(getClass().getResource("/media/unknown.jpg")));
//        else
//            filmImg = setScale(222, 312, new ImageIcon(data.getCoverImg()));
//        filmArea.setIcon(filmImg);
//        //FILM TITLE
//        JLabel titleArea = new JLabel();
//        titleArea.setText(data.getName());
//        titleArea.setForeground(style.White);
//        titleArea.setFont(style.title16);
//        //FILM RATING
//        JLabel ratingArea = new JLabel();
//        ImageIcon ratingImg = setScale(20, 20, new ImageIcon(getClass().getResource("/media/rating.png")));
//        ratingArea.setFont(style.title18);
//        ratingArea.setForeground(style.subTextColor);
//        ratingArea.setIconTextGap(10);
//        //FILM DURATION
//        JLabel durationArea = new JLabel();
//        ImageIcon durationImg = setScale(20, 20, new ImageIcon(getClass().getResource("/media/duration.png")));
//        durationArea.setFont(style.title18);
//        durationArea.setForeground(style.subTextColor);
//        durationArea.setIconTextGap(10);
//
//        ratingArea.setIcon(ratingImg);
//        ratingArea.setText(data.getRating().toString() + "/10");
//        durationArea.setIcon(durationImg);
//        String duration ;
//        if(data.getDuration() < 60){
//            duration = String.valueOf(data.getDuration()) + "m";
//        }else{
//            duration = String.valueOf(data.getDuration()/60) + "h " + String.valueOf(data.getDuration()%60) + "m" ;
//        }
//        durationArea.setText(duration);
//        
//        JPanel wholeArea = new JPanel();
//        wholeArea.setLayout(new FlowLayout(FlowLayout.CENTER));
//        wholeArea.setPreferredSize(style.singlefilmDimen);
//        wholeArea.setBackground(style.transparentColor);
//        wholeArea.add(ratingArea);
//        wholeArea.add(Box.createRigidArea(new Dimension(20, 0)));
//        wholeArea.add(durationArea);
//        //BUTTON CHOOSE
//        JButton btnchoose = new JButton();
//        btnchoose.setText(data.getStartTime() + " - " + data.getEndTime());
//        btnchoose.setPreferredSize(style.singlefilmDimen);
//        btnchoose.setMinimumSize(style.singlefilmDimen);
//        btnchoose.setMaximumSize(style.singlefilmDimen);
//        btnchoose.setBackground(style.btnColor2);
//        btnchoose.setForeground(style.White);
//        btnchoose.setFont(style.title16);
//
//        btnchoose.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showBookingScreen(data);
//            }
//        });
//        
//        cs.fill = GridBagConstraints.HORIZONTAL;
//        cs.gridx = 0; cs.gridy = 0;
//        output.add(filmArea,cs);
//        cs.gridy = 1;
//        output.add(wholeArea,cs);
//        cs.gridy = 2;
//        output.add(titleArea,cs);
//        cs.gridy = 3;
//        output.add(btnchoose,cs);
//        return output;
//    }
//    
//    public JPanel createClientHeader(){
//        
//        JPanel header = new JPanel();
//        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
//        header.setBackground(style.headerColor);
//        header.setPreferredSize(style.headerDimen);
//        header.setMinimumSize(style.headerDimen);
//        header.setMaximumSize(style.headerDimen);
//        //HEADER LOGO
//        ImageIcon headerLogo = setScale(158, 88, new ImageIcon(getClass().getResource("/media/logo.png")));
//        JLabel headerLogoArea = new JLabel();
//        headerLogoArea.setIcon(headerLogo);
//        headerLogoArea.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//            }
//        });
//        //HEADER SLOGAN
//        JLabel introduceArea = new JLabel();
//        introduceArea.setText("Come to Movie Time you plan your amuse time with your darling");
//        introduceArea.setFont(style.headerClientTitle);
//        introduceArea.setForeground(style.White);
//
//        header.add(Box.createRigidArea(new Dimension(50, 0)));
//        header.add(headerLogoArea);
//        header.add(Box.createRigidArea(new Dimension(50, 0)));
//        header.add(introduceArea);
//        return header;
//    }
//    
//    public JScrollPane createHomeScreen(java.util.List<Showtime> input) {
//        
//        JPanel content = new JPanel();
//        content.setLayout(new FlowLayout(FlowLayout.LEFT));
//        int temp = (int)(input.size()/4);
//        if(input.size()%4 != 0 )
//            temp ++;
//        content.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
//            (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2)*(temp+1)));       
//        content.setBackground(style.bgColor);
//        for(int i = 0; i < input.size(); ++i){
//            content.add(createPosterFilm(input.get(i)));
//        }
//        
//        JScrollPane scrollContent = new JScrollPane(content);
//        scrollContent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        scrollContent.setBackground(style.bgColor);
//        scrollContent.getVerticalScrollBar().setUnitIncrement(16);
//        scrollContent.getVerticalScrollBar().setBlockIncrement(64);
//        return scrollContent;
//    }
//    
//    public JPanel posterForBooking(Showtime input){
//        
//        JPanel output = new JPanel();
//        output.setBackground(style.bgColor);
//        output.setPreferredSize(style.BigPosterDimen);
//        output.setMinimumSize(style.BigPosterDimen);
//        output.setMaximumSize(style.BigPosterDimen);
//        
//        JPanel poster = new JPanel();
//        poster.setBackground(style.bgColor);
//        poster.setLayout(new GridBagLayout());
//        
//        JLabel avatarArea = new JLabel();
//        ImageIcon mainImg;
//        if(input.getCoverImg().equals("UNKNOWN"))
//            mainImg = setScale(320, 512, new ImageIcon(getClass().getResource("/media/unknown.jpg")));
//        else
//            mainImg = setScale(320, 523, new ImageIcon(input.getCoverImg()));
//        avatarArea.setIcon(mainImg);
//
//        JLabel ratingArea = new JLabel();
//        ImageIcon ratingImg = setScale(36, 32, new ImageIcon(getClass().getResource("/media/rating.png")));
//        ratingArea.setFont(style.title18);
//        ratingArea.setForeground(style.subTextColor);
//        ratingArea.setIcon(ratingImg);
//        ratingArea.setText(input.getRating() + "/10");
//
//        JLabel durationArea = new JLabel();
//        ImageIcon durationImg = setScale(28, 28, new ImageIcon(getClass().getResource("/media/duration.png")));
//        durationArea.setFont(style.title18);
//        durationArea.setForeground(style.subTextColor);
//        durationArea.setIcon(durationImg);
//        String duration;
//        if(input.getDuration() < 60){
//            duration = input.getDuration() + "m";
//        }else{
//            duration = input.getDuration()/60 + "h " + input.getDuration()%60 + "m" ;
//        }
//        durationArea.setText(duration);
//        
//        
//        JLabel groupAudi = new JLabel();
//        ImageIcon audiIcon = setScale(28,28, new ImageIcon(getClass().getResource("/media/auditorium.png")));
//        groupAudi.setFont(style.filmTitle);
//        groupAudi.setForeground(style.subTextColor);
//        groupAudi.setIcon(audiIcon);
//        groupAudi.setText(input.getAuditoriumId());
//        
//        JPanel group = new JPanel();
//        group.setLayout(new FlowLayout(FlowLayout.CENTER));
//        group.setBackground(style.bgColor);
//        group.setPreferredSize(style.bigPoster);
//        group.add(ratingArea);
//        group.add(Box.createRigidArea(new Dimension(10, 0)));
//        group.add(durationArea);
//        group.add(Box.createRigidArea(new Dimension(10,0)));
//        group.add(groupAudi);
//
//        JLabel title = new JLabel();
//        title.setForeground(style.White);
//        title.setFont(style.title20);
//        title.setLayout(new FlowLayout(FlowLayout.CENTER));
//        title.setPreferredSize(style.bigPoster);
//        title.setMinimumSize(style.bigPoster);
//        title.setMaximumSize(style.bigPoster);
//        title.setText(input.getName());
//
//        JButton time = new JButton(input.getStartTime() + " - " + input.getEndTime());
//        time.setPreferredSize(style.bigPoster);
//        time.setMinimumSize(style.bigPoster);
//        time.setMaximumSize(style.bigPoster);
//        time.setBackground(style.btnColor2);
//        time.setForeground(style.White);
//        time.setFont(style.title22);
//        time.setEnabled(false);
//        time.setFont(style.filmTitle);
//        time.setForeground(style.White);
//                
//        GridBagConstraints cs = new GridBagConstraints();
//        cs.fill = GridBagConstraints.HORIZONTAL;
//        cs.gridx = 0; cs.gridy = 0;
//        poster.add(avatarArea,cs);
//        cs.gridy = 1;
//        poster.add(group,cs);
//        cs.gridy = 2;
//        poster.add(title,cs);
//        cs.gridy =3;
//        poster.add(time,cs);
//
//        output.add(poster);
//        revalidate();
//        repaint();
//        return output;
//    }
//    
//    public JPanel createBookingArea(Showtime data, java.util.List<Booking> listBooking, java.util.List<auditorium> listAudi){
//        JPanel screen = new JPanel();
//        screen.setLayout(new BoxLayout(screen, BoxLayout.Y_AXIS));
//        screen.setPreferredSize(style.contentpart2);
//        screen.setMinimumSize(style.contentpart2);
//        screen.setMaximumSize(style.contentpart2);
//        screen.setBackground(style.bgColor);
//        
//        JLabel title = new JLabel();
//        title.setBackground(style.bgColor);
//        title.setForeground(style.White);
//        title.setFont(style.headerTitle);
//        title.setText("BOOKING AREA ");
//        
//        // USER INFORMATION 
//        JTextField txtName = new JTextField("Nhập tên của bạn");
//        txtName.setPreferredSize(style.textFieldDimen);
//        txtName.setMinimumSize(style.textFieldDimen);
//        txtName.setMaximumSize(style.textFieldDimen);
//        txtName.setFont(style.title20);
//        txtName.setForeground(style.White);
//        txtName.setBackground(style.bgColor);
//        txtName.setCaretColor(style.White);
//        //USER PHONE
//        JTextField txtPhone = new JTextField("Nhập số điện thoại của bạn");
//        txtPhone.setPreferredSize(style.textFieldDimen);
//        txtPhone.setMinimumSize(style.textFieldDimen);
//        txtPhone.setMaximumSize(style.textFieldDimen);
//        txtPhone.setFont(style.title20);
//        txtPhone.setForeground(style.White);
//        txtPhone.setBackground(style.bgColor);
//        txtPhone.setCaretColor(style.White);
//
//        
//        JLabel numberTil = new JLabel();
//        numberTil.setFont(style.filmTitle);
//        numberTil.setForeground(style.White);
//        numberTil.setText("Number of ticket: ");
//
//        // choose number of seat
//        JButton btnSubstract = new JButton("-");
//        JButton txtNumber = new JButton("0");       
//        JButton btnPlus = new JButton("+");
//        txtNumber.setEnabled(false);
//        
//        JPanel groupNumber = new JPanel();
//        groupNumber.setBackground(style.bgColor);
//        groupNumber.setLayout(new FlowLayout(FlowLayout.LEFT));
//        groupNumber.add(Box.createRigidArea(new Dimension(50,0)));
//        groupNumber.add(numberTil);
//        groupNumber.add(btnSubstract);
//        groupNumber.add(txtNumber);
//        groupNumber.add(btnPlus);
//        groupNumber.setPreferredSize(
//                new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 3 / 5, 40));
//        groupNumber.setMinimumSize(
//                new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 3 / 5, 40));
//        groupNumber.setMaximumSize(
//                new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 3 / 5, 40));
//
//        // button action
//        btnSubstract.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Integer current = Integer.parseInt(txtNumber.getText());
//                if (current > 0) {
//                    current--;
//                    txtNumber.setText(current.toString());
//                }
//            }
//        });
//        btnPlus.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Integer current = Integer.parseInt(txtNumber.getText());
//                current++;
//                txtNumber.setText(current.toString());
//            }
//        });
//
//       
//        //PREPARE DATA
//        int row = 0, col = 0;
//        char[][] kindOfChair = null;
//        boolean[][] statusBooking = null;
//        
//        long Pvip = 0;
//        long Pcouple = 0;
//        final long priceStandard = 45000;
//        
//        for(int i = 0 ; i < listAudi.size(); ++i){
//            if(data.getAuditoriumId().contains(listAudi.get(i).getID())){
//                row = listAudi.get(i).getRow();
//                col = listAudi.get(i).getCol();
//                kindOfChair = support.createKindMatrix(listAudi.get(i), row, col);
//                Pvip = listAudi.get(i).getVIP().getPrice();
//                Pcouple = listAudi.get(i).getCouple().getPrice();
//                break;
//            }
//        }
//        final char [][] status = kindOfChair;
//        final long priceVIP = Pvip;
//        final long priceCouple = Pcouple;
//        
//        for(int i = 0; i < listBooking.size(); ++i){
//            if(data.getAuditoriumId().contains(listBooking.get(i).getAuditoriumId()) && data.getShowtimeId().contains(listBooking.get(i).getShowtimeId())){
//                statusBooking = support.createStatusMatrix(listBooking.get(i), row, col);
//                break;
//            }
//        }
//        
//        JToggleButton[][] followChoose = new JToggleButton[row][col];
//        final int finalRow = row, finalCol = col;
//        // LIST SEAT 
//        JPanel listSeat = new JPanel();
//        listSeat.setPreferredSize(new Dimension(col * CHAIR_WIDHT, row * CHAR_HEIGHT));
//        listSeat.setMinimumSize(new Dimension(col * CHAIR_WIDHT, row * CHAR_HEIGHT));
//        listSeat.setMaximumSize(new Dimension(col * CHAIR_WIDHT, row * CHAR_HEIGHT));
//        listSeat.setBackground(style.transparentColor);
//        listSeat.setLayout(new FlowLayout(FlowLayout.CENTER));
//        
//        for (int i = 0; i < row; ++i) {
//            for (int j = 0; j < col; ++j) {
//                String color = "";
//                if(statusBooking[i][j] == true)
//                    color = "gray";
//                else if(kindOfChair[i][j] == 'N')
//                    color = "blue";
//                else if(kindOfChair[i][j] == 'V')
//                    color = "red";
//                else if(kindOfChair[i][j] == 'C')
//                    color = "pink";
//                followChoose[i][j] = printChair(color);
//                listSeat.add(followChoose[i][j]);
//                if (j >= col){
//                listSeat.add(new JPanel());
//
//                }
//            }
//        }
//
//        // NOTE SEAT
//        JLabel note = new JLabel();
//        note.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 2 / 5, 120));
//        note.setMinimumSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 2 / 5, 120));
//        note.setMaximumSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 2 / 5, 120));
//        ImageIcon noteImg = setScale(340, 100, new ImageIcon(getClass().getResource("/media/note.png")));
//        note.setIcon(noteImg);
//        // BUTTON COMPLETE
//        JButton btnComplete = new JButton("Complete");
//        btnComplete.setPreferredSize(new Dimension(300, 40));
//        btnComplete.setMinimumSize(new Dimension(300, 40));
//        btnComplete.setMaximumSize(new Dimension(300, 40));
//        btnComplete.setBackground(style.btnColor);
//        btnComplete.setForeground(style.White);
//        btnComplete.setFont(style.filmTitle);
//        btnComplete.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // TODO Auto-generated method stub
//                try{
//                    // resume the booking information 
//                    //get list of position 
//                    String listChoosen = support.createListOfPosition(followChoose, finalRow, finalCol);
//                    //get number of ticket
//                    String numberOfTicket = txtNumber.getText();
//                    // real number of ticket 
//                    int realNumberOfTicket;
//                    if(!listChoosen.isEmpty())
//                        realNumberOfTicket = 1;
//                    else realNumberOfTicket = 0;
//                    for(int i = 0; i < listChoosen.length(); ++i)
//                        if(listChoosen.charAt(i) == ',') ++realNumberOfTicket;
//                    
//                    // CALCULATE PRICE
//                    int pos = -1;
//                    int totalPrice = 0;
//                    String position = "";
//                    String temp = listChoosen;
//                    while(!temp.isEmpty()){
//                        pos = temp.indexOf(",");
//                        if(pos == -1){
//                           position = temp;
//                           temp = "";
//                        }else{
//                            position = temp.substring(0,pos);
//                            temp = temp.substring(pos+1);
//                        }
//                        int x = position.charAt(0) - 65;
//                        int y = Integer.parseInt(position.substring(1))-1;
//                        if(status[x][y] == 'V')
//                            totalPrice += priceVIP;
//                        else if(status[x][y] == 'C')
//                                totalPrice += priceCouple;
//                            else if(status[x][y] == 'N')
//                                totalPrice += priceStandard;
//                    }
//                    
//                    String code = support.generateRandomString(10);
//                    
//                    output.writeUTF("RESERVED/" + data.getAuditoriumId() + "/" + data.getShowtimeId() 
//                            + "/" + listChoosen + "/" + realNumberOfTicket + "/" + numberOfTicket + "/" 
//                            + txtName.getText()+ "/" + txtPhone.getText() + "/" + code);
//                    String response = input.readUTF();
//                    if(response.contains("SUCCESS")){   //booking success
//                        DialogInform dialog = new DialogInform(bookingFrame,"Thông báo",true, txtName.getText(), txtPhone.getText(),data,totalPrice, listChoosen,code);
//                        dialog.setVisible(true);
//                        System.err.println(response);
//                    }else {     // booking failed
//                        System.err.println(response);
//                        JOptionPane.showMessageDialog(null,response,"Notification",JOptionPane.WARNING_MESSAGE);
//                        bookingFrame.dispose();
//                        mainFrame.setFocusable(true);
//                    }
//                }catch(IOException err){
//                    err.printStackTrace();
//                }
//            }
//
//        });
//
//        screen.add(title);
//        screen.add(Box.createRigidArea(new Dimension(0, 50)));
//        screen.add(txtName);
//        screen.add(Box.createRigidArea(new Dimension(0,10)));
//        screen.add(txtPhone);
//        screen.add(Box.createRigidArea(new Dimension(0,10)));
//        screen.add(groupNumber);
//        screen.add(listSeat);
//        screen.add(note);
//        screen.add(btnComplete);
//        return screen;
//    }
//    
//    public JScrollPane createBookingScreen(Showtime input, java.util.List<Booking> listBooking, java.util.List<auditorium> listAudi){
//
//        JPanel booking = new JPanel();
//        booking.setLayout(new GridBagLayout());
//        booking.setBackground(style.bgColor);
//        // thêm poster
//        GridBagConstraints cs = new GridBagConstraints();
//        cs.fill = GridBagConstraints.HORIZONTAL;
//        cs.gridx = 0; cs.gridy = 0;
//        booking.add(posterForBooking(input),cs);
//        cs.gridx=1;
//        booking.add(createBookingArea(input, listBooking, listAudi),cs);
//        
//        JScrollPane scrollContent = new JScrollPane(booking);
//        scrollContent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        scrollContent.setBackground(style.bgColor);
//        scrollContent.getVerticalScrollBar().setUnitIncrement(16);
//        scrollContent.getVerticalScrollBar().setBlockIncrement(64);
//        return scrollContent;
//    }
//    
//    //color maybe "red","gray","blue","pink"
//    public JToggleButton printChair(String color){
//        
//        JToggleButton output = new JToggleButton();
//        output.setUI(new BasicButtonUI());
//        output.setBackground(style.bgColor);
//        
//        if (color.equals("red")) {
//            ImageIcon chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("/media/VIP_seat.png")));
//            output.setIcon(chairImg);
//        } else if (color.equals("pink")) {
//            ImageIcon chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("/media/couple_seat.png")));
//            output.setIcon(chairImg);
//        } else if (color.equals("blue")) {
//            ImageIcon chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("/media/standard_seat.png")));
//            output.setIcon(chairImg);
//        } else if (color.equals("gray")) {
//            ImageIcon chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("/media/reserved_seat.png")));
//            output.setIcon(chairImg);
//            output.setEnabled(false);
//        }
//        output.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (output.isSelected()) {
//                    ImageIcon chariImg = setScale(15, 15, new ImageIcon(getClass().getResource("/media/your_choose.png")));
//                    output.setIcon(chariImg);
//                    output.setBackground(style.bgColor);
//                } else {
//                    ImageIcon chairImg = null;
//                    if(color.equals("red"))
//                        chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("/media/VIP_seat.png")));
//                    else if(color.equals("blue"))
//                        chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("/media/standard_seat.png")));
//                    else if(color.equals("pink"))
//                        chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("/media/couple_seat.png")));
//
//                    output.setIcon(chairImg);
//                    output.setBackground(style.bgColor);
//                }
//            }
//
//        });
//        return output;
//    }
//    
//    public ImageIcon setScale(int width, int height, ImageIcon input){
//        origin = input.getImage();
//        resize = origin.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//        return new ImageIcon(resize);
//    }
//    
//    void showHomeScreen(java.util.List<Showtime> data){
//        mainFrame = this;
//        setSize(Toolkit.getDefaultToolkit().getScreenSize());
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        getContentPane().setBackground(style.bgColor);
//        getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
//        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
//        mainFrame.add(createClientHeader());
//        mainFrame.add(createHomeScreen(data));
//    }
//    
//    void showBookingScreen(Showtime data){
//        //get booking status 
//        java.util.List<Booking> listBooking = null;
//        java.util.List<auditorium> listAudi = null;
//        String idAuditorium = data.getAuditoriumId();
//        try{
//            String request = "GET/ListBooking/" + idAuditorium;
//            output.writeUTF(request);
//            String link = input.readUTF();
//            int pos = link.indexOf("|");
//            handleData handle = new handleData();
//            listAudi = handle.readAuditoriumFile(link.substring(pos+1));
//            listBooking = handle.readBookingStatus(link.substring(0,pos));
//        }catch(Exception e){
//            System.err.println(e.getMessage());
//        }
//        
//        bookingFrame = new JFrame();
//        bookingFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
//        bookingFrame.setBackground(style.bgColor);
//        bookingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        bookingFrame.getContentPane().setLayout(new BoxLayout(bookingFrame.getContentPane(),BoxLayout.PAGE_AXIS));
//        bookingFrame.setLayout(new BoxLayout(bookingFrame.getContentPane(), BoxLayout.Y_AXIS));
//        
//        bookingFrame.add(createClientHeader());
//        bookingFrame.add(createBookingScreen(data,listBooking, listAudi));
//        bookingFrame.setVisible(true);
//    }
//    
//    // for networking
//    private static final int PORT = 8080;
////    private static String serverName = "192.168.75.1";
//        private static String serverName = "";        //FOR .JAR FILE 
//
//    private DataOutputStream output;
//    private DataInputStream input;
//    
//    
//    public clientLayout(){
//                
//        try{  
//            Socket client = new Socket(serverName,PORT);
//            if(client.isConnected()){
//                System.out.println("Connected to server with address " + client.getRemoteSocketAddress());
//                // tạo đối tượng truyền dữ liệu giữa client và server 
//                input = new DataInputStream(client.getInputStream());
//                output = new DataOutputStream(client.getOutputStream());
//                String mess = "GET/ListShowtime";
//                output.writeUTF(mess);
//                String resp = input.readUTF();
//
//                System.out.println("response message: " + resp);
//                handleData handle = new handleData();
//                java.util.List<Showtime> output = handle.readShowtimeFile(resp);
//
//                // show layout
//                showHomeScreen(output);
//                setVisible(true);
//       
//            }
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//        
//    }
//    
//    
//    public static void main(String args[]){
//        serverName = args[0];
//        clientLayout client = new clientLayout();
//    }
//    
//   
//}
