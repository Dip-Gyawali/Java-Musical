import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Guitar extends JPanel {
    private Map<Character, String> soundMap;
    private Map<String, JButton> buttonMap;
    private TargetDataLine tdl;
    private boolean isRecord = false;
    private File recordStore;

    public Guitar() {
        JFrame guitarFrame = new JFrame("Guitar");
        guitarFrame.setSize(1040, 800);
        guitarFrame.setResizable(false);
        guitarFrame.setLocationRelativeTo(null);
        guitarFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addButtons();
        initializeSoundMap();
        setTitleAndLine();
        displayGuitar();
        recordBtn();
        setBackground(Color.BLACK);
        setLayout(null);

        guitarFrame.setContentPane(this);
        guitarFrame.setVisible(true);
        guitarFrame.addKeyListener(new KeyAdapter() {
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
                guitarFrame.requestFocusInWindow();
            }
        });
        guitarFrame.setFocusable(true);
        guitarFrame.requestFocusInWindow();
    }

    private void setTitleAndLine() {
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

    private void recordBtn() {
        JButton recordButton = new JButton("Record");
        recordButton.setBounds(30, 5, 100, 50);
        recordButton.setBackground(Color.RED);
        recordButton.addActionListener(e -> {
            if (!isRecord) {
                recordStore = recordFileSave();
                startRecording();
                recordButton.setText("Stop");
            } else {
                stopRecording();
                recordButton.setText("Record");
            }
            SwingUtilities.getWindowAncestor(Guitar.this).requestFocusInWindow();
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
            file = new File(directory, "guitar records" + count + ".wav");
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
        tdl.stop();
        tdl.close();
        System.out.println("Stopped Recording");
    }

    private void addButtons() {
        buttonMap = new HashMap<>();
        String[] buttons = {"A", "S", "D", "F", "G", "H", "Q", "W", "E", "R", "T", "Y", "Z", "X", "C", "V", "B", "N"};
        int[][] positions = {
                {110, 110}, {110, 240}, {110, 360}, {110, 470}, {110, 600}, {110, 720},
                {460, 110}, {460, 240}, {460, 360}, {460, 470}, {460, 600}, {460, 720},
                {850, 110}, {850, 240}, {850, 360}, {850, 470}, {850, 600}, {850, 720}
        };

        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton(buttons[i]);
            button.setBounds(positions[i][0], positions[i][1], 50, 50);
            button.setBackground(Color.WHITE);
            button.setFocusable(false);
            buttonMap.put(buttons[i], button);
            add(button);
        }
        revalidate();
        repaint();
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
