/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EventPlanningGUI extends JFrame {
    // Input Fields
    private JTextField nameField, dateField, timeField, descriptionField, organiserField;
    private JButton createButton, updateButton, deleteButton, viewButton, clearButton;
    private JTable eventTable;
    private DefaultTableModel tableModel;

    public EventPlanningGUI() {
        setTitle("Event Planning");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        inputPanel.add(new JLabel("Event Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Time (HH:MM):"));
        timeField = new JTextField();
        inputPanel.add(timeField);

        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Organiser Details:"));
        organiserField = new JTextField();
        inputPanel.add(organiserField);

        clearButton = new JButton("Clear Fields");
        inputPanel.add(clearButton);
        inputPanel.add(new JLabel()); // Spacer

        add(inputPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        createButton = new JButton("Create");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        viewButton = new JButton("View Events");
        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Table Panel
        String[] columnNames = {"ID", "Name", "Date", "Time", "Description", "Organiser"};
        tableModel = new DefaultTableModel(columnNames, 0);
        eventTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(eventTable);
        add(tableScrollPane, BorderLayout.SOUTH);

        // Event Listeners
        createButton.addActionListener(e -> createEvent());
        updateButton.addActionListener(e -> updateEvent());
        deleteButton.addActionListener(e -> deleteEvent());
        viewButton.addActionListener(e -> viewEvents());
        clearButton.addActionListener(e -> clearFields());

        // Load Events on Startup
        viewEvents();
    }

    private void createEvent() {
        String name = nameField.getText();
        String date = dateField.getText();
        String time = timeField.getText();
        String description = descriptionField.getText();
        String organiser = organiserField.getText();

        if (name.isEmpty() || date.isEmpty() || time.isEmpty() || description.isEmpty() || organiser.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Events (name, date, time, description, organiser_details) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, date);
            stmt.setString(3, time);
            stmt.setString(4, description);
            stmt.setString(5, organiser);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Event created successfully!");
            clearFields();
            viewEvents();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateEvent() {
        int selectedRow = eventTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an event to update.");
            return;
        }

        String id = tableModel.getValueAt(selectedRow, 0).toString();
        String name = nameField.getText();
        String date = dateField.getText();
        String time = timeField.getText();
        String description = descriptionField.getText();
        String organiser = organiserField.getText();

        if (name.isEmpty() || date.isEmpty() || time.isEmpty() || description.isEmpty() || organiser.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE Events SET name = ?, date = ?, time = ?, description = ?, organiser_details = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, date);
            stmt.setString(3, time);
            stmt.setString(4, description);
            stmt.setString(5, organiser);
            stmt.setString(6, id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Event updated successfully!");
            clearFields();
            viewEvents();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteEvent() {
        int selectedRow = eventTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an event to delete.");
            return;
        }

        String id = tableModel.getValueAt(selectedRow, 0).toString();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM Events WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Event deleted successfully!");
            clearFields();
            viewEvents();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void viewEvents() {
        tableModel.setRowCount(0); // Clear existing rows
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Events";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("description"),
                        rs.getString("organiser_details")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        nameField.setText("");
        dateField.setText("");
        timeField.setText("");
        descriptionField.setText("");
        organiserField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EventPlanningGUI().setVisible(true));
    }
}