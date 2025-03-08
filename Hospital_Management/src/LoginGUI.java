
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginGUI {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI() {
        frame = new JFrame("Hospital Management System - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });

        frame.add(userLabel);
        frame.add(usernameField);
        frame.add(passLabel);
        frame.add(passwordField);
        frame.add(new JLabel());
        frame.add(loginButton);

        frame.setVisible(true);
    }

    private void authenticateUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "SulbhaRode@123");
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(frame, "Login Successful");
                frame.dispose();
                new DashboardGUI();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Credentials");
            }

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database Connection Error");
        }
    }
    public static void main(String[] args) {
        new LoginGUI();
    }
}