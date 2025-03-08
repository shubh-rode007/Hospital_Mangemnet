
import java.awt.*;
import java.sql.*;
import javax.swing.*;

class AppointmentGUI {

    private JFrame frame;
    private JTextField patientIdField, doctorIdField, dateField, timeField;
    private JButton bookButton;

    public AppointmentGUI() {
        frame = new JFrame("Manage Appointments");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2));

        frame.add(new JLabel("Patient ID:"));
        patientIdField = new JTextField();
        frame.add(patientIdField);

        frame.add(new JLabel("Doctor ID:"));
        doctorIdField = new JTextField();
        frame.add(doctorIdField);

        frame.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        frame.add(dateField);

        frame.add(new JLabel("Time (HH:MM):"));
        timeField = new JTextField();
        frame.add(timeField);

        bookButton = new JButton("Book Appointment");
        bookButton.addActionListener(e -> bookAppointment());
        frame.add(new JLabel());
        frame.add(bookButton);

        frame.setVisible(true);
    }

    private void bookAppointment() {
        int patientId = Integer.parseInt(patientIdField.getText());
        int doctorId = Integer.parseInt(doctorIdField.getText());
        String date = dateField.getText();
        String time = timeField.getText();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "SulbhaRode@123");
            String query = "INSERT INTO appointments (patient_id, doctor_id, date, time) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, patientId);
            pst.setInt(2, doctorId);
            pst.setString(3, date);
            pst.setString(4, time);

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "Appointment booked successfully!");
            }

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database Error");
        }
    }
}
