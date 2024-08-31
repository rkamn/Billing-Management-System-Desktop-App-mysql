package in.lightbits.billingmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UpdateProductController {

    @FXML
    private TextField productSearchField;
    @FXML
    private TextField productName;
    @FXML
    private TextField price;
    @FXML
    private TextField desc;
    @FXML
    private TextField quantity;
    @FXML
    private TextField taxRate;
    @FXML
    private TextField HSN;

    @FXML
    private ChoiceBox<String> status;


    @FXML
    private Button saveProductBtn;
    @FXML
    private Button resetProductBtn;
    @FXML
    private Button closeProductBtn;

    int productId;
    @FXML
    private String basePath="/in/lightbits/billingmanagementsystem/";
    CustomUtility customUtility = new CustomUtility();
    DataBaseProductIntraction dataBaseProductIntraction = new DataBaseProductIntraction();

    List<Products> productsList = new ArrayList<>();
    @FXML
    private void initialize() {
        // Populate the ChoiceBox with items
        status.getItems().addAll("Yes", "No");
        // Optionally set a default value
        status.setValue("Yes");

        status.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleStatusSelection(newValue);
        });
    }

    public void handleStatusSelection(String newValue){
        System.out.println("Selected item: " + newValue);
    }



    @FXML
    public void searchProductBtnHandler(ActionEvent actionEvent){
        //get data from database based on name or id
        String searchBoxName = productSearchField.getText();
        //int searchBoxId = Integer.parseInt(searchBoxName);  // check type
        if (searchBoxName.isEmpty()) {
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Input Error", "Please enter a mobile number.");
            return;
        }


        productsList = dataBaseProductIntraction.searchProductByName(searchBoxName);

        if(!productsList.isEmpty()){
            for(Products product : productsList){
                productId = product.getId();
                System.out.println("Current Buyer id : " + productId);
                productName.setText(product.getName());
                price.setText(product.getPrice());
                desc.setText(product.getDescription());
                quantity.setText(product.getQuantity());
                taxRate.setText(product.getTaxRate());
                HSN.setText(product.getHSN());
                status.setValue(product.getStatus());

                System.out.println("Name : " + productName);
                System.out.println("Mobile : " + price);
                System.out.println("Email : " + desc);
                System.out.println("Gender : " + quantity);
                System.out.println("Email : " + taxRate);
                System.out.println("Email : " + HSN);
                System.out.println("Gender : " + status);
            }
        }
    }


    @FXML
    public void updateProductBtnHandler(ActionEvent actionEvent) {

        System.out.println(actionEvent +" : handler called");
        // check searchbox
        if(productSearchField.getText().isEmpty()){
            // alert
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING,"Error : Search box empty", "Failed to update Buyer information.");
        }else{
            // set data on same it after getting data by id
            System.out.println( productSearchField.getText()+" has buyer id : "); //  +buyerId
            System.out.println("set data on same id");
            int id = productId;
            String name = productName.getText();
            String pricePerUnit = price.getText();
            String description = desc.getText();
            String quant = quantity.getText();
            String taxSlab = taxRate.getText();
            String HSNNo = HSN.getText();
            String statusValue = status.getValue();



            // insert data to database at given id
            boolean success = dataBaseProductIntraction.updateProductById(id, name,  pricePerUnit,  description,  quant, taxSlab, HSNNo, statusValue);
            System.out.println(success);
            if (success) {
                customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION,"Success", "Product information updated successfully.");
            } else {
                customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Error", "Failed to update user information.");
            }
        }
        resetProductBtnHandler(actionEvent);
    }


    @FXML
    public void resetProductBtnHandler(ActionEvent actionEvent) {
        productName.clear();
        price.clear();
        desc.clear();
        quantity.clear();
        taxRate.clear();
        HSN.clear();
    }

    @FXML
    public void closeProductBtnHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() + ": closeBtnHandler ");
        customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Closing Update Bayer Page..", "Thank you!!");
        // navigation to home-view.fxml
        customUtility.navigationToNewPage(closeProductBtn, basePath+"home-view.fxml");

    }


    public void handleFocusGainedProductSearch(MouseEvent mouseEvent) {
    }

    public void handleFocusGainedProductName(MouseEvent mouseEvent) {
    }

    public void handleFocusGainedProductPrice(MouseEvent mouseEvent) {
    }

    public void handleFocusGainedProductDesc(MouseEvent mouseEvent) {
    }

    public void handleFocusGainedProductQuantity(MouseEvent mouseEvent) {
    }

    public void handleFocusGainedTaxRate(MouseEvent mouseEvent) {
    }

    public void handleFocusLostProductSearch(MouseEvent mouseEvent) {
    }

    public void handleFocusLostProductName(MouseEvent mouseEvent) {
    }

    public void handleFocusLostProductPrice(MouseEvent mouseEvent) {
    }

    public void handleFocusLostProductDesc(MouseEvent mouseEvent) {
    }

    public void handleFocusLostProductQuantity(MouseEvent mouseEvent) {
    }

    public void handleFocusLostTaxRate(MouseEvent mouseEvent) {
    }

    public void handleFocusGainedHSN(MouseEvent mouseEvent) {
    }

    public void handleFocusLostHSN(MouseEvent mouseEvent) {
    }
}
