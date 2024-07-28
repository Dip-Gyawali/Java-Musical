import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;


public class Menu extends JPanel {
    Image pianoImage;
    Image drumImage;
    Image guitarImage;
    Image violenImage;
    Image fluteImage;
    Image keyboardImage;

    Menu() {
        super();
        setLayout(null);
        setBackground(Color.BLACK);

        try {
            pianoImage = loadImage("image-asset.jpeg");
            drumImage = loadImage("drum.jpg");
            guitarImage = loadImage("guitar.jpg");
            violenImage = loadImage("Xylophone.jpg");
            fluteImage = loadImage("flute.jpg");
            keyboardImage = loadImage("gaming-keyboards-200-01.jpg");
        } catch (Exception e) {
            System.out.println("Error Occurred: " + e.getMessage());
        }

        addButtons();
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

    private void addButtons() {
        JButton pianoButton = createButton("Piano", 20, 330, 300, 30);
        pianoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Piano();

            }
        });

        JButton drumButton = createButton("Drum", 360, 330, 300, 30);
        drumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Drum();
            }
        });
        JButton guitarButton = createButton("Guitar", 700, 330, 300, 30);
        guitarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Guitar();
            }
        });
        JButton xyloButton = createButton("Xylophone", 20, 660, 300, 30);
        xyloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Xylo();
            }
        });
        JButton fluteButton = createButton("Flute", 360, 660, 300, 30);
        fluteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Flute();
            }
        });
        JButton keyboardButton = createButton("Keyboard", 700, 660, 300, 30);
        keyboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Keyboard();
            }
        });

        add(pianoButton);
        add(drumButton);
        add(guitarButton);
        add(xyloButton);
        add(fluteButton);
        add(keyboardButton);
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("paintComponent called");

        g.setColor(Color.WHITE);
        Font boldFont = new Font("Serif", Font.BOLD, 24);
        g.setFont(boldFont);
        g.drawString("Musical Instruments", 420, 40);
        g.drawLine(0, 60, 1040, 60);

        if (pianoImage != null) g.drawImage(pianoImage, 20, 120, 300, 200, this);
        if (drumImage != null) g.drawImage(drumImage, 360, 120, 300, 200, this);
        if (guitarImage != null) g.drawImage(guitarImage, 700, 120, 300, 200, this);
        if (violenImage != null) g.drawImage(violenImage, 20, 450, 300, 200, this);
        if (fluteImage != null) g.drawImage(fluteImage, 360, 450, 300, 200, this);
        if (keyboardImage != null) g.drawImage(keyboardImage, 700, 450, 300, 200, this);
    }
}
