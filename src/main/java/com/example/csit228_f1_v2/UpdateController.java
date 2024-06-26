package com.example.csit228_f1_v2;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateController {
    public Button btnDashboard;
    public PasswordField fieldNewPassword;
    public TextField fieldNewUsername;
    public Button btnSumbit;

    public void handleDashboard(ActionEvent actionEvent) {
        try {
            Parent dashboardView = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            Scene scene = btnDashboard.getScene();
            scene.setRoot(dashboardView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSubmit(ActionEvent actionEvent) {
        String username = HelloController.getCurrentUser();
        String password = fieldNewPassword.getText();

        if (password.isEmpty()) {
            return;
        }

        try {
            if (updateUser(username, password)) {
                showAlert(Alert.AlertType.INFORMATION, "Hack Successful I mean Update Successful", "Password hacked/updated successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Update Error", "Username does not exist!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean updateUser(String username, String password) throws SQLException {
        Connection c = MySQLConnection.getConnection();

        String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        PreparedStatement checkStatement = c.prepareStatement(checkQuery);
        checkStatement.setString(1, username);
        var resultSet = checkStatement.executeQuery();
        resultSet.next();

        int count = resultSet.getInt(1);
        if (count == 0) {
            return false;
        }

        String updateQuery = "UPDATE users SET password = ? WHERE username = ?";
        PreparedStatement updateStatement = c.prepareStatement(updateQuery);
        updateStatement.setString(1, password);
        updateStatement.setString(2, username);

        int rows = updateStatement.executeUpdate();

        c.close();

        return rows > 0;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
