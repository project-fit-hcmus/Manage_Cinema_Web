/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managecinema;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.DigestException;

public class serverLayout extends JFrame {

    private String[] listAudi = { "RAP01", "RAP02" };

    private JFrame mainFrame;
    private JPanel headerPanel, contentPanel;
    private Image origin, resize;
    private CardLayout card;

    // DIMENSION
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
    private Dimension textFieldDimen = new Dimension(800, 40);
    private Dimension textFieldSmallDimen = new Dimension(240, 40);
    // COLOR
    private Color bgColor = new Color(48, 48, 48);
    private Color headerColor = new Color(22, 22, 22);
    private Color transparentColor = new Color(0, 0, 0, 0);
    private Color btnColor2 = new Color(30, 183, 232);
    private Color White = new Color(255, 255, 255);
    private Color blur = new Color(21, 19, 23, 110);
    private Color btnColor = new Color(223, 33, 68);
    private Color lightGray = new Color(102, 102, 102, 100);
    private Color bgLighGray = new Color(217, 217, 217);
    // FONT
    private Font filmTitle = new Font("Arial", Font.BOLD, 16);
    private Font bigFilmTitle = new Font("Arial", Font.BOLD, 16);
    private Font headerTitle = new Font("Arial", Font.BOLD, 30);
    private Font title16 = new Font("Arial", Font.BOLD, 16);
    private Font title18 = new Font("Arial", Font.BOLD, 18);
    private Font title20 = new Font("Arial", Font.BOLD, 20);
    private Font title22 = new Font("Arial", Font.BOLD, 22);

    public serverLayout() {
        initComponents();
    }

    public void setListAuditorium(String[] input) {
        this.listAudi = input;
    }

    public void initComponents() {
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame = this;
        // set background cho màn hình chính
        getContentPane().setBackground(bgColor);
        getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        // set component
        headerPanel = createHeaderPanel();
        contentPanel = createContentPanel();

        mainFrame.add(headerPanel);
        mainFrame.add(contentPanel);
    }

    public JPanel createHeaderPanel() {
        JPanel output = new JPanel();
        output.setLayout(new BoxLayout(output, BoxLayout.X_AXIS));
        output.setBackground(headerColor);
        output.setPreferredSize(headerDimen);
        output.setMinimumSize(headerDimen);
        output.setMaximumSize(headerDimen);
        ImageIcon headerLogo = setScale(158, 88, new ImageIcon(getClass().getResource("../media/logo.png")));
        JLabel headerLogoArea = new JLabel();
        headerLogoArea.setIcon(headerLogo);
        headerLogoArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // move to main screen of server(using card layout to display )
            }
        });

        JButton btnStage = new JButton("Cấu hình xuất chiếu");
        btnStage.setBackground(btnColor);
        btnStage.setFont(title20);
        btnStage.setForeground(White);
        btnStage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(contentPanel, "stage");
            }
        });
        JButton btnAuditorium = new JButton("Cấu hình rạp chiếu phim");
        btnAuditorium.setBackground(btnColor);
        btnAuditorium.setFont(title20);
        btnAuditorium.setForeground(White);
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

    public JPanel createContentPanel() {
        JPanel output = new JPanel();
        card = new CardLayout();
        output.setLayout(card);
        output.add("stage", createStageSetting());
        output.add("auditorium", createAuditorumSetting());
        return output;
    }

    public JPanel createStageSetting() {
        JPanel output = new JPanel();
        output.setLayout(new BoxLayout(output, BoxLayout.Y_AXIS));
        output.setBackground(bgColor);

        JLabel title = new JLabel();
        title.setText("Cấu hình sân khấu");
        title.setFont(headerTitle);
        title.setForeground(White);

        DefaultComboBoxModel listAudi = new DefaultComboBoxModel();
        for (int i = 0; i < this.listAudi.length; ++i)
            listAudi.addElement(this.listAudi[i]);
        JComboBox audiCombo = new JComboBox(listAudi);
        audiCombo.setSelectedItem(0);
        audiCombo.setBackground(bgLighGray);
        audiCombo.setFont(title18);
        audiCombo.setForeground(lightGray);
        JScrollPane scrollPane = new JScrollPane(audiCombo);
        scrollPane.setPreferredSize(textFieldDimen);
        scrollPane.setMinimumSize(textFieldDimen);
        scrollPane.setMaximumSize(textFieldDimen);
        scrollPane.setBackground(bgLighGray);

        JPanel groupColRow = new JPanel();
        groupColRow.setLayout(new BoxLayout(groupColRow, BoxLayout.X_AXIS));
        groupColRow.setBackground(bgColor);
        groupColRow.setPreferredSize(textFieldDimen);
        groupColRow.setMinimumSize(textFieldDimen);
        groupColRow.setMaximumSize(textFieldDimen);

        JTextField txtRow = new JTextField("Số lượng hàng");
        txtRow.setFont(title18);
        txtRow.setForeground(lightGray);
        txtRow.setBackground(bgLighGray);

        JTextField txtCol = new JTextField("Số lượng hàng");
        txtCol.setFont(title18);
        txtCol.setForeground(lightGray);
        txtCol.setBackground(bgLighGray);

        groupColRow.add(txtRow);
        groupColRow.add(Box.createRigidArea(new Dimension(40, 0)));
        groupColRow.add(txtCol);

        JPanel groupVIP = new JPanel();
        groupVIP.setLayout(new BoxLayout(groupVIP, BoxLayout.Y_AXIS));
        groupVIP.setBackground(bgColor);

        JCheckBox VIPCheck = new JCheckBox("Thực hiện cấu hình cho khu vực VIP");
        VIPCheck.setMnemonic(KeyEvent.VK_C);
        VIPCheck.setBackground(bgColor);
        VIPCheck.setFont(title18);
        VIPCheck.setForeground(Color.RED);

        JPanel VIPContent = new JPanel();
        VIPContent.setLayout(new BoxLayout(VIPContent, BoxLayout.X_AXIS));
        VIPContent.setPreferredSize(textFieldDimen);
        VIPContent.setMaximumSize(textFieldDimen);
        VIPContent.setMinimumSize(textFieldDimen);
        VIPContent.setBackground(bgColor);

        // TEMP
        JTextField txtVIPFrom = new JTextField("Từ");
        JTextField txtVIPTo = new JTextField("Đến");
        JTextField txtVIPPrice = new JTextField("Giá vé (VNĐ)");
        txtVIPFrom.setFont(title18);
        txtVIPFrom.setForeground(lightGray);
        txtVIPTo.setFont(title18);
        txtVIPTo.setForeground(lightGray);
        txtVIPPrice.setFont(title18);
        txtVIPPrice.setForeground(lightGray);

        VIPContent.add(txtVIPFrom);
        VIPContent.add(Box.createRigidArea(new Dimension(10, 0)));
        VIPContent.add(txtVIPTo);
        VIPContent.add(Box.createRigidArea(new Dimension(60, 0)));
        VIPContent.add(txtVIPPrice);

        groupVIP.add(VIPCheck);
        groupVIP.add(VIPContent);

        JPanel groupCouple = new JPanel();
        groupCouple.setLayout(new BoxLayout(groupCouple, BoxLayout.Y_AXIS));
        groupCouple.setBackground(bgColor);

        JCheckBox CoupleCheck = new JCheckBox("Thực hiện cấu hình cho khu vực Couple");
        CoupleCheck.setMnemonic(KeyEvent.VK_C);
        CoupleCheck.setBackground(bgColor);
        CoupleCheck.setFont(title18);
        CoupleCheck.setForeground(Color.RED);

        JPanel CoupleContent = new JPanel();
        CoupleContent.setLayout(new BoxLayout(CoupleContent, BoxLayout.X_AXIS));
        CoupleContent.setPreferredSize(textFieldDimen);
        CoupleContent.setMaximumSize(textFieldDimen);
        CoupleContent.setMinimumSize(textFieldDimen);
        CoupleContent.setBackground(bgColor);

        // TEMP
        JTextField txtCoupleFrom = new JTextField("Từ");
        JTextField txtCoupleTo = new JTextField("Đến");
        JTextField txtCouplePrice = new JTextField("Giá vé (VNĐ)");
        txtCoupleFrom.setFont(title18);
        txtCoupleFrom.setForeground(lightGray);
        txtCoupleTo.setFont(title18);
        txtCoupleTo.setForeground(lightGray);
        txtCouplePrice.setFont(title18);
        txtCouplePrice.setForeground(lightGray);

        CoupleContent.add(txtCoupleFrom);
        CoupleContent.add(Box.createRigidArea(new Dimension(10, 0)));
        CoupleContent.add(txtCoupleTo);
        CoupleContent.add(Box.createRigidArea(new Dimension(60, 0)));
        CoupleContent.add(txtCouplePrice);

        groupCouple.add(CoupleCheck);
        groupCouple.add(CoupleContent);

        JButton btnComplete = new JButton("Hoàn Thành");
        btnComplete.setBackground(btnColor);
        btnComplete.setFont(title20);
        btnComplete.setForeground(White);

        output.add(title);
        output.add(Box.createRigidArea(new Dimension(0, 60)));
        output.add(scrollPane);
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

    public JPanel createAuditorumSetting() {
        JPanel output = new JPanel();
        output.setLayout(new BoxLayout(output, BoxLayout.Y_AXIS));
        output.setBackground(bgColor);

        JLabel title = new JLabel();
        title.setText("Thêm xuất chiếu");
        title.setFont(headerTitle);
        title.setForeground(White);

        JTextField txtName = new JTextField("Nhập tên film");
        txtName.setPreferredSize(textFieldDimen);
        txtName.setMinimumSize(textFieldDimen);
        txtName.setMaximumSize(textFieldDimen);
        txtName.setFont(title20);
        txtName.setForeground(lightGray);
        txtName.setBackground(bgLighGray);

        JPanel groupfilm = new JPanel();
        groupfilm.setLayout(new BoxLayout(groupfilm, BoxLayout.X_AXIS));
        groupfilm.setPreferredSize(textFieldDimen);
        groupfilm.setMaximumSize(textFieldDimen);
        groupfilm.setMinimumSize(textFieldDimen);
        groupfilm.setBackground(bgColor);

        JButton btnAddImg = new JButton();
        ImageIcon icon = setScale(40, 30, new ImageIcon(getClass().getResource("../media/addImg.png")));
        btnAddImg.setIcon(icon);
        btnAddImg.setText("Thêm hình ảnh");
        btnAddImg.setFont(title18);
        btnAddImg.setForeground(lightGray);
        btnAddImg.setBackground(bgLighGray);

        JTextField txtTime = new JTextField("Thời lượng (phút)");
        txtTime.setFont(title18);
        txtTime.setForeground(lightGray);
        txtTime.setBackground(bgLighGray);

        JTextField txtRating = new JTextField("Đánh giá");
        txtRating.setFont(title18);
        txtRating.setForeground(lightGray);
        txtRating.setBackground(bgLighGray);

        groupfilm.add(btnAddImg);
        groupfilm.add(Box.createRigidArea(new Dimension(10, 0)));
        groupfilm.add(txtTime);
        groupfilm.add(Box.createRigidArea(new Dimension(10, 0)));
        groupfilm.add(txtRating);

        JTextField txtShowtime = new JTextField("Chọn xuất chiếu");
        txtShowtime.setPreferredSize(textFieldDimen);
        txtShowtime.setMinimumSize(textFieldDimen);
        txtShowtime.setMaximumSize(textFieldDimen);
        txtShowtime.setFont(title20);
        txtShowtime.setForeground(lightGray);
        txtShowtime.setBackground(bgLighGray);

        DefaultComboBoxModel listAudi = new DefaultComboBoxModel();
        for (int i = 0; i < this.listAudi.length; ++i)
            listAudi.addElement(this.listAudi[i]);
        JComboBox audiCombo = new JComboBox(listAudi);
        audiCombo.setSelectedItem(0);
        audiCombo.setBackground(bgLighGray);
        audiCombo.setFont(title18);
        audiCombo.setForeground(lightGray);
        JScrollPane scrollPane = new JScrollPane(audiCombo);
        scrollPane.setPreferredSize(textFieldDimen);
        scrollPane.setMinimumSize(textFieldDimen);
        scrollPane.setMaximumSize(textFieldDimen);
        scrollPane.setBackground(bgLighGray);

        JButton btnComplete = new JButton("Hoàn Thành");
        btnComplete.setBackground(btnColor);
        btnComplete.setFont(title20);
        btnComplete.setForeground(White);

        output.add(title);
        output.add(Box.createRigidArea(new Dimension(0, 60)));
        output.add(txtName);
        output.add(Box.createRigidArea(new Dimension(0, 10)));
        output.add(groupfilm);
        output.add(Box.createRigidArea(new Dimension(0, 10)));
        output.add(txtShowtime);
        output.add(Box.createRigidArea(new Dimension(0, 10)));
        output.add(scrollPane);
        output.add(Box.createRigidArea(new Dimension(0, 60)));
        output.add(btnComplete);
        return output;
    }

    // scale một ảnh với kích thước đầu vào (width,height)
    public ImageIcon setScale(int width, int height, ImageIcon input) {
        origin = input.getImage();
        resize = origin.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resize);
    }

    public static void main(String[] args) {
        serverLayout layout = new serverLayout();
        layout.setVisible(true);
    }

}

