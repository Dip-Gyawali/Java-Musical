import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Piano extends JPanel {
    Image logoImage;
    Map<Character, String> soundMap;
    Map<String, JButton> buttonMap;
    TargetDataLine tdl; // Java sound API to record the audio and save in a file
    boolean isRecord = false;
    File recordStore;

    Piano() {
        JFrame pianoFrame = new JFrame("Piano");
        pianoFrame.setSize(1040, 800);
        pianoFrame.setResizable(false);
        pianoFrame.setLocationRelativeTo(null);
        pianoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(Color.BLACK);
        setLayout(null);

        initializeSoundMap();
        addTitleandLine();
        addLogo();
        recordBtn();
        addButtons();
        pianoFrame.setContentPane(this);
        pianoFrame.setVisible(true);

        pianoFrame.addKeyListener(new KeyAdapter() {
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
                pianoFrame.requestFocusInWindow();
            }
        });
        pianoFrame.setFocusable(true);
        pianoFrame.requestFocusInWindow();
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

    private void recordBtn() {
        JButton recordButton = new JButton("Record");
        recordButton.setBounds(30, 5, 100, 50);
        recordButton.setBackground(Color.RED);
        recordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRecord) {
                    startRecording();
                    recordButton.setText("Stop");
                } else {
                    stopRecording();
                    recordButton.setText("Record");
                }
                SwingUtilities.getWindowAncestor(Piano.this).requestFocusInWindow();
            }
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
            file = new File(directory, "piano records" + count + ".wav");
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
        tdl.stop();
        tdl.close();
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
            buttonMap.put(label, button);
            panel.add(button);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    SwingUtilities.getWindowAncestor(Piano.this).requestFocusInWindow();
                }
            });
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
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            clip.drain();
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Unsupported audio file format: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println("Audio line unavailable: " + e.getMessage());
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
