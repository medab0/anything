package com.example.csit228_f1_v2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            createProductsTable();
            createInventoryTransactionsTable();
            createUsersTable();
            Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void createProductsTable() {
        try (Connection c = MySQLConnection.getConnection();
             Statement statement = c.createStatement()) {

            String query = "CREATE TABLE IF NOT EXISTS tblproducts(" +
                    "product_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "price DECIMAL(10, 2) NOT NULL," +
                    "quantity INT NOT NULL" +
                    ")";
            statement.execute(query);
            System.out.println("Products table created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createInventoryTransactionsTable() {
        try (Connection c = MySQLConnection.getConnection();
             Statement statement = c.createStatement()) {

            String query = "CREATE TABLE IF NOT EXISTS tblTransactions(" +
                    "transaction_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "product_id INT," +
                    "type ENUM('PURCHASE', 'SALE') NOT NULL," +
                    "quantity INT NOT NULL," +
                    "transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (product_id) REFERENCES tblProducts(product_id)" +
                    ")";
            statement.execute(query);
            System.out.println("Inventory transactions table created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createUsersTable() {
        try (Connection c = MySQLConnection.getConnection();
             Statement statement = c.createStatement()) {

            String query = "CREATE TABLE IF NOT EXISTS tblUsers(" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL," +
                    "password VARCHAR(100) NOT NULL," +
                    "constraint unique_username UNIQUE(username)" +
                    ")";
            statement.execute(query);
            System.out.println("Users table created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
