package com.example.csit228_f1_v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class HelloController {

    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Button btnSignIn;
    @FXML
    private Label MessageText;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnUserDetails;
    @FXML
    private Button btnInventory;

    public static String currentUser;
/*
    @FXML
    private void initialize() {
        btnRegister.setVisible(false);
    }
*/

    @FXML
    private void userLogin(ActionEvent event) {
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        try {
            if (isValidLogin(username, password)) {
                Parent dashboard = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                if (dashboard == null) {
                    System.err.println("Failed to load dashboard.fxml. Resource may not be found or null.");
                } else {
                    Scene scene = btnSignIn.getScene();
                    scene.setRoot(dashboard);
                }
            } else {
                MessageText.setText("Incorrect username or password.");
            }
        } catch (IOException e) {
            System.err.println("Error loading dashboard.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            System.err.println("Unexpected error loading dashboard.fxml: " + ex.getMessage());
            ex.printStackTrace();
        }
    }




    private boolean isValidLogin(String username, String password) {
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(
                     "SELECT password FROM tblUsers WHERE username = ?")) {
            stmt.setString(1, username);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                return res.getString("password").equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void setCurrentUser(String username) {
        currentUser = username;
    }

    public static void clearCurrentUser() {
        currentUser = null;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    @FXML
    private void handleRegister(ActionEvent event) throws IOException {
        Parent loginView = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("register-view.fxml")));
        Scene scene = btnRegister.getScene();
        scene.setRoot(loginView);
        clearCurrentUser();
    }
    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        Parent loginView = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("login-view.fxml")));
        Scene scene = btnLogout.getScene();
        scene.setRoot(loginView);
        clearCurrentUser();
    }

    @FXML
    private void openUserDetails(ActionEvent event) throws IOException {
        Parent userDetailsView = FXMLLoader.load(getClass().getResource("user-details.fxml"));
        Scene scene = btnUserDetails.getScene();
        scene.setRoot(userDetailsView);
    }

    @FXML
    private void openInventoryView(ActionEvent event) {
        try {
            Parent inventoryView = FXMLLoader.load(getClass().getResource("inventory-view.fxml"));
            if (inventoryView == null) {
                System.err.println("Failed to load inventory-view.fxml. Resource may not be found or null.");
            } else {
                Scene scene = new Scene(inventoryView, 600, 800);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            System.err.println("Error loading inventory-view.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            System.err.println("Unexpected error loading inventory-view.fxml: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


}
