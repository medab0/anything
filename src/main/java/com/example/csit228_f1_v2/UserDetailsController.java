package com.example.csit228_f1_v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class UserDetailsController {

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField tfPassword;
    @FXML
    private Button btnBack;

    @FXML
    private void updateDetails(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Success", "User details updated successfully!");
    }

    @FXML
    private void goToDashboard(ActionEvent event) throws IOException {
        System.out.println("Redirecting to dashboard...");
        Parent dashboard = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        if (dashboard == null) {
            System.err.println("Failed to load dashboard.fxml. Resource may not be found or null.");
        } else {
            Scene scene = btnBack.getScene();
            scene.setRoot(dashboard);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
