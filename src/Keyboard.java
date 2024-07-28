import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Keyboard extends JPanel {
    private Image keyboardImage;
    private Map<Integer, String> soundMap;

    public Keyboard() {
        JFrame keyboardFrame = new JFrame("Keyboard");
        keyboardFrame.setSize(1040, 800);
        keyboardFrame.setResizable(false);
        keyboardFrame.setLocationRelativeTo(null);
        keyboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(Color.BLACK);
        setLayout(null);

        initializeSoundMap();
        addTitleandLine();
        displayKeyboard();

        keyboardFrame.setContentPane(this);
        keyboardFrame.setVisible(true);

        // Add key listener to the panel
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode(); // Get key code
                if (soundMap.containsKey(keyCode)) {
                    playSound(soundMap.get(keyCode));
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow(); // Ensure the panel has focus
    }

    private void addTitleandLine() {
        JLabel titleLabel = new JLabel("Keyboard Section");
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

    private void displayKeyboard() {
        try {
            keyboardImage = ImageIO.read(new File("D:\\JAVA\\JavaSwing\\Main\\src\\Assets\\keyboardModel.jpeg"));
        } catch (IOException e) {
            System.out.println("Error displaying keyboard image: " + e.getMessage());
        }
    }

    private void initializeSoundMap() {
        soundMap = new HashMap<>();
        String defaultSoundPath = "D:\\JAVA\\JavaSwing\\Main\\src\\keyboardSound\\all.wav";
        String spaceSoundPath = "D:\\JAVA\\JavaSwing\\Main\\src\\keyboardSound\\space.wav";
        String enterShiftSoundPath = "D:\\JAVA\\JavaSwing\\Main\\src\\keyboardSound\\enter shift.wav";
        String backspaceSoundPath = "D:\\JAVA\\JavaSwing\\Main\\src\\keyboardSound\\Backspace.wav";

        // Mapping each key to the same sound file
        for (char c = 'A'; c <= 'Z'; c++) {
            soundMap.put((int) c, defaultSoundPath);
        }
        for (char c = '0'; c <= '9'; c++) {
            soundMap.put((int) c, defaultSoundPath);
        }

        // Special keys
        soundMap.put(KeyEvent.VK_ESCAPE, defaultSoundPath);
        soundMap.put(KeyEvent.VK_TAB, defaultSoundPath);
        soundMap.put(KeyEvent.VK_CAPS_LOCK, defaultSoundPath);
        soundMap.put(KeyEvent.VK_CONTROL, defaultSoundPath);
        soundMap.put(KeyEvent.VK_WINDOWS, defaultSoundPath);
        soundMap.put(KeyEvent.VK_ALT, defaultSoundPath);
        soundMap.put(KeyEvent.VK_DELETE, defaultSoundPath);
        soundMap.put(KeyEvent.VK_END, defaultSoundPath);
        soundMap.put(KeyEvent.VK_PAGE_DOWN, defaultSoundPath);
        soundMap.put(KeyEvent.VK_PAGE_UP, defaultSoundPath);
        soundMap.put(KeyEvent.VK_INSERT, defaultSoundPath);
        soundMap.put(KeyEvent.VK_UP, defaultSoundPath);
        soundMap.put(KeyEvent.VK_DOWN, defaultSoundPath);
        soundMap.put(KeyEvent.VK_LEFT, defaultSoundPath);
        soundMap.put(KeyEvent.VK_RIGHT, defaultSoundPath);

        // Specific sounds for certain keys
        soundMap.put(KeyEvent.VK_SPACE, spaceSoundPath);
        soundMap.put(KeyEvent.VK_ENTER, enterShiftSoundPath);
        soundMap.put(KeyEvent.VK_SHIFT, enterShiftSoundPath);
        soundMap.put(KeyEvent.VK_BACK_SPACE, backspaceSoundPath);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (keyboardImage != null) {
            g.drawImage(keyboardImage, 50, 300, 900, 400, this);
        }
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

    public static void main(String[] args) {
        new Keyboard();
    }
}
