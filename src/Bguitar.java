import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Bguitar extends JPanel {
    private Map<Character, String> soundMap;
    private Map<String, JButton> buttonMap;
    private TargetDataLine tdl;
    private boolean isRecord = false;
    private File recordStore;

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
        addRecordButton();
        bguitarFrame.add(this);
        bguitarFrame.setVisible(true);

        bguitarFrame.addKeyListener(new KeyAdapter() {
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
                bguitarFrame.requestFocusInWindow();
            }
        });
        bguitarFrame.setFocusable(true);
        bguitarFrame.requestFocusInWindow();
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
            SwingUtilities.getWindowAncestor(Bguitar.this).requestFocusInWindow();
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
            file = new File(directory, "Bass Guitar Records" + count + ".wav");
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
        String[] buttons = {"A", "S", "D", "F", "Q", "W", "E", "R", "Z", "X", "C", "V", "G", "H", "J", "K", "T", "Y", "U", "I", "B", "N", "M", "O", "1", "2", "3", "4", "5", "6", "7", "8"};
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
        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton(buttons[i]);
            button.setBounds(positions[i][0], positions[i][1], 50, 45);
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
