import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Musical Instrument");
        frame.setResizable(false);
        Menu menu = new Menu();
        frame.add(menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1040,800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
