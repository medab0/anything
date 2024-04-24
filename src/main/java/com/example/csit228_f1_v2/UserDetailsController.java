package com.example.csit228_f1_v2;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDetailsController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public void initialize() {
        // Populate fields with current user details
        String currentUser = HelloController.getCurrentUser();
        usernameField.setText(currentUser);
        // Retrieve password from database and display it if needed
    }

    @FXML
    private void updateDetails() {
        // Update user details in the database
        String newUsername = usernameField.getText();
        String newPassword = passwordField.getText();

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE tblUsers SET password = ? WHERE username = ?")) {
            stmt.setString(1, newPassword);
            stmt.setString(2, newUsername);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User details updated successfully.");
            } else {
                System.out.println("Failed to update user details.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
