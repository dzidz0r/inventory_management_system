package com.example.senadzidzor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.*;
import java.time.LocalDate;

public class BillManagement {

    @FXML
    private TableColumn<Bill, Integer> columnBillID;

    @FXML
    private TableColumn<Bill, Date> columnDate;

    @FXML
    private TableColumn<Bill, Double> columnTotalAmount;

    @FXML
    private TableView<Bill> billTable;

    @FXML
    private Button createBillButton;

    @FXML
    private Button removeBillButton;

    private Connection connection;
    private Statement statement;

    public void initialize() {
        connectToDatabase();
        createBillsTable();
        setupTableView();
    }

    private void setupTableView() {
        // Set up cell value factories for each column
        columnBillID.setCellValueFactory(cellData -> cellData.getValue().billIDProperty().asObject());
        columnDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        columnTotalAmount.setCellValueFactory(cellData -> cellData.getValue().totalAmountProperty().asObject());

        // Set the item list for the TableView
        ObservableList<Bill> billsList = getBillsFromDatabase();
        billTable.setItems(billsList);
    }

    // Connect to the database
    private void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/inventory";
            String username = "root";
            String password = "password";
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create the bills table in the database
    private void createBillsTable() {
        try {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS bills (" +
                    "billID INT PRIMARY KEY AUTO_INCREMENT," +
                    "date DATE," +
                    "totalAmount DOUBLE)";
            statement.executeUpdate(createTableQuery);
            System.out.println("Bills table created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get bills from the database
    private ObservableList<Bill> getBillsFromDatabase() {
        ObservableList<Bill> billsList = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM bills";
        try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                int billID = resultSet.getInt("billID");
                Date date = resultSet.getDate("date");
                double totalAmount = resultSet.getDouble("totalAmount");
                billsList.add(new Bill(billID, date, totalAmount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billsList;
    }

    // Add event handler for the "Create Bill" button
    @FXML
    private void handleCreateBill() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Create a new bill object
        Bill newBill = new Bill(0, Date.valueOf(currentDate), 0.0);

        // Insert the new bill into the database
        String insertQuery = "INSERT INTO bills (date, totalAmount) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(currentDate));
            statement.setDouble(2, 0.0);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("New bill added to the database");

                // Get the generated billID from the database and set it in the new bill object
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedBillID = generatedKeys.getInt(1);
                        newBill.setBillID(generatedBillID);
                    }
                }

                // Add the new bill to the table
                billTable.getItems().add(newBill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add event handler for the "Remove Bill" button
    @FXML
    private void handleRemoveBill() {
        // Get the selected bill from the table
        Bill selectedBill = billTable.getSelectionModel().getSelectedItem();

        if (selectedBill != null) {
            // Remove the selected bill from the table
            billTable.getItems().remove(selectedBill);

            // Delete the selected bill from the database
            String deleteQuery = "DELETE FROM bills WHERE billID = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                statement.setInt(1, selectedBill.getBillID());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Bill removed from the database");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Other methods and logic related to bill management

}
