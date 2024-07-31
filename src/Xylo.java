import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Xylo extends JPanel {
    private Map<Character, String> soundMap;
    private Map<String, JButton> buttonMap;
    private TargetDataLine tdl;
    private boolean isRecord = false;
    private File recordStore;

    public Xylo() {
        JFrame XyloFrame = new JFrame("Xylo");
        XyloFrame.setSize(1040, 800);
        XyloFrame.setResizable(false);
        XyloFrame.setLocationRelativeTo(null);
        XyloFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setBackground(Color.BLACK);
        setLayout(null);

        initializeSoundMap();
        addTitleAndLine();
        displayXylo();
        addRecordButton();
        addButtons();

        XyloFrame.setContentPane(this);
        XyloFrame.setVisible(true);

        XyloFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char keyChar = Character.toUpperCase(e.getKeyChar());
                if (soundMap.containsKey(keyChar)) {
                    JButton button = buttonMap.get(String.valueOf(keyChar));
                    if (button != null) {
                        pressButton(button);
                        playSound(soundMap.get(keyChar));
                    }
                }
                XyloFrame.requestFocusInWindow();
            }
        });
        XyloFrame.setFocusable(true);
        XyloFrame.requestFocusInWindow();
    }

    private void addTitleAndLine() {
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
            xyloLabel.setBounds(0, 100, 1040, 700);
            add(xyloLabel);
        } catch (Exception e) {
            System.out.println("Error displaying xylophone image: " + e.getMessage());
        }
    }

    private void addRecordButton() {
        JButton recordButton = new JButton("Record");
        recordButton.setBounds(30, 5, 100, 50);
        recordButton.setBackground(Color.RED);
        recordButton.addActionListener(e -> {
            if (!isRecord) {
                startRecording();
                recordButton.setText("Stop");
            } else {
                stopRecording();
                recordButton.setText("Record");
            }
            SwingUtilities.getWindowAncestor(Xylo.this).requestFocusInWindow();
        });
        add(recordButton);
    }

    private File recordFileSave() {
        File directory = new File("D:\\JAVA\\JavaSwing\\Main\\All records");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        int count = 1;
        File file;
        do {
            file = new File(directory, "Xylophone records" + count + ".wav");
            count++;
        } while (file.exists());

        return file;
    }

    private void startRecording() {
        try {
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Error: Line not supported");
                return;
            }
            tdl = (TargetDataLine) AudioSystem.getLine(info);
            tdl.open(format);
            tdl.start();
            System.out.println("Start Recording...");
            isRecord = true;
            recordStore = recordFileSave();
            Thread recordingThread = new Thread(() -> {
                try (AudioInputStream audioStream = new AudioInputStream(tdl)) {
                    AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, recordStore);
                    System.out.println("Recording saved to: " + recordStore.getAbsolutePath());
                } catch (IOException e) {
                    System.out.println("Error during recording: " + e.getMessage());
                }
            });
            recordingThread.start();
        } catch (LineUnavailableException e) {
            System.out.println("Error: Line unavailable - " + e.getMessage());
        }
    }

    private void stopRecording() {
        isRecord = false;
        if (tdl != null) {
            tdl.stop();
            tdl.close();
        }
        System.out.println("Stopped Recording");
        int option =JOptionPane.showConfirmDialog(null,"Do You Want To Save?", "Save Recording", JOptionPane.YES_NO_OPTION);
        if (option == 1) {
            if (recordStore != null && recordStore.exists()) {
                recordStore.delete();
                System.out.println("Recording discarded");
            }
        }
    }

    private void addButtons() {
        buttonMap = new HashMap<>();
        String[] buttons = {"A", "S", "D", "F", "H", "J", "K", "L"};
        int[][] positions = {
                {320, 250}, {370, 250}, {420, 250}, {470, 250}, {520, 250}, {570, 250}, {620, 250}, {670, 250}
        };

        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton(buttons[i]);
            button.setBounds(positions[i][0], positions[i][1], 45, 40);
            button.setFont(new Font("Serif", Font.PLAIN, 15));
            button.setBackground(Color.WHITE);
            button.setFocusable(false);
            add(button);
            buttonMap.put(buttons[i], button);
        }

        revalidate();
        repaint();
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

    private void pressButton(JButton button) {
        Color originalColor = button.getBackground();
        button.setBackground(Color.LIGHT_GRAY);
        Object currentTimer = button.getClientProperty("pressTimer");
        if (currentTimer instanceof Timer) {
            ((Timer) currentTimer).stop();
        }
        Timer timer = new Timer(100, e -> button.setBackground(originalColor));
        timer.setRepeats(false);
        timer.start();
        button.putClientProperty("pressTimer", timer);
    }

    private void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            if (!soundFile.exists()) {
                System.out.println("File does not exist: " + filePath);
                return;
            }
            try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile)) {
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
                clip.drain();
            }
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Unsupported audio file format: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println("Audio line unavailable: " + e.getMessage());
        }
    }
}
