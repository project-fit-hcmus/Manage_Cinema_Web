/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author User
 */

//VD: [VIP|D5|H16|60000]
// chỉ có 2 loại kind là VIP và COUPLE, STANDARD sẽ là phần còn lại của màn hình chưa được dãn nhã VIP hoặc COUPLE
public class kind {
    private String name; // VIP and COUPLE
    private String start;
    private String end;
    private long price;

    public void readInfo(String input) {
        int pos = input.indexOf("|");
        name = input.substring(1, pos);
        input = input.substring(pos + 1);

        pos = input.indexOf("|");
        start = input.substring(0, pos);
        input = input.substring(pos + 1);

        pos = input.indexOf("|");
        end = input.substring(0, pos);
        input = input.substring(pos + 1);

        pos = input.indexOf("]");
        price = Long.parseLong(input.substring(0, pos));
    }

    public void PrintInfo() {
        System.out.println("Name: " + name);
        System.out.println("Start at: " + start);
        System.out.println("End at: " + end);
        System.out.println("Price: " + price);
    }
}
