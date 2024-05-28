/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managecinema;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import data.*;
import java.util.*;

import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author User
 */
public class clientLayout extends JFrame{
    private java.util.List<auditorium> listAuditorium;
    private java.util.List<Showtime> listShowtime;
    private java.util.List<Booking> listBookings;
    
    private String dir;
    private JFrame mainFrame;
    private JPanel headerPanel, contentPanel;
    private Image origin, resize;
    private CardLayout card;
    // dimension
    private Dimension headerDimen = new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 145);
    private Dimension contentDimen = new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
    private Dimension aHalfContent = new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2,
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() + 200);
    private Dimension contentpart1 = new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 2 / 5,
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() + 200);
    private Dimension contentpart2 = new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 3 / 5,
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() + 200);
    private Dimension singlefilmDimen = new Dimension(222, 30);
    private Dimension bigPoster = new Dimension(335, 40);
    private Dimension bookingElement = new Dimension(450, 40);
    private Dimension notiDimen = new Dimension(720, 540);
    // color
    private Color bgColor = new Color(48, 48, 48);
    private Color headerColor = new Color(22, 22, 22);
    private Color transparentColor = new Color(0, 0, 0, 0);
    private Color btnColor2 = new Color(30, 183, 232);
    private Color White = new Color(255, 255, 255);
    private Color blur = new Color(21, 19, 23, 110);
    private Color btnColor = new Color(223, 33, 68);
    // font
    private Font filmTitle = new Font("Arial", Font.BOLD, 16);
    private Font bigFilmTitle = new Font("Arial", Font.BOLD, 16);
    private Font headerTitle = new Font("Comic Sans MS", Font.BOLD, 30);
    private Font title16 = new Font("Arial", Font.BOLD, 16);
    private Font title18 = new Font("Arial", Font.BOLD, 18);
    private Font title20 = new Font("Arial", Font.BOLD, 20);
    private Font title22 = new Font("Arial", Font.BOLD, 22);

    // size
    private final int CHAIR_WIDHT = 75;
    private final int CHAR_HEIGHT = 50;
    
    public clientLayout(){
        dir = System.getProperty("user.dir");
        initComponents();
    }
    
    public void initComponents(){
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame = this;
        // set background cho màn hình chính
        getContentPane().setBackground(bgColor);
        getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        // frame
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        // header layout
        headerPanel = createClientHeader();

        // content panel
        card = new CardLayout();
        contentPanel = new JPanel();
        contentPanel.setLayout(card);
        contentPanel.add("homeScreen", createHomeScreen());
        contentPanel.add("bookingScreen", createBookingScreen());
        contentPanel.add("successScreen", createSuccessScreen());
        // contentPanel = createHomeScreen();

        JScrollPane scrollContent = new JScrollPane(contentPanel);
        scrollContent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollContent.setBackground(bgColor);
        scrollContent.getVerticalScrollBar().setUnitIncrement(16);
        scrollContent.getVerticalScrollBar().setBlockIncrement(64);

        // add element to main frame
        mainFrame.add(headerPanel);
        mainFrame.add(scrollContent);
    }
    
    public JPanel createPosterFilm(){
        JPanel output = new JPanel();
        output.setLayout(new BoxLayout(output, BoxLayout.Y_AXIS));
        output.setBackground(transparentColor);

        JLabel filmArea = new JLabel();
        ImageIcon filmImg = setScale(222, 312, new ImageIcon(getClass().getResource("../media/film_image.png")));
        filmArea.setIcon(filmImg);

        JLabel titleArea = new JLabel();
        titleArea.setText("Avatar The Way of Water 2022");
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
        ratingArea.setText("7.8/10");
        durationArea.setIcon(durationImg);
        durationArea.setText("3h 20m");
        JPanel wholeArea = new JPanel();
        wholeArea.setLayout(new FlowLayout(FlowLayout.CENTER));
        wholeArea.setPreferredSize(singlefilmDimen);
        wholeArea.setBackground(transparentColor);
        wholeArea.add(ratingArea);
        wholeArea.add(Box.createRigidArea(new Dimension(20, 0)));
        wholeArea.add(durationArea);

        JButton btnchoose = new JButton();
        btnchoose.setText("7h30 - 10h");
        btnchoose.setPreferredSize(singlefilmDimen);
        btnchoose.setMinimumSize(singlefilmDimen);
        btnchoose.setMaximumSize(singlefilmDimen);
        btnchoose.setBackground(btnColor2);
        btnchoose.setForeground(White);
        btnchoose.setFont(title16);

        btnchoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(contentPanel, "bookingScreen");
            }
        });

        output.add(filmArea);
        output.add(wholeArea);
        output.add(titleArea);
        output.add(btnchoose);

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
                card.show(contentPanel, "homeScreen");
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
    
    public JPanel createHomeScreen() {
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout(FlowLayout.LEFT));
        content.setPreferredSize(contentDimen);
        content.setBackground(bgColor);
        content.add(createPosterFilm());
        content.add(createPosterFilm());
        content.add(createPosterFilm());
        content.add(createPosterFilm());
        content.add(createPosterFilm());
        content.add(createPosterFilm());
        content.add(createPosterFilm());
        content.add(createPosterFilm());
        return content;
    }
    
    public JPanel posterForBooking(){
        JPanel output = new JPanel();
        output.setBackground(bgColor);
        output.setPreferredSize(contentpart1);
        output.setMinimumSize(contentpart1);
        output.setMaximumSize(contentpart1);
        JPanel poster = new JPanel();
        poster.setBackground(bgColor);
        poster.setLayout(new BoxLayout(poster, BoxLayout.Y_AXIS));
        JLabel avatarArea = new JLabel();
        ImageIcon mainImg = setScale(335, 523, new ImageIcon(getClass().getResource("../media/film_image.png")));
        avatarArea.setIcon(mainImg);

        JLabel ratingArea = new JLabel();
        ratingArea.setFont(filmTitle);
        ratingArea.setForeground(White);
        ImageIcon ratingImg = setScale(36, 32, new ImageIcon(getClass().getResource("../media/rating.png")));
        ratingArea.setIcon(ratingImg);
        ratingArea.setText("7.6/10");

        JLabel durationArea = new JLabel();
        durationArea.setFont(filmTitle);
        durationArea.setForeground(White);
        ImageIcon durationImg = setScale(28, 28, new ImageIcon(getClass().getResource("../media/duration.png")));
        durationArea.setIcon(durationImg);
        durationArea.setText("3h 12m");
        JPanel group = new JPanel();

        group.setLayout(new FlowLayout(FlowLayout.CENTER));
        group.setBackground(bgColor);
        group.setPreferredSize(bigPoster);
        group.add(ratingArea);
        group.add(Box.createRigidArea(new Dimension(50, 0)));
        group.add(durationArea);

        JLabel title = new JLabel();
        title.setForeground(White);
        title.setFont(filmTitle);
        title.setLayout(new FlowLayout(FlowLayout.CENTER));
        title.setPreferredSize(bigPoster);
        title.setMinimumSize(bigPoster);
        title.setMaximumSize(bigPoster);
        title.setText("Avatar The Way of Water 2022");

        JButton time = new JButton("7h - 10h30");
        time.setPreferredSize(bigPoster);
        time.setMinimumSize(bigPoster);
        time.setMaximumSize(bigPoster);
        time.setBackground(btnColor2);
        time.setForeground(White);
        time.setFont(filmTitle);
        time.setEnabled(false);

        time.setFont(filmTitle);
        time.setForeground(White);

        poster.add(avatarArea);
        poster.add(group);
        poster.add(title);
        poster.add(time);

        output.add(poster);
        return output;
    }
    
    public JPanel createBookingArea(){
        JPanel output = new JPanel();
        output.setLayout(new BoxLayout(output, BoxLayout.Y_AXIS));
        output.setPreferredSize(contentpart2);
        output.setMinimumSize(contentpart2);
        output.setMaximumSize(contentpart2);
        output.setBackground(bgColor);
        JLabel title = new JLabel();
        title.setPreferredSize(bookingElement);
        title.setMinimumSize(bookingElement);
        title.setMaximumSize(bookingElement);
        title.setBackground(bgColor);
        title.setForeground(White);
        title.setFont(headerTitle);
        title.setText("BOOKING AREA ");

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

        // list seat
        JPanel listSeat = new JPanel();
        listSeat.setPreferredSize(new Dimension(10 * CHAIR_WIDHT, 8 * CHAR_HEIGHT));
        listSeat.setMinimumSize(new Dimension(10 * CHAIR_WIDHT, 8 * CHAR_HEIGHT));
        listSeat.setMaximumSize(new Dimension(10 * CHAIR_WIDHT, 8 * CHAR_HEIGHT));
        listSeat.setBackground(transparentColor);
        listSeat.setLayout(new FlowLayout(FlowLayout.CENTER));
        // in sai số lượng hàng và cột
        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 10; ++j) {
                listSeat.add(printChair("blue"));
                if (j > 10)
                    listSeat.add(new JPanel());
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
                card.show(contentPanel, "successScreen");
            }

        });

        output.add(title);
        output.add(Box.createRigidArea(new Dimension(0, 50)));
        output.add(groupNumber);
        output.add(groupSeat);
        output.add(listSeat);
        output.add(note);
        output.add(btnComplete);

        return output;
    }
    
    public JPanel createBookingScreen(){
        JPanel booking = new JPanel();
        booking.setLayout(new BoxLayout(booking, BoxLayout.X_AXIS));
        booking.setPreferredSize(contentDimen);
        booking.setBackground(bgColor);
        // thêm poster
        booking.add(posterForBooking());
        booking.add(createBookingArea());

        // thêm nội dung booking
        return booking;
    }
    
    //color maybe "red","gray","blue","pink"
    public JToggleButton printChair(String color){
        JToggleButton output = new JToggleButton();
        output.setUI(new BasicButtonUI());
        output.setBackground(bgColor);
        if (color.equals("red")) {
            ImageIcon chairImg = setScale(30, 30, new ImageIcon(getClass().getResource("../media/VIP_seat.png")));
            output.setIcon(chairImg);
        } else if (color.equals("pink")) {
            ImageIcon chairImg = setScale(30, 30, new ImageIcon(getClass().getResource("../media/couple_seat.png")));
            output.setIcon(chairImg);
        } else if (color.equals("blue")) {
            ImageIcon chairImg = setScale(30, 30, new ImageIcon(getClass().getResource("../media/standard_seat.png")));
            output.setIcon(chairImg);
        } else if (color.equals("gray")) {
            ImageIcon chairImg = setScale(30, 30, new ImageIcon(getClass().getResource("../media/reserved_seat.png")));
            output.setIcon(chairImg);
        }
        output.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (output.isSelected()) {
                    // TODO Auto-generated method stub
                    ImageIcon chariImg = setScale(30, 30, new ImageIcon(getClass().getResource("../media/your_choose.png")));
                    output.setIcon(chariImg);
                    output.setBackground(bgColor);
                } else {
                    ImageIcon chairImg = setScale(30, 30, new ImageIcon(getClass().getResource("../media/standard_seat.png")));
                    output.setIcon(chairImg);
                    output.setBackground(bgColor);
                }
            }

        });
        return output;
    }
    
    public JPanel createSuccessScreen(){
        JPanel output = new JPanel();
        output.setLayout(new BoxLayout(output, BoxLayout.X_AXIS));
        output.add(posterForBooking());
        output.add(createSuccessInform());
        return output;
    }
    
    public JPanel createSuccessInform(){
        JPanel output = new JPanel();
        output.setBackground(bgColor);
        JPanel cover = new JPanel();
        cover.setBackground(blur);
        cover.setPreferredSize(notiDimen);
        cover.setMinimumSize(notiDimen);
        cover.setMaximumSize(notiDimen);
        cover.setLayout(new BoxLayout(cover, BoxLayout.Y_AXIS));

        JLabel header = new JLabel();
        header.setForeground(White);
        header.setFont(bigFilmTitle);
        header.setText("THÔNG TIN ĐẶT VÉ ");

        JLabel code = new JLabel();
        code.setForeground(White);
        code.setFont(filmTitle);
        code.setText("Mã xuất vé: CF0129413723"); // ham tạo một string random

        JLabel name = new JLabel();
        name.setForeground(White);
        name.setFont(filmTitle);
        name.setText("Tên phim: Avatar The Way of Water 2022");

        JLabel time = new JLabel();
        time.setForeground(White);
        time.setFont(filmTitle);
        time.setText("Xuất chiếu: 7h-10h30");

        JLabel duration = new JLabel();
        duration.setForeground(White);
        duration.setFont(filmTitle);
        duration.setText("Thời lượng: 3h 12m");

        JLabel room = new JLabel();
        room.setForeground(White);
        room.setFont(filmTitle);
        room.setText("Rạp: 4");

        JLabel warning = new JLabel();
        warning.setForeground(btnColor);
        warning.setFont(filmTitle);
        warning.setText("(Vui lòng lưu lại thông tin mã xuất vé để in vé tại rạp phim)");

        cover.add(header);
        cover.add(Box.createRigidArea(new Dimension(0, 50)));
        cover.add(code);
        cover.add(name);
        cover.add(time);
        cover.add(duration);
        cover.add(room);
        cover.add(warning);
        output.add(cover);
        return output;
    }
    
    public static String generateRandomString(int length) {
        String character = "abcdefghijklmnopqrstuvwxyzABCDEFJHIJKLMNOPQRSTUVWXYZ";
        String number = "0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; ++i) {
            int index;
            char randomChar;
            if (i <= 3) {
                index = random.nextInt(character.length());
                randomChar = character.charAt(index);
            } else {
                index = random.nextInt(number.length());
                randomChar = character.charAt(index);
            }
            sb.append(randomChar);
        }
        return sb.toString();
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
    
    public static void main(String[] args){
        clientLayout gui = new clientLayout();
        gui.setVisible(true);
    }
}
