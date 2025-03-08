
import java.awt.*;
import java.sql.*;
import javax.swing.*;

class DoctorGUI {

    private JFrame frame;
    private JTextField nameField, specialtyField;
    private JButton addButton, viewButton;

    public DoctorGUI() {
        frame = new JFrame("Manage Doctors");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3, 2));

        frame.add(new JLabel("Doctor Name:"));
        nameField = new JTextField();
        frame.add(nameField);

        frame.add(new JLabel("Specialty:"));
        specialtyField = new JTextField();
        frame.add(specialtyField);

        addButton = new JButton("Add Doctor");
        addButton.addActionListener(e -> addDoctor());
        frame.add(addButton);

        viewButton = new JButton("View Doctors");
        viewButton.addActionListener(e -> viewDoctors());
        frame.add(viewButton);

        frame.setVisible(true);
    }

    private void addDoctor() {
        String name = nameField.getText();
        String specialty = specialtyField.getText();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "SulbhaRode@123");
            String query = "INSERT INTO doctors (name, specialization) VALUES (?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, specialty);

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "Doctor added successfully!");
            }

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database Error");
        }
    }

    private void viewDoctors() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "SulbhaRode@123");
            String query = "SELECT * FROM doctors";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            JTextArea textArea = new JTextArea();
            while (rs.next()) {
                textArea.append("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name")
                        + ", Specialty: " + rs.getString("specialization") + "\n");
            }

            JOptionPane.showMessageDialog(frame, new JScrollPane(textArea), "Doctor List", JOptionPane.INFORMATION_MESSAGE);
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database Error");
        }
    }
}
