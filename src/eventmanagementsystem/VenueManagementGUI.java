/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class VenueManagementGUI extends JFrame {
    private JTextField nameField, addressField, capacityField;
    private JCheckBox availabilityCheckBox;
    private JButton addButton, updateButton, deleteButton, viewButton;
    private JTextArea venueList;

    public VenueManagementGUI() {
        setTitle("Venue Management");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Venue Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        inputPanel.add(addressField);

        inputPanel.add(new JLabel("Capacity:"));
        capacityField = new JTextField();
        inputPanel.add(capacityField);

        inputPanel.add(new JLabel("Available:"));
        availabilityCheckBox = new JCheckBox();
        inputPanel.add(availabilityCheckBox);

        add(inputPanel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        viewButton = new JButton("View Venues");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Venue List
        venueList = new JTextArea();
        venueList.setEditable(false);
        add(new JScrollPane(venueList), BorderLayout.SOUTH);

        // Event Handlers
        addButton.addActionListener(e -> addVenue());
        updateButton.addActionListener(e -> updateVenue());
        deleteButton.addActionListener(e -> deleteVenue());
        viewButton.addActionListener(e -> viewVenues());
    }

    private void addVenue() {
        String name = nameField.getText();
        String address = addressField.getText();
        int capacity;

        try {
            capacity = Integer.parseInt(capacityField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Capacity must be a number!");
            return;
        }

        boolean available = availabilityCheckBox.isSelected();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Venues (name, address, capacity, availability) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setInt(3, capacity);
            stmt.setBoolean(4, available);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Venue added successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateVenue() {
        // Implement update functionality
    }

    private void deleteVenue() {
        // Implement delete functionality
    }

    private void viewVenues() {
        venueList.setText("");
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Venues";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String venue = "ID: " + rs.getInt("id") +
                               ", Name: " + rs.getString("name") +
                               ", Address: " + rs.getString("address") +
                               ", Capacity: " + rs.getInt("capacity") +
                               ", Available: " + rs.getBoolean("availability") + "\n";
                venueList.append(venue);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VenueManagementGUI().setVisible(true));
    }
}

