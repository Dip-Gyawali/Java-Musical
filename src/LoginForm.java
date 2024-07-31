import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm extends JFrame {
    private JTextField userText;
    private JPasswordField passwordText;

    public LoginForm() {
        setTitle("Login Form");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        initUI();
    }

    private void initUI() {
        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(60, 20, 80, 25);
        add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(140, 20, 165, 25);
        add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(60, 80, 80, 25);
        add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(140, 80, 165, 25);
        add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 120, 80, 25);
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                if (authenticate(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login Successful");
                    dispose();
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            JFrame menuFrame = new JFrame("Musical Instrument");
                            menuFrame.setResizable(false);
                            menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            Menu menu = new Menu();
                            menuFrame.add(menu);
                            menuFrame.setSize(1040, 800);
                            menuFrame.setLocationRelativeTo(null);
                            menuFrame.setVisible(true);
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Credentials");
                }
            }
        });
    }

    private boolean authenticate(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/musicaljava";
        String dbUsername = "root";
        String dbPassword = "hiten";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT * FROM detail_user WHERE user = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
