/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AttendeeRegistrationGUI extends JFrame {
    private JTextField nameField, emailField, contactField, eventIdField;
    private JButton registerButton, updateButton, deleteButton, viewButton;
    private JTextArea attendeeList;

    public AttendeeRegistrationGUI() {
        setTitle("Attendee Registration");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Contact Number:"));
        contactField = new JTextField();
        inputPanel.add(contactField);

        inputPanel.add(new JLabel("Event ID:"));
        eventIdField = new JTextField();
        inputPanel.add(eventIdField);

        add(inputPanel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        registerButton = new JButton("Register");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        viewButton = new JButton("View Attendees");
        buttonPanel.add(registerButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Attendee List
        attendeeList = new JTextArea();
        attendeeList.setEditable(false);
        add(new JScrollPane(attendeeList), BorderLayout.SOUTH);

        // Event Handlers
        registerButton.addActionListener(e -> registerAttendee());
        updateButton.addActionListener(e -> updateAttendee());
        deleteButton.addActionListener(e -> deleteAttendee());
        viewButton.addActionListener(e -> viewAttendees());
    }

    private void registerAttendee() {
        String name = nameField.getText();
        String email = emailField.getText();
        String contact = contactField.getText();
        int eventId;

        try {
            eventId = Integer.parseInt(eventIdField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Event ID must be a number!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Attendees (name, email, contact_number, event_id) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, contact);
            stmt.setInt(4, eventId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Attendee registered successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateAttendee() {
        // Implement update functionality
    }

    private void deleteAttendee() {
        // Implement delete functionality
    }

    private void viewAttendees() {
        attendeeList.setText("");
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Attendees";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String attendee = "ID: " + rs.getInt("id") +
                                  ", Name: " + rs.getString("name") +
                                  ", Email: " + rs.getString("email") +
                                  ", Contact: " + rs.getString("contact_number") +
                                  ", Event ID: " + rs.getInt("event_id") + "\n";
                attendeeList.append(attendee);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AttendeeRegistrationGUI().setVisible(true));
    }
}