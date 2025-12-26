/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagementsystem;

import javax.swing.*;
import java.awt.*;

public class MainDashboard extends JFrame {
    public MainDashboard() {
        setTitle("Event Management System");
        setLayout(new GridLayout(3, 1, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window

        // Buttons for navigation
        JButton eventPlanningButton = new JButton("Event Planning");
        JButton attendeeRegistrationButton = new JButton("Attendee Registration");
        JButton venueManagementButton = new JButton("Venue Management");

        // Event Handlers
        eventPlanningButton.addActionListener(e -> openEventPlanning());
        attendeeRegistrationButton.addActionListener(e -> openAttendeeRegistration());
        venueManagementButton.addActionListener(e -> openVenueManagement());

        // Add buttons to the frame
        add(eventPlanningButton);
        add(attendeeRegistrationButton);
        add(venueManagementButton);
    }

    private void openEventPlanning() {
        SwingUtilities.invokeLater(() -> new EventPlanningGUI().setVisible(true));
    }

    private void openAttendeeRegistration() {
        SwingUtilities.invokeLater(() -> new AttendeeRegistrationGUI().setVisible(true));
    }

    private void openVenueManagement() {
        SwingUtilities.invokeLater(() -> new VenueManagementGUI().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainDashboard().setVisible(true));
    }
}