package in.lightbits.billingmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class NewBuyerController {
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
    private Button saveBtn;
    @FXML
    private Button resetBtn;
    @FXML
    private Button closeBtn;

    @FXML
    private String basePath="/in/lightbits/billingmanagementsystem/";
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

    //work on all handlers
    public void handleGenderSelection(String newValue){

        System.out.println("Selected item: " + newValue);
    }

    public void handleFocusGainedBuyerName() {

        System.out.println("buyer Name focuse gain");
    }

    public void handleFocusLostBuyerName() {
        System.out.println("buyer Name focuse Lost");
    }

    public void handleFocusGainedBuyerMobile() {

        System.out.println("buyer Mobile focuse gain");
    }

    @FXML
    public void handleFocusLostBuyerMobile() {
        System.out.println("buyer Name focuse Lost");
    }
    public void handleFocusGainedBuyerEmail() {

        System.out.println("buyer Email focuse gain");
    }

    @FXML
    public void handleFocusLostBuyerEmail() {
        System.out.println("buyer Name focuse Lost");
    }






    @FXML
    public void saveBtnHandler(ActionEvent actionEvent) {
            String name = buyerName.getText();
            //int mobile = Integer.parseInt(buyerMobile.getText());
            String mobile = buyerMobile.getText();
            String email = buyerEmail.getText();
            String address = buyerAddress.getText();
            String gender = buyerGenderChoice.getValue();
            System.out.println(name);
            if(name !=null){
                //inserting new buyer to DB
                System.out.println("Buyer Name : "+name);
                dataBaseIntraction.insertNewBuyersData(name,mobile,email, address ,gender);
                customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Successful", "Data Saved successfully, Welcome, Mr. : " + name + " !");
                //clear all fiels
                resetBtnHandler(actionEvent);
            }else {
                customUtility.showAlertActionStatus(Alert.AlertType.ERROR, "Failed", "Sorry.., Data Not saved.");
            }
    }






    @FXML
    public void resetBtnHandler(ActionEvent actionEvent) {
        buyerName.clear();
        buyerEmail.clear();
        buyerMobile.clear();

        //buyerGenderChoice.getItems().clear();
    }






    @FXML
    public void closeBtnHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() + ": closeBtnHandler ");
        customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Closing New Bayer Page..", "Closing..., Thank you!!");
        // navigation to home-view.fxml
        customUtility.navigationToNewPage(closeBtn, basePath+"home-view.fxml");
    }

    public void handleFocusGainedBuyerAddress(MouseEvent mouseEvent) {
    }

    public void handleFocusLostBuyerAddress(MouseEvent mouseEvent) {
    }
}
