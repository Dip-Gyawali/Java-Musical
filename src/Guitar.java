import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Guitar extends JPanel {
    private Map<Character, String> soundMap;

    public Guitar() {

        JFrame guitarFrame = new JFrame("Guitar");
        guitarFrame.setSize(1040, 800);
        guitarFrame.setResizable(false);
        guitarFrame.setLocationRelativeTo(null);
        guitarFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addButtons();
        setBackground(Color.BLACK);
        setLayout(null);

        // Initialize the sound map
        initializeSoundMap();

        setTitleandLine();
        displayGuitar();


        guitarFrame.setContentPane(this);
        guitarFrame.setVisible(true);

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

    private void setTitleandLine() {
        JLabel titleLabel = new JLabel("Guitar Section");
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

    private void displayGuitar() {
        try {
            ImageIcon neckGuitar = new ImageIcon("D:\\JAVA\\JavaSwing\\Main\\src\\Assets\\AdobeStock_76072578.jpeg");
            JLabel guitarLabel = new JLabel(neckGuitar);
            guitarLabel.setBounds(0, 100, 1040, 700);
            add(guitarLabel);
        } catch (Exception e) {
            System.out.println("Error displaying guitar image: " + e.getMessage());
        }
    }

    private void addButtons() {
        // Define button positions and labels
        String[] buttons = {"A", "S", "D", "F", "G", "H", "Q", "W", "E", "R", "T", "Y", "Z", "X", "C", "V", "B", "N"};
        int[][] positions = {
                {110, 110}, {110, 240}, {110, 360}, {110, 470}, {110, 600}, {110, 720},
                {460, 110}, {460, 240}, {460, 360}, {460, 470}, {460, 600}, {460, 720},
                {850, 110}, {850, 240}, {850, 360}, {850, 470}, {850, 600}, {850, 720}
        };

        // Create and add buttons
        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton(buttons[i]);
            button.setBounds(positions[i][0], positions[i][1], 50, 50);
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
        soundMap.put('A', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\A.wav");
        soundMap.put('S', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\S.wav");
        soundMap.put('D', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\D.wav");
        soundMap.put('F', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\F.wav");
        soundMap.put('G', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\G.wav");
        soundMap.put('H', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\H.wav");
        soundMap.put('Q', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\Q.wav");
        soundMap.put('W', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\W.wav");
        soundMap.put('E', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\E.wav");
        soundMap.put('R', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\R.wav");
        soundMap.put('T', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\T.wav");
        soundMap.put('Y', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\Y.wav");
        soundMap.put('Z', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\Z.wav");
        soundMap.put('X', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\X.wav");
        soundMap.put('C', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\C.wav");
        soundMap.put('V', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\V.wav");
        soundMap.put('B', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\B.wav");
        soundMap.put('N', "D:\\JAVA\\JavaSwing\\Main\\src\\guitarSound\\N.wav");
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
