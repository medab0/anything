package com.example.csit228_f1_v2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryController {

    @FXML
    private TextField productIdField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private Label messageLabel;

    private ObservableList<Product> productList;

    public void initialize() {
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        productList = FXCollections.observableArrayList();
        tableView.setItems(productList);

        loadInventory();
    }

    @FXML
    private void saveButtonClicked() {
        // Add new product to the database and update the table
        String name = nameField.getText();
        double price = Double.parseDouble(priceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());
        saveProduct(name, price, quantity);
        loadInventory();
    }

    @FXML
    private void updateButtonClicked() {
        // Update selected product in the database and update the table
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            int productId = selectedProduct.getProductId();
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            updateProduct(productId, name, price, quantity);
            loadInventory();
        } else {
            messageLabel.setText("Please select a product to update.");
        }
    }

    @FXML
    private void deleteButtonClicked() {
        // Delete selected product from the database and update the table
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            int productId = selectedProduct.getProductId();
            deleteProduct(productId);
            loadInventory();
        } else {
            messageLabel.setText("Please select a product to delete.");
        }
    }

    private void saveProduct(String name, double price, int quantity) {
        // Save product to the database
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO tblproducts (name, price, quantity) VALUES (?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
            messageLabel.setText("Product saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Error saving product.");
        }
    }

    private void updateProduct(int productId, String name, double price, int quantity) {
        // Update product in the database
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE tblproducts SET name = ?, price = ?, quantity = ? WHERE product_id = ?")) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, quantity);
            stmt.setInt(4, productId);
            stmt.executeUpdate();
            messageLabel.setText("Product updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Error updating product.");
        }
    }

    private void deleteProduct(int productId) {
        // Delete product from the database
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM tblproducts WHERE product_id = ?")) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
            messageLabel.setText("Product deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Error deleting product.");
        }
    }

    private void loadInventory() {
        // Load inventory from the database and populate the table
        productList.clear();
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tblproducts");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                productList.add(new Product(productId, name, price, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Error loading inventory.");
        }
    }
}
