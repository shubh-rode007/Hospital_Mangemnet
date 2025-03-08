
import java.awt.*;
import java.sql.*;
import javax.swing.*;
class PatientGUI {

    private JFrame frame;
    private JTextField nameField, ageField, genderField;
    private JButton addButton, viewButton;

    public PatientGUI() {
        frame = new JFrame("Manage Patients");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 2));

        frame.add(new JLabel("Patient Name:"));
        nameField = new JTextField();
        frame.add(nameField);

        frame.add(new JLabel("Age:"));
        ageField = new JTextField();
        frame.add(ageField);

        frame.add(new JLabel("Gender:"));
        genderField = new JTextField();
        frame.add(genderField);

        addButton = new JButton("Add Patient");
        addButton.addActionListener(e -> addPatient());
        frame.add(addButton);

        viewButton = new JButton("View Patients");
        viewButton.addActionListener(e -> viewPatients());
        frame.add(viewButton);

        frame.setVisible(true);
    }

    private void addPatient() {
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String gender = genderField.getText();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "SulbhaRode@123");
            String query = "INSERT INTO patients (name, age, gender) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, gender);

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "Patient added successfully!");
            }

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database Error");
        }
    }

    private void viewPatients() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "SulbhaRode@123");
            String query = "SELECT * FROM patients";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            JTextArea textArea = new JTextArea();
            while (rs.next()) {
                textArea.append("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name")
                        + ", Age: " + rs.getInt("age") + ", Gender: " + rs.getString("gender") + "\n");
            }

            JOptionPane.showMessageDialog(frame, new JScrollPane(textArea), "Patient List", JOptionPane.INFORMATION_MESSAGE);
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database Error");
        }
    }
}
