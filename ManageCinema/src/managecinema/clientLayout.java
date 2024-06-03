/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managecinema;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import data.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author User
 */
public class clientLayout extends JFrame{
    private java.util.List<auditorium> listAuditorium;
    private java.util.List<Showtime> listShowtime;
    private java.util.List<Booking> listBookings;
    private JFrame mainFrame,bookingFrame;
    private Image origin, resize;

    // dimension
    private Dimension headerDimen = new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 145);
    private Dimension contentpart2 = new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 3.7) / 5,
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() + 200);
    private Dimension BigPosterDimen = new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 1.3) / 5,
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() + 200);
    private Dimension singlefilmDimen = new Dimension(222, 30);
    private Dimension bigPoster = new Dimension(335, 40);
    private Dimension bookingElement = new Dimension(450, 40);
    private Dimension textFieldDimen = new Dimension(700, 40);
    // color
    private Color bgColor = new Color(48, 48, 48);
    private Color headerColor = new Color(22, 22, 22);
    private Color transparentColor = new Color(0, 0, 0, 0);
    private Color btnColor2 = new Color(30, 183, 232);
    private Color White = new Color(255, 255, 255);
    private Color blur = new Color(21, 19, 23, 110);
    private Color btnColor = new Color(223, 33, 68);
    private Color lightGray = new Color(102, 102, 102, 100);
    private Color bgLighGray = new Color(217, 217, 217);
    // font
    private Font filmTitle = new Font("Arial", Font.BOLD, 16);
    private Font bigFilmTitle = new Font("Arial", Font.BOLD, 16);
    private Font headerTitle = new Font("Comic Sans MS", Font.BOLD, 30);
    private Font title16 = new Font("Arial", Font.BOLD, 16);
    private Font title18 = new Font("Arial", Font.BOLD, 18);
    private Font title20 = new Font("Arial", Font.BOLD, 20);
    private Font title22 = new Font("Arial", Font.BOLD, 22);
    private Font title26 = new Font("Arial", Font.BOLD, 26);

    // size
    private final int CHAIR_WIDHT = 55;
    private final int CHAR_HEIGHT = 30;
    
    private supportFunc support = new supportFunc();
   

    
    public JPanel createPosterFilm(Showtime data){
        
        JPanel output = new JPanel();
        output.setLayout(new BoxLayout(output, BoxLayout.Y_AXIS));
        output.setBackground(transparentColor);
        output.setPreferredSize(new Dimension(350,420));
        output.setMinimumSize(new Dimension(350,420));
        output.setMaximumSize(new Dimension(350,420));


        JLabel filmArea = new JLabel();
        ImageIcon filmImg = setScale(222, 312, new ImageIcon(data.getCoverImg()));
        filmArea.setIcon(filmImg);

        JLabel titleArea = new JLabel();
        titleArea.setText(data.getName());
        titleArea.setForeground(White);
        titleArea.setFont(title16);

        JLabel ratingArea = new JLabel();
        ImageIcon ratingImg = setScale(20, 20, new ImageIcon(getClass().getResource("../media/rating.png")));
        ratingArea.setForeground(White);
        ImageIcon durationImg = setScale(20, 20, new ImageIcon(getClass().getResource("../media/duration.png")));
        ratingArea.setIconTextGap(10);

        JLabel durationArea = new JLabel();
        durationArea.setForeground(White);
        durationArea.setIconTextGap(10);

        ratingArea.setIcon(ratingImg);
        ratingArea.setText(data.getRating().toString() + "/10");
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
        wholeArea.setPreferredSize(singlefilmDimen);
        wholeArea.setBackground(transparentColor);
        wholeArea.add(ratingArea);
        wholeArea.add(Box.createRigidArea(new Dimension(20, 0)));
        wholeArea.add(durationArea);

        JButton btnchoose = new JButton();
        btnchoose.setText(data.getStartTime() + " - " + data.getEndTime());
        btnchoose.setPreferredSize(singlefilmDimen);
        btnchoose.setMinimumSize(singlefilmDimen);
        btnchoose.setMaximumSize(singlefilmDimen);
        btnchoose.setBackground(btnColor2);
        btnchoose.setForeground(White);
        btnchoose.setFont(title16);

        btnchoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookingScreen(data);
            }
        });

        output.add(filmArea);
        output.add(wholeArea);
        output.add(titleArea);
        output.add(Box.createRigidArea(new Dimension(0,10)));
        output.add(btnchoose);
        output.add(Box.createRigidArea(new Dimension(0,40)));
        return output;
    }
    
    public JPanel createClientHeader(){
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        header.setBackground(headerColor);
        header.setPreferredSize(headerDimen);
        header.setMinimumSize(headerDimen);
        header.setMaximumSize(headerDimen);
        ImageIcon headerLogo = setScale(158, 88, new ImageIcon(getClass().getResource("../media/logo.png")));
        JLabel headerLogoArea = new JLabel();
        headerLogoArea.setIcon(headerLogo);
        headerLogoArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });

        JLabel introduceArea = new JLabel();
        introduceArea.setText("Come to Movie Time you plan your amuse time with your darling");
        introduceArea.setFont(headerTitle);
        introduceArea.setForeground(White);

        header.add(Box.createRigidArea(new Dimension(50, 0)));
        header.add(headerLogoArea);
        header.add(Box.createRigidArea(new Dimension(50, 0)));
        header.add(introduceArea);
        return header;
    }
    
    public JScrollPane createHomeScreen(java.util.List<Showtime> input) {
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout(FlowLayout.LEFT));
        int temp = (int)(input.size()/4);
        if(input.size()%4 != 0 )
            temp ++;
        content.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
            (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2)*(temp+1)));       

        content.setBackground(bgColor);
        for(int i = 0; i < input.size(); ++i){
            content.add(createPosterFilm(input.get(i)));
        }
        
                JScrollPane scrollContent = new JScrollPane(content);
        scrollContent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollContent.setBackground(bgColor);
        scrollContent.getVerticalScrollBar().setUnitIncrement(16);
        scrollContent.getVerticalScrollBar().setBlockIncrement(64);
        
        return scrollContent;
    }
    
    public JPanel posterForBooking(Showtime input){
        System.out.println("Into create poster for film!!!");
        input.printInfo();
        
        JPanel output = new JPanel();
        output.setBackground(bgColor);
        output.setPreferredSize(BigPosterDimen);
        output.setMinimumSize(BigPosterDimen);
        output.setMaximumSize(BigPosterDimen);
        JPanel poster = new JPanel();
        poster.setBackground(bgColor);
        poster.setLayout(new BoxLayout(poster, BoxLayout.Y_AXIS));
        JLabel avatarArea = new JLabel();
        ImageIcon mainImg = setScale(335, 523, new ImageIcon(input.getCoverImg()));
        avatarArea.setIcon(mainImg);

        JLabel ratingArea = new JLabel();
        ratingArea.setFont(filmTitle);
        ratingArea.setForeground(White);
        ImageIcon ratingImg = setScale(36, 32, new ImageIcon(getClass().getResource("../media/rating.png")));
        ratingArea.setIcon(ratingImg);
        ratingArea.setText(input.getRating() + "/10");

        JLabel durationArea = new JLabel();
        durationArea.setFont(filmTitle);
        durationArea.setForeground(White);
        ImageIcon durationImg = setScale(28, 28, new ImageIcon(getClass().getResource("../media/duration.png")));
        durationArea.setIcon(durationImg);
        String duration;
        if(input.getDuration() < 60){
            duration = input.getDuration() + "m";
        }else{
            duration = input.getDuration()/60 + "h " + input.getDuration()%60 + "m" ;
        }
        durationArea.setText(duration);
        JPanel group = new JPanel();

        group.setLayout(new FlowLayout(FlowLayout.CENTER));
        group.setBackground(bgColor);
        group.setPreferredSize(bigPoster);
        group.add(ratingArea);
        group.add(Box.createRigidArea(new Dimension(50, 0)));
        group.add(durationArea);

        JLabel title = new JLabel();
        title.setForeground(White);
        title.setFont(title20);
        title.setLayout(new FlowLayout(FlowLayout.CENTER));
        title.setPreferredSize(bigPoster);
        title.setMinimumSize(bigPoster);
        title.setMaximumSize(bigPoster);
        title.setText(input.getName());

        JButton time = new JButton(input.getStartTime() + " - " + input.getEndTime());
        time.setPreferredSize(bigPoster);
        time.setMinimumSize(bigPoster);
        time.setMaximumSize(bigPoster);
        time.setBackground(btnColor2);
        time.setForeground(White);
        time.setFont(title22);
        time.setEnabled(false);

        time.setFont(filmTitle);
        time.setForeground(White);

        poster.add(avatarArea);
        poster.add(group);
        poster.add(title);
        poster.add(time);

        output.add(poster);
        revalidate();
        repaint();
        return output;
    }
    
    public JPanel createBookingArea(Showtime data, java.util.List<Booking> listBooking, java.util.List<auditorium> listAudi){
        JPanel screen = new JPanel();
        screen.setLayout(new BoxLayout(screen, BoxLayout.Y_AXIS));
        screen.setPreferredSize(contentpart2);
        screen.setMinimumSize(contentpart2);
        screen.setMaximumSize(contentpart2);
        screen.setBackground(bgColor);
        JLabel title = new JLabel();
        title.setPreferredSize(bookingElement);
        title.setMinimumSize(bookingElement);
        title.setMaximumSize(bookingElement);
        title.setBackground(bgColor);
        title.setForeground(White);
        title.setFont(headerTitle);
        title.setText("BOOKING AREA ");
        
        // user information 
        JTextField txtName = new JTextField("Nhập tên của bạn");
        txtName.setPreferredSize(textFieldDimen);
        txtName.setMinimumSize(textFieldDimen);
        txtName.setMaximumSize(textFieldDimen);
        txtName.setFont(title20);
        txtName.setForeground(White);
        txtName.setBackground(bgColor);
        txtName.setCaretColor(White);
        
        JTextField txtPhone = new JTextField("Nhập số điện thoại của bạn");
        txtPhone.setPreferredSize(textFieldDimen);
        txtPhone.setMinimumSize(textFieldDimen);
        txtPhone.setMaximumSize(textFieldDimen);
        txtPhone.setFont(title20);
        txtPhone.setForeground(White);
        txtPhone.setBackground(bgColor);
        txtPhone.setCaretColor(White);
        

        JPanel groupNumber = new JPanel();
        groupNumber.setBackground(bgColor);
        groupNumber.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel numberTil = new JLabel();
        numberTil.setFont(filmTitle);
        numberTil.setForeground(White);
        numberTil.setText("Number of ticket: ");

        // choose number of seat
        JButton btnSubstract = new JButton("-");
        JButton txtNumber = new JButton("0");
        txtNumber.setEnabled(false);
        JButton btnPlus = new JButton("+");
        groupNumber.add(numberTil);
        groupNumber.add(btnSubstract);
        groupNumber.add(txtNumber);
        groupNumber.add(btnPlus);
        groupNumber.setPreferredSize(
                new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 3 / 5, 40));
        groupNumber.setMinimumSize(
                new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 3 / 5, 40));
        groupNumber.setMaximumSize(
                new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 3 / 5, 40));

        // button action
        btnSubstract.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer current = Integer.parseInt(txtNumber.getText());
                if (current > 0) {
                    current--;
                    txtNumber.setText(current.toString());
                }
            }
        });
        btnPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer current = Integer.parseInt(txtNumber.getText());
                current++;
                txtNumber.setText(current.toString());
            }
        });

        // choosen seat
        JLabel seatTil = new JLabel();
        seatTil.setFont(filmTitle);
        seatTil.setForeground(White);
        seatTil.setText("Choosen seat: ");
        JButton txtSeat = new JButton("F6 - F7");
        txtSeat.setBackground(bgColor);
        txtSeat.setForeground(White);
        txtSeat.setEnabled(false);
        JPanel groupSeat = new JPanel();
        groupSeat.setBackground(bgColor);
        groupSeat.setLayout(new FlowLayout(FlowLayout.LEFT));
        groupSeat.setPreferredSize(
                new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 3 / 5, 40));
        groupSeat.setMaximumSize(
                new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 3 / 5, 40));
        groupSeat.setMinimumSize(
                new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 3 / 5, 40));
        groupSeat.add(seatTil);
        groupSeat.add(txtSeat);
        //PREPARE DATA
        int row = 0, col = 0;
        char[][] kindOfChair = null;
        boolean[][] statusBooking = null;
        
        long Pvip = 0;
        long Pcouple = 0;
        final long priceStandard = 45000;
        
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
        final char [][] status = kindOfChair;
        final long priceVIP = Pvip;
        final long priceCouple = Pcouple;
        
       
        
        for(int i = 0; i < listBooking.size(); ++i){
            if(data.getAuditoriumId().contains(listBooking.get(i).getAuditoriumId()) && data.getShowtimeId().contains(listBooking.get(i).getShowtimeId())){
                statusBooking = support.createStatusMatrix(listBooking.get(i), row, col);
                break;
            }
        }

        
        
        JToggleButton[][] followChoose = new JToggleButton[row][col];
        final int finalRow = row, finalCol = col;
        // LIST SEAT 
        JPanel listSeat = new JPanel();
        listSeat.setPreferredSize(new Dimension(col * CHAIR_WIDHT, row * CHAR_HEIGHT));
        listSeat.setMinimumSize(new Dimension(col * CHAIR_WIDHT, row * CHAR_HEIGHT));
        listSeat.setMaximumSize(new Dimension(col * CHAIR_WIDHT, row * CHAR_HEIGHT));
        listSeat.setBackground(transparentColor);
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
                followChoose[i][j] = printChair(color);
                listSeat.add(followChoose[i][j]);
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
        ImageIcon noteImg = setScale(340, 100, new ImageIcon(getClass().getResource("../media/note.png")));
        note.setIcon(noteImg);
        // button complete
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
                // TODO Auto-generated method stub
                try{
                    // resume the booking information 
                    //get list of position 
                    String listChoosen = support.createListOfPosition(followChoose, finalRow, finalCol);
                    //get number of ticket
                    String numberOfTicket = txtNumber.getText();
                    // real number of ticket 
                    int realNumberOfTicket;
                    if(!listChoosen.isEmpty())
                        realNumberOfTicket = 1;
                    else realNumberOfTicket = 0;
                    for(int i = 0; i < listChoosen.length(); ++i)
                        if(listChoosen.charAt(i) == ',') ++realNumberOfTicket;
                    
                    
                    // tính tiền vé 
                    int pos = -1;
                    int totalPrice = 0;
                    String position = "";
                    String temp = listChoosen;
                    while(!temp.isEmpty()){
                        pos = temp.indexOf(",");
                        if(pos == -1){
                           position = temp;
                           temp = "";
                        }else{
                            position = temp.substring(0,pos);
                            temp = temp.substring(pos+1);
                        }
                        int x = position.charAt(0) - 65;
                        int y = Integer.parseInt(position.substring(1))-1;
                        if(status[x][y] == 'V')
                            totalPrice += priceVIP;
                        else if(status[x][y] == 'C')
                                totalPrice += priceCouple;
                            else if(status[x][y] == 'N')
                                totalPrice += priceStandard;
                    }
                    
                    
                    
                    output.writeUTF("RESERVED/" + data.getAuditoriumId() + "/" + data.getShowtimeId() + "/" + listChoosen + "/" + realNumberOfTicket + "/" + numberOfTicket);
                    String response = input.readUTF();
                    if(response.contains("SUCCESS")){   //booking success
                        DialogInform dialog = new DialogInform(bookingFrame,"Thông báo",true, txtName.getText(), txtPhone.getText(),data,totalPrice, listChoosen);
                        dialog.setVisible(true);
                        System.err.println(response);

                    }else {     // booking failed
                        System.err.println("FAILED: " + response);
                        System.out.println(response);
                        bookingFrame.dispose();
                        mainFrame.setFocusable(true);
                    }
                    
                }catch(IOException err){
                    err.printStackTrace();
                }
            }

        });

        screen.add(title);
        screen.add(Box.createRigidArea(new Dimension(0, 50)));
        screen.add(txtName);
        screen.add(Box.createRigidArea(new Dimension(0,10)));
        screen.add(txtPhone);
        screen.add(Box.createRigidArea(new Dimension(0,10)));
        screen.add(groupNumber);
        screen.add(groupSeat);
        screen.add(listSeat);
        screen.add(note);
        screen.add(btnComplete);

        return screen;
    }
    
    public JScrollPane createBookingScreen(Showtime input, java.util.List<Booking> listBooking, java.util.List<auditorium> listAudi){
        JPanel booking = new JPanel();
    
        booking.setLayout(new BoxLayout(booking, BoxLayout.X_AXIS));
        booking.setPreferredSize(new Dimension(500,1250));
        booking.setBackground(bgColor);
        // thêm poster
        booking.add(posterForBooking(input));
        booking.add(createBookingArea(input, listBooking, listAudi));
        
        JScrollPane scrollContent = new JScrollPane(booking);
        scrollContent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollContent.setBackground(bgColor);
        scrollContent.getVerticalScrollBar().setUnitIncrement(16);
        scrollContent.getVerticalScrollBar().setBlockIncrement(64);

//        
        return scrollContent;
    }
    
    //color maybe "red","gray","blue","pink"
    public JToggleButton printChair(String color){
        JToggleButton output = new JToggleButton();
        output.setUI(new BasicButtonUI());
        output.setBackground(bgColor);
        if (color.equals("red")) {
            ImageIcon chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("../media/VIP_seat.png")));
            output.setIcon(chairImg);
        } else if (color.equals("pink")) {
            ImageIcon chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("../media/couple_seat.png")));
            output.setIcon(chairImg);
        } else if (color.equals("blue")) {
            ImageIcon chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("../media/standard_seat.png")));
            output.setIcon(chairImg);
        } else if (color.equals("gray")) {
            ImageIcon chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("../media/reserved_seat.png")));
            output.setIcon(chairImg);
            output.setEnabled(false);
        }
        output.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (output.isSelected()) {
                    // TODO Auto-generated method stub
                    ImageIcon chariImg = setScale(15, 15, new ImageIcon(getClass().getResource("../media/your_choose.png")));
                    output.setIcon(chariImg);
                    output.setBackground(bgColor);
                } else {
                    ImageIcon chairImg = null;
                    if(color.equals("red"))
                        chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("../media/VIP_seat.png")));
                    else if(color.equals("blue"))
                        chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("../media/standard_seat.png")));
                    else if(color.equals("pink"))
                        chairImg = setScale(15, 15, new ImageIcon(getClass().getResource("../media/couple_seat.png")));

                    output.setIcon(chairImg);
                    output.setBackground(bgColor);
                }
            }

        });
        return output;
    }
    
    public ImageIcon setScale(int width, int height, ImageIcon input){
        origin = input.getImage();
        resize = origin.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resize);
    }
    public void setAuditorium(java.util.List<auditorium> input){
        this.listAuditorium = input;
    }
    public void setShowtime(java.util.List<Showtime> input){
        this.listShowtime = input;
    }
    public void setBooking(java.util.List<Booking> input){
        this.listBookings = input;
    }
    public java.util.List<auditorium> getAuditorium(){return this.listAuditorium;}
    public java.util.List<Showtime> getShowtime(){ return this.listShowtime;}
    public java.util.List<Booking> getBooking(){return this.listBookings;}

    
    void showHomeScreen(java.util.List<Showtime> data){
        mainFrame = this;
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(bgColor);
        getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        mainFrame.add(createClientHeader());
        mainFrame.add(createHomeScreen(data));
        
        
        
    }
    
    void showBookingScreen(Showtime data){
        System.out.println("Come to ShowBookingScreen!!");
        //get booking status 
        java.util.List<Booking> listBooking = null;
        java.util.List<auditorium> listAudi = null;
        String idAuditorium = data.getAuditoriumId();
        try{
            String request = "GET/ListBooking/" + idAuditorium;
            output.writeUTF(request);
            String link = input.readUTF();
            System.out.println(link);
            int pos = link.indexOf("|");
            handleData handle = new handleData();
            listAudi = handle.readAuditoriumFile(link.substring(pos+1));
            listBooking = handle.readBookingStatus(link.substring(0,pos));
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        
        bookingFrame = new JFrame();
        bookingFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        bookingFrame.setBackground(bgColor);
        bookingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bookingFrame.getContentPane().setLayout(new BoxLayout(bookingFrame.getContentPane(),BoxLayout.PAGE_AXIS));
        bookingFrame.setLayout(new BoxLayout(bookingFrame.getContentPane(), BoxLayout.Y_AXIS));
        
        bookingFrame.add(createClientHeader());
        bookingFrame.add(createBookingScreen(data,listBooking, listAudi));
        bookingFrame.setVisible(true);
    }
    
    // for networking
    private static final int PORT = 8080;
    private static final String serverName = "192.168.75.1";
    private DataOutputStream output;
    private DataInputStream input;
    
    
    public clientLayout(){
                
        try{  
            Socket client = new Socket(serverName,PORT);
            if(client.isConnected()){
                System.out.println("Connected to server with address " + client.getRemoteSocketAddress());
                // tạo đối tượng truyền dữ liệu giữa client và server 
                input = new DataInputStream(client.getInputStream());
                output = new DataOutputStream(client.getOutputStream());
                String mess = "GET/ListShowtime";
                output.writeUTF(mess);
                String resp = input.readUTF();

                System.out.println("response message: " + resp);
                handleData handle = new handleData();
                java.util.List<Showtime> output = handle.readShowtimeFile(resp);
                System.out.println("output size:" + output.size());

                // show layout
                showHomeScreen(output);
                setVisible(true);
       
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
    
    public static void main(String args[]){
        clientLayout client = new clientLayout();
    }
    
   
}
