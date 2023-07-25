package com.example.senadzidzor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.List;

public class VendorControllerManagement {

    @FXML
    private TableColumn<Vendor, Integer> columnId;

    @FXML
    private TableColumn<Vendor, String> columnName;

    @FXML
    private TableColumn<Vendor, String> columnPhone;

    @FXML
    private TableColumn<Vendor, String> columnLocation;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private TableView<Vendor> vendorTable;

    @FXML
    private Button addVendorButton;

    @FXML
    private Button removeVendorButton;

    private Connection connection;
    private VendorController vendorController;

    public void initialize() {
        connectToDatabase();
        vendorController = new VendorController();
        setupTableView();
    }

    private void setupTableView() {
        // Set up cell value factories for each column
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

        // Set the item list for the TableView
        ObservableList<Vendor> vendorList = FXCollections.observableArrayList();
        vendorTable.setItems(vendorList);
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/inventory";
            String username = "root";
            String password = "password";
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddVendor() {
        int id = Integer.parseInt(idTextField.getText());
        String name = nameTextField.getText();
        String phone = phoneTextField.getText();
        String location = locationTextField.getText();

        vendorController.addVendor(id, name, phone, location);

        // Clear the input fields
        idTextField.clear();
        nameTextField.clear();
        phoneTextField.clear();
        locationTextField.clear();

        // Update the table view
        refreshTableView();
    }

    private void refreshTableView() {
        List<Vendor> vendors = vendorController.getAllVendors();
        vendorTable.getItems().setAll(vendors);
    }


    @FXML
    private void handleRemoveVendor() {
        Vendor selectedVendor = vendorTable.getSelectionModel().getSelectedItem();

        if (selectedVendor != null) {
            String deleteQuery = "DELETE FROM vendor WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                statement.setInt(1, selectedVendor.getId());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Vendor removed from the database");

                    vendorController.removeVendor(selectedVendor.getId());

                    updateTableView();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateTableView() {
        List<Vendor> allVendors = vendorController.getAllVendors();
        ObservableList<Vendor> vendorList = FXCollections.observableArrayList(allVendors);
        vendorTable.setItems(vendorList);
    }
}
