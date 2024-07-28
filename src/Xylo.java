import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Xylo extends JPanel {
    private Map<Character, String> soundMap;

    public Xylo() {
        // Initialize the frame
        JFrame XyloFrame = new JFrame("Xylo");
        XyloFrame.setSize(1040, 800);
        XyloFrame.setResizable(false);
        XyloFrame.setLocationRelativeTo(null);
        XyloFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setBackground(Color.BLACK);
        setLayout(null);

        // Initialize the sound map
        initializeSoundMap();

        addTitleandLine();
        displayXylo();
        addButtons();

        XyloFrame.setContentPane(this);
        XyloFrame.setVisible(true);

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

    private void addTitleandLine() {
        JLabel titleLabel = new JLabel("Xylophone Section");
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

    private void displayXylo() {
        try {
            ImageIcon frameXylo = new ImageIcon("D:\\JAVA\\JavaSwing\\Main\\src\\Assets\\framexylo.jpg");
            JLabel xyloLabel = new JLabel(frameXylo);
            xyloLabel.setBounds(-100, 100, 1240, 700);
            add(xyloLabel);
        } catch (Exception e) {
            System.out.println("Error displaying xylophone image: " + e.getMessage());
        }
    }

    private void addButtons() {
        // Define button positions and labels
        String[] buttons = {"A", "S", "D", "F", "H", "J", "K", "L"};
        int[][] positions = {
                {320, 250}, {370, 250}, {420, 250}, {470, 250}, {520, 250}, {570, 250}, {620, 250}, {670, 250}
        };

        // Create and add buttons
        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton(buttons[i]);
            button.setBounds(positions[i][0], positions[i][1], 45, 40);
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
        soundMap.put('A', "D:\\JAVA\\JavaSwing\\Main\\src\\soundXylo\\A.wav");
        soundMap.put('S', "D:\\JAVA\\JavaSwing\\Main\\src\\soundXylo\\S.wav");
        soundMap.put('D', "D:\\JAVA\\JavaSwing\\Main\\src\\soundXylo\\D.wav");
        soundMap.put('F', "D:\\JAVA\\JavaSwing\\Main\\src\\soundXylo\\F.wav");
        soundMap.put('H', "D:\\JAVA\\JavaSwing\\Main\\src\\soundXylo\\H.wav");
        soundMap.put('J', "D:\\JAVA\\JavaSwing\\Main\\src\\soundXylo\\J.wav");
        soundMap.put('K', "D:\\JAVA\\JavaSwing\\Main\\src\\soundXylo\\K.wav");
        soundMap.put('L', "D:\\JAVA\\JavaSwing\\Main\\src\\soundXylo\\L.wav");
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
