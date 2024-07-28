import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Piano extends JPanel {
    Image logo;
    JFrame pianoFrame;
    Map<String, Clip> soundMap = new HashMap<>();
    Map<String, String> keySoundMap = new HashMap<>();

    Piano() {
        super();
        pianoFrame = new JFrame("Piano");
        pianoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pianoFrame.setSize(1040, 800);
        pianoFrame.setResizable(false);
        pianoFrame.setLocationRelativeTo(null);

        pianoFrame.setVisible(true);

        try {
            logo = loadImage("texture-treble-clef-dark-background-isolated-generative-ai_169016-29581.jpg");
            loadSounds();
        } catch (Exception e) {
            System.out.println("Error Occurred: " + e.getMessage());
        }

        JPanel blackButtonPanel = new JPanel();
        blackButtonPanel.setLayout(new GridLayout(1, 6)); // 1 row, 6 columns
        blackButtonPanel.setBounds(0, 340, 1040, 100);

        addButtonToPanel(blackButtonPanel, "W", Color.BLACK, Color.WHITE);
        addButtonToPanel(blackButtonPanel, "E", Color.BLACK, Color.WHITE);
        addButtonToPanel(blackButtonPanel, "T", Color.BLACK, Color.WHITE);
        addButtonToPanel(blackButtonPanel, "Y", Color.BLACK, Color.WHITE);
        addButtonToPanel(blackButtonPanel, "U", Color.BLACK, Color.WHITE);
        addButtonToPanel(blackButtonPanel, "B", Color.BLACK, Color.WHITE);

        JPanel whiteButtonPanel = new JPanel();
        whiteButtonPanel.setLayout(new GridLayout(1, 12)); // 1 row, 12 columns
        whiteButtonPanel.setBounds(0, 440, 1040, 260);

        addButtonToPanel(whiteButtonPanel, "A", Color.WHITE, Color.BLACK);
        addButtonToPanel(whiteButtonPanel, "S", Color.WHITE, Color.BLACK);
        addButtonToPanel(whiteButtonPanel, "D", Color.WHITE, Color.BLACK);
        addButtonToPanel(whiteButtonPanel, "F", Color.WHITE, Color.BLACK);
        addButtonToPanel(whiteButtonPanel, "G", Color.WHITE, Color.BLACK);
        addButtonToPanel(whiteButtonPanel, "H", Color.WHITE, Color.BLACK);
        addButtonToPanel(whiteButtonPanel, "J", Color.WHITE, Color.BLACK);
        addButtonToPanel(whiteButtonPanel, "K", Color.WHITE, Color.BLACK);
        addButtonToPanel(whiteButtonPanel, "L", Color.WHITE, Color.BLACK);
        addButtonToPanel(whiteButtonPanel, "Z", Color.WHITE, Color.BLACK);
        addButtonToPanel(whiteButtonPanel, "X", Color.WHITE, Color.BLACK);
        addButtonToPanel(whiteButtonPanel, "C", Color.WHITE, Color.BLACK);

        // Add the button panels to the frame
        pianoFrame.add(blackButtonPanel);
        pianoFrame.add(whiteButtonPanel);

        // Add the main panel to the frame
        pianoFrame.add(this);

        setBackground(Color.BLACK);

        // Add key listener to the frame
        pianoFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String keyText = String.valueOf(e.getKeyChar()).toUpperCase();
                playSound(keyText);
            }
        });

        // Ensure the frame has focus to capture key events
        pianoFrame.setFocusable(true);
        pianoFrame.requestFocusInWindow();
    }

    private void addButtonToPanel(JPanel panel, String buttonText, Color background, Color foreground) {
        JButton button = new JButton(buttonText);
        button.setBackground(background);
        button.setForeground(foreground);
        button.addActionListener(e -> playSound(buttonText));
        panel.add(button);
    }

    private Image loadImage(String imageName) {
        Image image = null;
        try (InputStream is = getClass().getResourceAsStream("/Assets/" + imageName)) {
            if (is != null) {
                image = ImageIO.read(is);
                System.out.println("Loaded " + imageName);
            } else {
                System.out.println("Error: " + imageName + " not found.");
            }
        } catch (IOException e) {
            System.out.println("Error loading " + imageName + ": " + e.getMessage());
        }
        return image;
    }

    private void loadSounds() {
        String[] keys = {"A", "B", "Bb", "C", "C_s", "C1", "C_s1", "D", "D_s", "D1", "D_s1", "E", "E1", "F", "F_s", "F1", "G", "G_s"};
        String[] filenames = {"A.wav", "B.wav", "Bb.wav", "C.wav", "C_s.wav", "C1.wav", "C_s1.wav", "D.wav", "D_s.wav", "D1.wav", "D_s1.wav", "E.wav", "E1.wav", "F.wav", "F_s.wav", "F1.wav", "G.wav", "G_s.wav"};
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            String filePath = "D:\\JAVA\\JavaSwing\\Main\\src\\pianoSound\\" + filenames[i];
            try {
                File soundFile = new File(filePath);
                if (soundFile.exists()) {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    soundMap.put(key, clip);
                    keySoundMap.put(getButtonKeyForSoundKey(key), key);
                    System.out.println("Loaded sound for key: " + key);
                } else {
                    System.out.println("Error: Sound file for key " + key + " not found at " + filePath);
                }
            } catch (Exception e) {
                System.out.println("Error loading sound for key " + key + ": " + e.getMessage());
            }
        }
    }

    private String getButtonKeyForSoundKey(String soundKey) {
        switch (soundKey) {
            case "A": return "A";
            case "B": return "B";
            case "Bb": return "S";
            case "C": return "C";
            case "C_s": return "D";
            case "C1": return "E";
            case "C_s1": return "F";
            case "D": return "G";
            case "D_s": return "H";
            case "D1": return "J";
            case "D_s1": return "K";
            case "E": return "L";
            case "E1": return "Z";
            case "F": return "X";
            case "F_s": return "W";
            case "F1": return "T";
            case "G": return "Y";
            case "G_s": return "U";
            default: return null;
        }
    }

    private void playSound(String key) {
        String soundKey = keySoundMap.get(key);
        Clip clip = soundMap.get(soundKey);
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        } else {
            System.out.println("No sound mapped for key: " + key);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        Font boldFont = new Font("Serif", Font.BOLD, 24);
        g.setFont(boldFont);
        g.drawString("Piano Section", 420, 40);
        g.drawLine(0, 60, 1040, 60);

        if (logo != null) g.drawImage(logo, 270, 110, 500, 200, this);
        g.fillRect(0, 320, 1040, 500);
    }
}
