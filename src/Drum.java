import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Drum extends JPanel {
    JFrame drumFrame;

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

        drumFrame.add(this);
        drumFrame.setVisible(true);

        drumFrame.setFocusable(true);
        drumFrame.requestFocusInWindow();

        // Add key listener to frame
        drumFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char key = e.getKeyChar();
                switch (key) {
                    case 'V':
                    case 'v':
                        playSound("V");
                        break;
                    case 'A':
                    case 'a':
                        playSound("A");
                        break;
                    case 'S':
                    case 's':
                        playSound("S");
                        break;
                    case 'Q':
                    case 'q':
                        playSound("Q");
                        break;
                    case 'W':
                    case 'w':
                        playSound("W");
                        break;
                    case 'J':
                    case 'j':
                        playSound("J");
                        break;
                    case 'K':
                    case 'k':
                        playSound("K");
                        break;
                    case 'L':
                    case 'l':
                        playSound("L");
                        break;
                    default:
                        break;
                }
            }
        });
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

    private void addButtons() {
        // Blue button
        CircularButton blueButton = new CircularButton("V", Color.BLUE);
        blueButton.setBounds(370, 450, 300, 300);
        add(blueButton);

        // Red buttons
        CircularButton redButton1 = new CircularButton("A", Color.RED);
        redButton1.setBounds(370, 320, 140, 140);
        add(redButton1);
        CircularButton redButton2 = new CircularButton("S", Color.RED);
        redButton2.setBounds(530, 320, 140, 140);
        add(redButton2);

        // Green buttons
        CircularButton greenButton1 = new CircularButton("Q", Color.GREEN);
        greenButton1.setBounds(230, 420, 160, 160);
        add(greenButton1);
        CircularButton greenButton2 = new CircularButton("W", Color.GREEN);
        greenButton2.setBounds(640, 440, 160, 100);
        add(greenButton2);

        // Yellow buttons (elliptical shapes)
        EllipticalButton yellowButton1 = new EllipticalButton("J", Color.YELLOW);
        yellowButton1.setBounds(210, 350, 160, 60);
        add(yellowButton1);
        EllipticalButton yellowButton2 = new EllipticalButton("K", Color.YELLOW);
        yellowButton2.setBounds(670, 340, 200, 90);
        add(yellowButton2);
        EllipticalButton yellowButton3 = new EllipticalButton("L", Color.YELLOW);
        yellowButton3.setBounds(100, 400, 80, 40);
        add(yellowButton3);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        // to connect tom1 and tom2
        g.drawLine(400, 385, 600, 385);
        g.drawLine(400, 387, 600, 387);
        // to connect tom1 and tom2 with bass
        g.drawLine(520, 387, 520, 700);
        g.drawLine(522, 387, 522, 700);
        g.drawLine(518, 387, 518, 700);
        // connect hihat to ground
        g.drawLine(140, 410, 140, 800);
        // connect snare to ground
        g.drawLine(310, 450, 310, 800);
        // floor tom to floor connect
        g.drawLine(720, 450, 720, 800);
        // ride cymbal to floor
        g.drawLine(750, 350, 840, 500);
        g.drawLine(840, 500, 840, 800);
        // crash cymbal to ground
        g.drawLine(320, 390, 200, 450);
        g.drawLine(200, 450, 200, 800);
    }

    private void playSound(String soundName) {
        try {
            String soundPath = "drumSound/" + soundName + ".wav";
            InputStream soundStream = getClass().getClassLoader().getResourceAsStream(soundPath);
            if (soundStream == null) {
                throw new FileNotFoundException("File not found: " + soundPath);
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class CircularButton extends JButton {
        private Color color;

        CircularButton(String text, Color color) {
            super(text);
            this.color = color;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            addActionListener(e -> playSound(getText()));
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {
                g.setColor(color.darker());
            } else {
                g.setColor(color);
            }
            g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
            super.paintComponent(g);
        }

        @Override
        public void paintBorder(Graphics g) {
            g.setColor(color.darker());
            g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
        }

        @Override
        public boolean contains(int x, int y) {
            int radius = getWidth() / 2;
            return (x - radius) * (x - radius) + (y - radius) * (y - radius) <= radius * radius;
        }
    }

    class EllipticalButton extends JButton {
        private Color color;

        EllipticalButton(String text, Color color) {
            super(text);
            this.color = color;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            addActionListener(e -> playSound(getText()));
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {
                g.setColor(color.darker());
            } else {
                g.setColor(color);
            }
            g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
            super.paintComponent(g);
        }

        @Override
        public void paintBorder(Graphics g) {
            g.setColor(color.darker());
            g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
        }
    }
}
