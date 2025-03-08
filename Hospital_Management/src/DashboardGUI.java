
import java.awt.*;
import javax.swing.*;
class DashboardGUI {

    private JFrame frame;

    public DashboardGUI() {
        frame = new JFrame("Hospital Management System - Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridLayout(3, 1));

        JButton managePatientsButton = new JButton("Manage Patients");
        JButton manageDoctorsButton = new JButton("Manage Doctors");
        JButton manageAppointmentsButton = new JButton("Manage Appointments");
        JButton exitButton = new JButton("Exit");

        managePatientsButton.addActionListener(e -> new PatientGUI());
        manageDoctorsButton.addActionListener(e -> new DoctorGUI());
        manageAppointmentsButton.addActionListener(e -> new AppointmentGUI());
        exitButton.addActionListener(e -> frame.dispose());

        frame.add(managePatientsButton);
        frame.add(manageDoctorsButton);
        frame.add(manageAppointmentsButton);
        frame.add(exitButton);

        frame.setVisible(true);
    }
}
