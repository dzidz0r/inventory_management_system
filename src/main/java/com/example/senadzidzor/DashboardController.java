package com.example.senadzidzor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {


    @FXML
    private void initialize() {
        // You can perform any setup or initialization here if needed.
    }

    @FXML
    private void goToGoodsManagement() {
        // Code to navigate to the Goods Management page goes here
        loadFXML("GoodsManagement.fxml");
    }

    @FXML
    private void goToVendorManagement() {
        // Code to navigate to the Vendor Management page goes here
        loadFXML("VendorManagement.fxml");
    }

    @FXML
    private void goToBillManagement() {
        // Code to navigate to the Bill Management page goes here
        loadFXML("BillManagement.fxml");
    }

    /*@FXML
    private void goToReportManagement() {
        // Code to navigate to the Report Management page goes here
        loadFXML("/path/to/ReportManagement.fxml");
    }*/

    // Other methods and logic related to the dashboard

    private void loadFXML(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
