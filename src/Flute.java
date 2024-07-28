import javax.swing.*;
import java.awt.*;

public class Flute extends JPanel {
    Flute(){
        JFrame fluteFrame = new JFrame("Flute");
        fluteFrame.setSize(1040, 800);
        fluteFrame.setResizable(false);
        fluteFrame.setLocationRelativeTo(null);
        fluteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(Color.BLACK);
        setLayout(null);

        fluteFrame.setVisible(true);
        displayFlute();
        addTitleandLine();

        fluteFrame.add(this);
    }

    private void displayFlute() {
        try {
            ImageIcon frameFlute = new ImageIcon("D:\\JAVA\\JavaSwing\\Main\\src\\Assets\\fluteImg.jpg");
            JLabel fluteLabel = new JLabel(frameFlute);
            fluteLabel.setBounds(-100, 90, 1240, 700);
            add(fluteLabel);
        } catch (Exception e) {
            System.out.println("Error displaying xylophone image: " + e.getMessage());
        }
    }

    private void addTitleandLine() {
        JLabel titleLabel = new JLabel("Flute Section");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setBounds(420, 20, 200, 30);
        add(titleLabel);

        JSeparator separator = new JSeparator();
        separator.setBackground(Color.WHITE);
        separator.setForeground(Color.WHITE);
        separator.setBounds(0, 60, 1040, 2);
        add(separator);
    }
    private void addButtons(){

    }
}
