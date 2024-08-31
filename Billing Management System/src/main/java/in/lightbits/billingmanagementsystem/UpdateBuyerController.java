package in.lightbits.billingmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class UpdateBuyerController {
    @FXML
    private TextField searchBox;

    @FXML
    private TextField buyerName;
    @FXML
    private TextField buyerMobile;
    @FXML
    private TextField buyerEmail;
    @FXML
    private TextField buyerAddress;
    @FXML
    private ChoiceBox<String> buyerGenderChoice;

    @FXML
    private Button searchBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button resetBtn;
    @FXML
    private Button closeBtn;
    @FXML
    private String basePath="/in/lightbits/billingmanagementsystem/";

    private int buyerId;

    CustomUtility customUtility = new CustomUtility();
    DataBaseIntraction dataBaseIntraction = new DataBaseIntraction();

    @FXML
    private void initialize() {
        // Populate the ChoiceBox with items
        buyerGenderChoice.getItems().addAll("Male", "Female", "Other");
        // Optionally set a default value
        buyerGenderChoice.setValue("Male");
        buyerGenderChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleGenderSelection(newValue);
        });
    }

    public void handleGenderSelection(String newValue){
        System.out.println("Selected item: " + newValue);
    }

    @FXML
    public void searchBtnHandler() {
        //get data from database based on mobile
        String searchBoxMobile = searchBox.getText();
        if (searchBoxMobile.isEmpty()) {
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Input Error", "Please enter a mobile number.");
            return;
        }

        Buyers buyer = dataBaseIntraction.getBuyerByMobileNumber(searchBoxMobile);
        if(buyer !=null){
            buyerId = buyer.getId();
                    System.out.println("Current Buyer id : " + buyerId);
                    buyerName.setText(buyer.getName());
                    buyerMobile.setText(buyer.getMobile());
                    buyerEmail.setText(buyer.getEmail());
                    buyerAddress.setText(buyer.getAddress());
                    buyerGenderChoice.setValue(buyer.getGender());

                    System.out.println("Name : " + buyerName);
                    System.out.println("Mobile : " + buyerMobile);
                    System.out.println("Email : " + buyerEmail);
                    System.out.println("Name : " + buyerAddress);
                    System.out.println("Gender : " + buyerGenderChoice);
        }else {
            customUtility.showAlertActionStatus(Alert.AlertType.ERROR, "Error", "No Data Found in database..");
        }
    }

    @FXML
    public void updateBtnHandler(ActionEvent actionEvent) {

        System.out.println(actionEvent +" : handler called");
        // check mobilenumber
        if(buyerMobile.getText().isEmpty()){
            // alert
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING,"Error: Mobile number empty", "Failed to update Buyer information.");
        }else{
            // set data on same it after getting data by id
            System.out.println( buyerMobile.getText()+" has buyer id : "+buyerId);
            System.out.println("set data on same id");
            int id = buyerId;
            String name = buyerName.getText();
            String mobile = buyerMobile.getText();
            String email = buyerEmail.getText();
            String address = buyerAddress.getText();
            String gender = buyerGenderChoice.getValue();



            // insert data to database at given id
            boolean success = dataBaseIntraction.updateBuyerById( id,  name,  mobile,  email, address, gender);
            System.out.println(success);
            if (success) {
                customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION,"Success", "User information updated successfully.");
            } else {
                customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Error", "Failed to update user information.");
            }
        }
        resetBtnHandler(actionEvent);
    }


    @FXML
    public void resetBtnHandler(ActionEvent actionEvent) {
        searchBox.clear();
        buyerName.clear();
        buyerEmail.clear();
        buyerMobile.clear();
        buyerAddress.clear();
        buyerGenderChoice.setValue("");
    }

    @FXML
    public void closeBtnHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() + ": closeBtnHandler ");
        customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Closing Update Bayer Page..", "Thank you!!");
        // navigation to home-view.fxml
        customUtility.navigationToNewPage(closeBtn, basePath+"home-view.fxml");

    }

    public void handleFocusGainedSearchMobile(MouseEvent mouseEvent) {
    }

    public void handleFocusGainedBuyerName(MouseEvent mouseEvent) {
    }

    public void handleFocusGainedBuyerMobile(MouseEvent mouseEvent) {
    }

    public void handleFocusGainedBuyerEmail(MouseEvent mouseEvent) {
    }

    public void handleFocusLostSearchMobile(MouseEvent mouseEvent) {
    }

    public void handleFocusLostBuyerName(MouseEvent mouseEvent) {
    }

    public void handleFocusLostBuyerMobile(MouseEvent mouseEvent) {
    }

    public void handleFocusLostBuyerEmail(MouseEvent mouseEvent) {
    }


    public void handleFocusGainedBuyerAddress(MouseEvent mouseEvent) {
    }

    public void handleFocusLostBuyerAddress(MouseEvent mouseEvent) {
    }
}
