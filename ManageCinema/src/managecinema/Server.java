/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managecinema;
import data.auditorium;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 *
 * @author User
 */
public class Server extends JFrame{
    //layout setup 
    private JFrame mainFrame;
    private JPanel headerPanel, contentPanel;
    private Image origin, resize;
    private CardLayout card;

    // DIMENSION
    private Dimension headerDimen = new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 145);
    private Dimension textFieldDimen = new Dimension(800, 40);
    // COLOR
    private Color bgColor = new Color(48, 48, 48);
    private Color headerColor = new Color(22, 22, 22);
    private Color White = new Color(255, 255, 255);
    private Color btnColor = new Color(223, 33, 68);
    private Color lightGray = new Color(102, 102, 102, 100);
    private Color bgLighGray = new Color(217, 217, 217);
    // FONT
    private Font headerTitle = new Font("Arial", Font.BOLD, 30);
    private Font title18 = new Font("Arial", Font.BOLD, 18);
    private Font title20 = new Font("Arial", Font.BOLD, 20);
    
    //NETWORKING SETUP 
    private ServerSocket serverSocket;
    private static final int PORT = 8080;
    private String dir = "";
    private serverLayout layout;
    private String[] listAudi = { "RAP01", "RAP02" };
    private ManageCinema manageServer = new ManageCinema();
    private handleData handleServer = new handleData();
    
    public JPanel createContentPanel() {
        JPanel output = new JPanel();
        card = new CardLayout();
        output.setLayout(card);
        output.add("stage", createStageSetting());
        output.add("auditorium", createShowtimeSetting());
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
        
        JTextField audiCombo = new JTextField("Chọn rạp muốn cấu hình");
        
        
//        DefaultComboBoxModel model = new DefaultComboBoxModel();
//        for (int i = 0; i < this.listAudi.length; ++i)
//            model.addElement(this.listAudi[i]);
//        JComboBox audiCombo = new JComboBox(model);
//        audiCombo.setSelectedItem(0);
        audiCombo.setBackground(bgLighGray);
        audiCombo.setFont(title18);
        audiCombo.setForeground(lightGray);
        
        audiCombo.setPreferredSize(textFieldDimen);
        audiCombo.setMinimumSize(textFieldDimen);
        audiCombo.setMaximumSize(textFieldDimen);
        audiCombo.setBackground(bgLighGray);

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

        JTextField txtCol = new JTextField("Số lượng cột");
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
        txtVIPFrom.setEnabled(false);
        txtVIPFrom.setBackground(Color.GRAY);
 
        txtVIPTo.setFont(title18);
        txtVIPTo.setForeground(lightGray);
        txtVIPTo.setEnabled(false);
        txtVIPTo.setBackground(Color.GRAY);

        txtVIPPrice.setFont(title18);
        txtVIPPrice.setForeground(lightGray);
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
                    txtVIPFrom.setBackground(bgLighGray);
                    txtVIPTo.setBackground(bgLighGray);
                    txtVIPPrice.setBackground(bgLighGray);

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
        

        groupVIP.add(VIPCheck);
        groupVIP.add(VIPContent);

        //SET UP for couple area
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
        txtCoupleFrom.setBackground(Color.GRAY);
        txtCoupleFrom.setEnabled(false);
        
        txtCoupleTo.setFont(title18);
        txtCoupleTo.setForeground(lightGray);
        txtCoupleTo.setBackground(Color.GRAY);
        txtCoupleTo.setEnabled(false);
        
        txtCouplePrice.setFont(title18);
        txtCouplePrice.setForeground(lightGray);
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
                    txtCoupleFrom.setBackground(bgLighGray);
                    txtCoupleTo.setBackground(bgLighGray);
                    txtCouplePrice.setBackground(bgLighGray);
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
        

        groupCouple.add(CoupleCheck);
        groupCouple.add(CoupleContent);

        JButton btnComplete = new JButton("Hoàn Thành");
        btnComplete.setBackground(btnColor);
        btnComplete.setFont(title20);
        btnComplete.setForeground(White);
        
        //bnt Complete click action 
        btnComplete.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //get data 
                String Id="", fromVIP="", toVIP="", fromCouple="", toCouple="";
                int col, row;
                long priceVIP =0, priceCouple=0;
                boolean VIP=false, Couple=false;
                
//                Id = audiCombo.getSelectedItem().toString();
                Id = audiCombo.getText();
                row = Integer.parseInt(txtRow.getText());
                col = Integer.parseInt(txtCol.getText());
                
                if(VIPCheck.isSelected()){
                    VIP = true;
                    fromVIP = txtVIPFrom.getText();
                    toVIP = txtVIPTo.getText();
                    priceVIP = Long.parseLong(txtVIPPrice.getText());
                }
                if(CoupleCheck.isSelected()){
                    Couple = true;
                    fromCouple = txtCoupleFrom.getText();
                    toCouple = txtCoupleTo.getText();
                    priceCouple = Long.parseLong(txtCouplePrice.getText());
                }
                System.err.println("Id: " + Id );
                System.err.println("row -- col:" + row + ", " +col);
                System.err.println("VIP: " + VIP + "--" + fromVIP + "---" + toVIP + "---" + priceVIP);
                System.err.println("Couple: "  + Couple + "---" + fromCouple + "---" + toCouple + "---" + priceCouple);
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
//        output.add(scrollPane);
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

    public JPanel createShowtimeSetting() {
        JPanel output = new JPanel();
        output.setLayout(new BoxLayout(output, BoxLayout.Y_AXIS));
        output.setBackground(bgColor);
        JLabel linkImg = new JLabel();

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
        
        final JFileChooser fileChooser = new JFileChooser();
        btnAddImg.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int returnVal = fileChooser.showOpenDialog(mainFrame);
                if( returnVal == JFileChooser.APPROVE_OPTION){
                    java.io.File file = fileChooser.getSelectedFile();
                    btnAddImg.setText(file.getName());
                    System.out.println("file choosen: " + file);
                    linkImg.setText(file.toString());
                    System.out.println("file name: " + file.getName());
                }
                else{
                    System.out.println("Action is canceled!!");
                }
            }
        });

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


        //create data for combo box 
        DefaultComboBoxModel modelHour = new DefaultComboBoxModel();
        for(int i = 1; i <= 24; ++i )
            modelHour.addElement(i + " h");
        DefaultComboBoxModel modelMinute = new DefaultComboBoxModel();
        for(int i = 0; i < 60; i = i + 15)
            modelMinute.addElement(i);

        JPanel startTime = new JPanel();
        startTime.setLayout(new BoxLayout(startTime,BoxLayout.X_AXIS));
        startTime.setPreferredSize(textFieldDimen);
        startTime.setMinimumSize(textFieldDimen);
        startTime.setMaximumSize(textFieldDimen);
        startTime.setBackground(bgColor);

        JComboBox hourCombo = new JComboBox(modelHour);
        hourCombo.setSelectedItem(0);
        hourCombo.setFont(title18);
        hourCombo.setBackground(bgLighGray);
        hourCombo.setForeground(lightGray);
        JComboBox minuteCombo = new JComboBox(modelMinute);
        minuteCombo.setSelectedItem(0);
        minuteCombo.setFont(title18);
        minuteCombo.setBackground(bgLighGray);
        minuteCombo.setForeground(lightGray);
        
        JLabel txtStartTime = new JLabel("Chọn thời gian xuất chiếu: ");
        txtStartTime.setFont(title18);
        txtStartTime.setForeground(White);
        
        startTime.add(txtStartTime);
        startTime.add(hourCombo);
        startTime.add(Box.createRigidArea(new Dimension(10,0)));
        startTime.add(minuteCombo);
        
        //get list auditorium 
        java.util.List<auditorium> raw = handleServer.readAuditoriumFile(dir + "/src/data/auditorium.txt");
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for(int i = 0; i < raw.size(); ++i)
            model.addElement(raw.get(i).getID());

        JComboBox audiCombo = new JComboBox(model);
        audiCombo.setSelectedItem(0);
        audiCombo.setBackground(bgLighGray);
        audiCombo.setFont(title18);
        audiCombo.setForeground(lightGray);
        audiCombo.setPreferredSize(textFieldDimen);
        audiCombo.setMinimumSize(textFieldDimen);
        audiCombo.setMaximumSize(textFieldDimen);
        audiCombo.setBackground(bgLighGray);


        JButton btnComplete = new JButton("Hoàn Thành");
        btnComplete.setBackground(btnColor);
        btnComplete.setFont(title20);
        btnComplete.setForeground(White);
        
        btnComplete.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String filmTitle = txtName.getText();
                String avatarUrl = linkImg.getText();
                int duration = Integer.parseInt(txtTime.getText());
                double rating = Double.parseDouble(txtRating.getText());
                String temp = hourCombo.getSelectedItem().toString();
                int pos = temp.indexOf(" ");
                String auditoriumID = audiCombo.getSelectedItem().toString();
                String time = temp.substring(0,pos) + ":" + minuteCombo.getSelectedItem().toString();
                System.out.println("film name: " + filmTitle);
                System.out.println("avatar URL: " + avatarUrl);
                System.out.println("rating: " + rating);
                System.out.println("duration: " + duration);
                System.out.println("time: " + time);
                System.out.println("auditorium: " + auditoriumID);
                String result = manageServer.setupShowtime(filmTitle,avatarUrl,duration,rating,time,auditoriumID);
                if(result.contains("true")){
                     JOptionPane.showMessageDialog(null, "Cấu hình xuất chiếu thành công!!!","Notification",JOptionPane.WARNING_MESSAGE);
                     txtName.setText("Nhập tên film");
                     btnAddImg.setText("Thêm hình ảnh");
                     txtTime.setText("Thời lượng(Phút)");
                     txtRating.setText("Đánh giá");
                     //failed 
                     hourCombo.setSelectedItem(0);
                     minuteCombo.setSelectedItem(0);
                     audiCombo.setSelectedItem(0);
                             
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

        JButton btnStage = new JButton("Cấu hình sân khấu");
        btnStage.setBackground(btnColor);
        btnStage.setFont(title20);
        btnStage.setForeground(White);
        btnStage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(contentPanel, "stage");
            }
        });
        JButton btnAuditorium = new JButton("Cấu hình xuất chiếu");
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
    

    // scale một ảnh với kích thước đầu vào (width,height)
    public ImageIcon setScale(int width, int height, ImageIcon input) {
        origin = input.getImage();
        resize = origin.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resize);
    }

    public void ShowHomeScreen(){
        mainFrame = this;
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(bgColor);
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
            layout.setVisible(false);
            
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
                            resp = dir + "\\src\\data\\showtime.txt";
                        }
                        if(mess.contains("ListBooking")){
                            resp = dir +  "\\src\\data\\booking.txt|";
                            resp += dir + "\\src\\data\\auditorium.txt";
                        }
                        if(mess.contains("ListAuditorium")){
                            resp = dir + "\\src\\data\\auditorium.txt";
                        }
                    }else
                    if(mess.contains("RESERVED")){
                        String result = manage.solveBooking(mess);
                        if(result.contains("true")){
                            resp = "SUCCESS";
                        }
                        else {
                            resp = "FAILED" + result;
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
