import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Piano extends JPanel {
    Image logoImage;
    Map<Character, String> soundMap;
    Map<String, JButton> buttonMap;

    Piano() {
        JFrame pianoFrame = new JFrame("Piano");
        pianoFrame.setSize(1040, 800);
        pianoFrame.setResizable(false);
        pianoFrame.setLocationRelativeTo(null);
        pianoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(Color.BLACK);
        setLayout(null);

        // Initialize the sound map
        initializeSoundMap();

        addTitleandLine();
        addLogo();
        addButtons();

        pianoFrame.setContentPane(this);
        pianoFrame.setVisible(true);

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
        JLabel titleLabel = new JLabel("Piano Section");
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

    private void addLogo() {
        try {
            logoImage = ImageIO.read(new File("D:\\JAVA\\JavaSwing\\Main\\src\\Assets\\musicLogo.jpg"));
        } catch (IOException e) {
            System.out.println("Error displaying keyboard image: " + e.getMessage());
        }
    }

    private void addButtons() {
        buttonMap = new HashMap<>();

        JPanel blackButtonPanel = new JPanel(new GridLayout(1, 10));
        blackButtonPanel.setBounds(10, 340, 1000, 200);
        String[] blackLabels = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
        addButtonsToPanel(blackButtonPanel, blackLabels, Color.GRAY);

        JPanel whiteButtonPanel = new JPanel(new GridLayout(1, 15));
        whiteButtonPanel.setBounds(10, 540, 1000, 200);
        String[] whiteLabels = {"A", "S", "D", "F", "G", "H", "J", "K", "L", "X", "C", "V", "B", "N", "M"};
        addButtonsToPanel(whiteButtonPanel, whiteLabels, Color.WHITE);

        add(blackButtonPanel);
        add(whiteButtonPanel);
    }

    private void addButtonsToPanel(JPanel panel, String[] labels, Color color) {
        for (String label : labels) {
            JButton button = new JButton(label);
            button.setPreferredSize(new Dimension(60, 200));
            button.setBackground(color);
            button.addActionListener(e -> playSound(soundMap.get(label.charAt(0))));
            buttonMap.put(label, button);
            panel.add(button);
        }
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
        soundMap.put('A', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\A.wav");
        soundMap.put('B', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\B.wav");
        soundMap.put('C', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\C.wav");
        soundMap.put('D', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\D.wav");
        soundMap.put('E', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\E.wav");
        soundMap.put('F', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\F.wav");
        soundMap.put('G', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\G.wav");
        soundMap.put('H', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\H.wav");
        soundMap.put('I', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\I.wav");
        soundMap.put('J', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\J.wav");
        soundMap.put('K', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\K.wav");
        soundMap.put('L', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\L.wav");
        soundMap.put('M', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\M.wav");
        soundMap.put('N', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\N.wav");
        soundMap.put('O', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\O.wav");
        soundMap.put('P', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\P.wav");
        soundMap.put('Q', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\Q.wav");
        soundMap.put('R', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\R.wav");
        soundMap.put('S', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\S.wav");
        soundMap.put('T', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\T.wav");
        soundMap.put('U', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\U.wav");
        soundMap.put('V', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\V.wav");
        soundMap.put('W', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\W.wav");
        soundMap.put('X', "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\X.wav");
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (logoImage != null) {
            g.drawImage(logoImage, 300, 100, 400, 200, this);
        }
    }
}
