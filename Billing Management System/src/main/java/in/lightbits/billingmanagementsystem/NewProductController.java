package in.lightbits.billingmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class NewProductController {

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

    @FXML
    private String basePath="/in/lightbits/billingmanagementsystem/";
    CustomUtility customUtility = new CustomUtility();
    DataBaseProductIntraction dataBaseProductIntraction = new DataBaseProductIntraction();

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


    //work on all handlers

    public void handleStatusSelection(String newValue){

        System.out.println("Selected item: " + newValue);
    }


    public void handleFocusGainedProductName() {

        System.out.println("buyer Name focus gain");
    }

    public void handleFocusLostProductName() {

        System.out.println("buyer Name focus Lost");
    }

    public void handleFocusGainedProductRate() {

        System.out.println("product rate focus gain");
    }

    @FXML
    public void handleFocusLostProductRate() {

        System.out.println("product rate focus Lost");
    }
    public void handleFocusGainedProductDesc() {

        System.out.println("product descriptin focus gain");
    }

    @FXML
    public void handleFocusLostProductDesc() {

        System.out.println("product descriptin focus Lost");
    }
    public void handleFocusGainedProductQuantity() {

        System.out.println("product quantity focus gain");
    }

    @FXML
    public void handleFocusLostProductQuantity() {

        System.out.println("product quantity focus Lost");
    }
    public void handleFocusGainedTaxRate() {

        System.out.println("product tax rate focus gain");
    }

    @FXML
    public void handleFocusLostTaxRate() {
        System.out.println("product tax rate focus Lost");
    }

    @FXML
    public void saveProductBtnHandler(ActionEvent actionEvent) {
        String name = productName.getText();
        String priceRate = price.getText();
        String description = desc.getText();
        String quant = quantity.getText();
        String taxSlab = taxRate.getText();
        String HSNNo = HSN.getText();
        String statusValue = status.getValue();
        System.out.println(name);
        if(name !=null){
            //inserting new product to DB
            System.out.println("Buyer Name : "+name);
            dataBaseProductIntraction.insertNewProductData(name,priceRate,description,quant,taxSlab,HSNNo,statusValue);
            customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Successful", "Data Saved successfully");
            //clear all fiels
            resetProductBtnHandler(actionEvent);
        }else {
            customUtility.showAlertActionStatus(Alert.AlertType.ERROR, "Failed", "Sorry.., Data Not saved.");
        }
    }


    @FXML
    public void resetProductBtnHandler(ActionEvent actionEvent) {
        productName.clear();
        price.clear();
        desc.clear();
        quantity.clear();
        taxRate.clear();
    }






    @FXML
    public void closeProductBtnHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() + ": closeBtnHandler ");
        customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Closing New Product Page..", "Closing..., Thank you!!");
        // navigation to home-view.fxml
        customUtility.navigationToNewPage(closeProductBtn, basePath+"home-view.fxml");
    }

    public void handleFocusGainedHSN(MouseEvent mouseEvent) {
    }

    public void handleFocusLostHSN(MouseEvent mouseEvent) {
    }
}
