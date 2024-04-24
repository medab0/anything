package com.example.csit228_f1_v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {
    @FXML
    public Button btnRegister;
    public Button btnLogin;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "Nothing was inputted.");
            return;
        }

        try {
            if (registerUser(username, password)) {
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User registered!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Error", "Username already exists!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean registerUser(String username, String password) throws SQLException {
        Connection c = MySQLConnection.getConnection();

        String checkQuery = "SELECT COUNT(*) FROM tblUsers WHERE username = ?";
        PreparedStatement checkStatement = c.prepareStatement(checkQuery);
        checkStatement.setString(1, username);
        var resultSet = checkStatement.executeQuery();
        resultSet.next();

        int count = resultSet.getInt(1);
        if (count > 0) {
            return false;
        }

        String insertQuery = "INSERT INTO tblUsers (username, password) VALUES (?, ?)";
        PreparedStatement insertStatement = c.prepareStatement(insertQuery);
        insertStatement.setString(1, username);
        insertStatement.setString(2, password);

        int rows = insertStatement.executeUpdate();

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

    @FXML
    private void handleLogin(ActionEvent event) {
        try {
            Parent loginView = FXMLLoader.load(getClass().getResource("login-view.fxml"));
            Scene scene = btnRegister.getScene();
            scene.setRoot(loginView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}