/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managecinema;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

/**
 *
 * @author User
 */
public class Style {
    // DIMENSION
    public Dimension headerDimen = new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 145);
    public Dimension textFieldDimen = new Dimension(800, 40);
//    public Dimension singlefilmDimen = new Dimension(222, 30);
        public Dimension singlefilmDimen = new Dimension(150, 30);
    public  Dimension contentpart2 = new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 3.8) / 5,
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() + 200);
    public Dimension BigPosterDimen = new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 1.2) / 5,
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() + 200);
    public Dimension bigPoster = new Dimension(335, 40);
    public Dimension bookingElement = new Dimension(450, 40);

    // COLOR
    public Color bgColor = new Color(48, 48, 48);
    public Color headerColor = new Color(22, 22, 22);
    public Color White = new Color(255, 255, 255);
    public Color btnColor = new Color(223, 33, 68);
    public Color lightGray = new Color(102, 102, 102, 100);
    public Color bgLighGray = new Color(217, 217, 217);
    public Color transparentColor = new Color(0, 0, 0, 0);
    public Color btnColor2 = new Color(30, 183, 232);
    public Color blur = new Color(21, 19, 23, 110);
    public Color subTextColor = new Color(238,187,7);

    // FONT
    public Font headerTitle = new Font("Arial", Font.BOLD, 30);
    public Font title18 = new Font("Arial", Font.BOLD, 18);
    public Font title20 = new Font("Arial", Font.BOLD, 20);
    public Font title16 = new Font("Arial", Font.BOLD, 16);
    public Font filmTitle = new Font("Arial", Font.BOLD, 16);
    public Font bigFilmTitle = new Font("Arial", Font.BOLD, 16);
    public Font headerClientTitle = new Font("Comic Sans MS", Font.BOLD, 30);
    public Font title22 = new Font("Arial", Font.BOLD, 22);
    public Font title26 = new Font("Arial", Font.BOLD, 26);
    public Font title7 = new Font("Arial", Font.BOLD, 9);

    
}
