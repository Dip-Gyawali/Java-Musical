import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Bguitar extends JPanel {
    private Map<Character, String> soundMap;

    public Bguitar() {
        JFrame bguitarFrame = new JFrame("Bass Guitar");
        bguitarFrame.setSize(1040, 800);
        bguitarFrame.setResizable(false);
        bguitarFrame.setLocationRelativeTo(null);
        bguitarFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(Color.BLACK);
        setLayout(null);

        initializeSoundMap();
        addButtons();
        displayBguitar();
        addTitleandLine();

        bguitarFrame.add(this);
        bguitarFrame.setVisible(true);

        // Add key listener to the panel
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char keyChar = Character.toUpperCase(e.getKeyChar()); // Convert to uppercase to match soundMap keys
                if (soundMap.containsKey(keyChar)) {
                    playSound(soundMap.get(keyChar));
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow(); // Ensure the panel has focus

        // Add action listeners to buttons
        addButtonActionListeners();
    }

    private void displayBguitar() {
        try {
            ImageIcon frameFlute = new ImageIcon("D:\\JAVA\\JavaSwing\\Main\\src\\Assets\\neck.jpg");
            JLabel fluteLabel = new JLabel(frameFlute);
            fluteLabel.setBounds(-100, 90, 1240, 700);
            add(fluteLabel);
        } catch (Exception e) {
            System.out.println("Error displaying xylophone image: " + e.getMessage());
        }
    }

    private void addTitleandLine() {
        JLabel titleLabel = new JLabel("Bass Guitar");
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

    private void addButtons() {
        // Define button positions and labels
        String[] buttons = {"A", "S", "D", "F", "Q", "w", "E", "R", "Z", "X", "C", "V", "G", "H", "J", "K", "T", "Y", "U", "I", "B", "N", "m", "O", "1", "2", "3", "4", "5", "6", "7", "8"};
        int[][] positions = {
                {170, 305}, {170, 375}, {170, 445}, {170, 515},
                {280, 305}, {280, 375}, {280, 445}, {280, 520},
                {380, 305}, {380, 375}, {380, 445}, {380, 520},
                {500, 305}, {500, 375}, {500, 445}, {500, 520},
                {600, 305}, {600, 375}, {600, 445}, {600, 520},
                {690, 305}, {690, 375}, {690, 445}, {690, 520},
                {765, 305}, {765, 375}, {765, 450}, {765, 525},
                {832, 305}, {832, 385}, {832, 450}, {832, 530}
        };

        // Create and add buttons
        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton(buttons[i]);
            button.setBounds(positions[i][0], positions[i][1], 45, 45);
            button.setFont(new Font("Serif", Font.PLAIN, 15));
            add(button);
        }

        revalidate();
        repaint();
    }

    private void addButtonActionListeners() {
        for (Component comp : getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        playSound(soundMap.get(button.getText().charAt(0)));
                    }
                });
            }
        }
    }

    private void initializeSoundMap() {
        soundMap = new HashMap<>();
        soundMap.put('A', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\A.wav");
        soundMap.put('S', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\S.wav");
        soundMap.put('D', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\D.wav");
        soundMap.put('F', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\F.wav");
        soundMap.put('Q', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\Q.wav");
        soundMap.put('W', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\W.wav");
        soundMap.put('E', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\E.wav");
        soundMap.put('R', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\R.wav");
        soundMap.put('Z', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\Z.wav");
        soundMap.put('X', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\X.wav");
        soundMap.put('C', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\C.wav");
        soundMap.put('V', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\V.wav");
        soundMap.put('G', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\G.wav");
        soundMap.put('H', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\H.wav");
        soundMap.put('J', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\J.wav");
        soundMap.put('K', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\K.wav");
        soundMap.put('T', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\T.wav");
        soundMap.put('Y', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\Y.wav");
        soundMap.put('U', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\U.wav");
        soundMap.put('I', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\I.wav");
        soundMap.put('B', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\B.wav");
        soundMap.put('N', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\N.wav");
        soundMap.put('M', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\M.wav");
        soundMap.put('O', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\O.wav");
        soundMap.put('1', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\1.wav");
        soundMap.put('2', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\N.wav");
        soundMap.put('3', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\M.wav");
        soundMap.put('4', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\O.wav");
        soundMap.put('5', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\5.wav");
        soundMap.put('6', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\N.wav");
        soundMap.put('7', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\M.wav");
        soundMap.put('8', "D:\\JAVA\\JavaSwing\\Main\\src\\bGuitarSound\\O.wav");
    }

    private void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            clip.start();
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }
}
