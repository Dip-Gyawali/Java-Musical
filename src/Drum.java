import javax.imageio.ImageIO;
import javax.sound.sampled.*;
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

public class Drum extends JPanel {
    private JFrame drumFrame;
    private Map<Character, String> soundMap;
    private Map<String, JButton> buttonMap;
    private TargetDataLine tdl; // Java sound API to record the audio and save in a file
    private boolean isRecord = false;
    private File recordStore;
    Image tom1;
    Image tom2;
    Image bass;
    Image crash;
    Image floorTom;
    Image hihat;
    Image ride;
    Image snare;

    Drum() {
        super();
        drumFrame = new JFrame("Drum Set");
        drumFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        drumFrame.setSize(1040, 800);
        drumFrame.setResizable(false);
        drumFrame.setLocationRelativeTo(null);

        setLayout(null);
        addTitleAndLine();
        addButtons();
        setBackground(Color.BLACK);

        initializeSoundMap();
        displayDrum();
        recordBtn();
        drumFrame.add(this);
        drumFrame.setVisible(true);

        drumFrame.addKeyListener(new KeyAdapter() {
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
                drumFrame.requestFocusInWindow(); // Ensure focus remains on the frame
            }
        });
        drumFrame.setFocusable(true);
        drumFrame.requestFocusInWindow();
    }

    private void addTitleAndLine() {
        JLabel titleLabel = new JLabel("Drum Section");
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

    public void displayDrum(){
        try {
             tom1 = ImageIO.read(new File("D:\\JAVA\\JavaSwing\\Main\\src\\drumImages\\tom1.png"));
             tom2 = ImageIO.read(new File("D:\\JAVA\\JavaSwing\\Main\\src\\drumImages\\tom2.png"));
             bass = ImageIO.read(new File("D:\\JAVA\\JavaSwing\\Main\\src\\drumImages\\bass.png"));
             crash = ImageIO.read(new File("D:\\JAVA\\JavaSwing\\Main\\src\\drumImages\\crash.png"));
             floorTom = ImageIO.read(new File("D:\\JAVA\\JavaSwing\\Main\\src\\drumImages\\floor tom.png"));
             hihat = ImageIO.read(new File("D:\\JAVA\\JavaSwing\\Main\\src\\drumImages\\hihat.png"));
             ride = ImageIO.read(new File("D:\\JAVA\\JavaSwing\\Main\\src\\drumImages\\ride.png"));
             snare = ImageIO.read(new File("D:\\JAVA\\JavaSwing\\Main\\src\\drumImages\\snare.png"));

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
                SwingUtilities.getWindowAncestor(Drum.this).requestFocusInWindow();
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
            file = new File(directory, "drum records" + count + ".wav");
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
        String[] buttons={"A","S","V","Q","W","K","J","L"};
        int[][] positions = {
                {420,370},{580,370},{500,620},{280,450},{690,450},{740,360},{270,350},{120,440}
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
        soundMap.put('V', "D:\\JAVA\\JavaSwing\\Main\\src\\drumSound\\V.wav");
        soundMap.put('A', "D:\\JAVA\\JavaSwing\\Main\\src\\drumSound\\A.wav");
        soundMap.put('S', "D:\\JAVA\\JavaSwing\\Main\\src\\drumSound\\S.wav");
        soundMap.put('Q', "D:\\JAVA\\JavaSwing\\Main\\src\\drumSound\\Q.wav");
        soundMap.put('W', "D:\\JAVA\\JavaSwing\\Main\\src\\drumSound\\W.wav");
        soundMap.put('J', "D:\\JAVA\\JavaSwing\\Main\\src\\drumSound\\J.wav");
        soundMap.put('K', "D:\\JAVA\\JavaSwing\\Main\\src\\drumSound\\K.wav");
        soundMap.put('L', "D:\\JAVA\\JavaSwing\\Main\\src\\drumSound\\L.wav");
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
        g.setColor(Color.WHITE);

        // Connect tom1 and tom2
        g.drawLine(400, 385, 600, 385);
        g.drawLine(400, 387, 600, 387);
        //add drum images
        if (tom1 != null) {
            g.drawImage(tom1, 370, 320, 140, 140, this);
        }
        if (tom2 != null) {
            g.drawImage(tom2, 530, 320, 140, 140, this);
        }
        // Connect tom1 and tom2 with bass
        g.drawLine(520, 387, 520, 700);
        g.drawLine(522, 387, 522, 700);
        g.drawLine(518, 387, 518, 700);
        if (bass != null) {
            g.drawImage(bass, 370, 450, 300, 300, this);
        }
        // Connect snare to ground
        g.drawLine(300, 450, 300, 800);
        if (snare != null) {
            g.drawImage(snare, 230, 420, 140, 140, this);
        }
        // Connect floor tom to floor
        g.drawLine(720, 450, 720, 800);
        if (floorTom != null) {
            g.drawImage(floorTom, 640, 440, 160, 100, this);
        }
        if (crash != null) {
            g.drawImage(crash,210, 350, 160, 60, this);
        }
        // Connect hi-hat to ground
        g.drawLine(140, 410, 140, 800);
        if (hihat != null) {
            g.drawImage(hihat, 90, 350, 110, 90, this);
        }
        // Ride cymbal to floor
        g.drawLine(750, 350, 840, 500);
        g.drawLine(840, 500, 840, 800);
        if (ride != null) {
            g.drawImage(ride,670, 340, 200, 90, this);
        }
        // Crash cymbal to ground
        g.drawLine(280, 390, 200, 450);
        g.drawLine(200, 450, 200, 800);
    }
}
