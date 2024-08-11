package in.lightbits.billingmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class BillingController {
    @FXML
    private TextField buyersMobile;
    @FXML
    private TextField buyersName;
    @FXML
    private TextField buyersEmail;
    @FXML
    private TextField buyersAddress;

    @FXML
    private TextField productId;
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
    private TextField status;

    @FXML
    private TableView<Products> productsTable;

    @FXML
    private TableColumn<Products, Integer> serialNumColumn;
    @FXML
    private TableColumn<Products, String> productNameColumn;
    @FXML
    private TableColumn<Products, String> priceColumn ;
    @FXML
    private TableColumn<Products, String> descColumn;
    @FXML
    private TableColumn<Products, String> quantityColumn;
    @FXML
    private TableColumn<Products, String> taxRateColumn;
    @FXML
    private TableColumn<Products, String> statusColumn;
    @FXML
    private Button addProductBtn;
    @FXML
    private Button saveBillingBtn;
    @FXML
    private Button resetBillingBtn;
    @FXML
    private Button closeBillingBtn;

    private int countAddProductBtn = 0;

    @FXML
    private String basePath="/in/lightbits/billingmanagementsystem/";
    CustomUtility customUtility = new CustomUtility();
    DataBaseProductIntraction dataBaseProductIntraction = new DataBaseProductIntraction();
    DataBaseIntraction dataBaseIntraction = new DataBaseIntraction();
    List<Products> productsList = new ArrayList<>();
    List<Buyers> buyersList = new ArrayList<>();

    private ObservableList<Products> productsObservableListList;

    @FXML
    public void initialize() {
        // Initialize the columns
        serialNumColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        taxRateColumn.setCellValueFactory(new PropertyValueFactory<>("taxRate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // table data
//        ObservableList<Products> observableProductsList = FXCollections.observableArrayList(getAllProductsDetails());
//        for(Products product : observableProductsList){  // printing in buyer details in console
//            System.out.println(product.getId()+"----"+product.getName()+"---"+product.getDescription()+"---"+product.getPrice());
//        }
        productsObservableListList = FXCollections.observableArrayList();
        productsTable.setItems(productsObservableListList);

    }



    public void searchBuyerByMobileBtnHandler(ActionEvent actionEvent) {
        String mobile = buyersMobile.getText();
        String email = buyersEmail.getText();
        String name = buyersName.getText();
        String address = buyersAddress.getText();

        buyersList = dataBaseIntraction.searchBuyersByMobileNumber(mobile);  // search buyers in databse
        for(Buyers buyer : buyersList){
            System.out.println("ID : " + buyer.getId());
            System.out.println("Name : " + buyer.getName());
            System.out.println("Mobile : " + buyer.getMobile());
            System.out.println("Email : " + buyer.getEmail());
            System.out.println("Address : " + buyer.getAddress());

            buyersMobile.setText(buyer.getMobile());
            buyersName.setText(buyer.getName());
            buyersEmail.setText(buyer.getEmail());
            buyersAddress.setText(buyer.getAddress());

        }

    }



    public void addProductBtnHandler(ActionEvent actionEvent) {
        countAddProductBtn++;
        int sNo = countAddProductBtn;   // generated serial num
        String name = productName.getText();

        productsList = dataBaseProductIntraction.searchProductByName(name);   // search product in Database

        for (Products product : productsList){
            System.out.println("ID : " + product.getId());
            System.out.println("Name : " + product.getName());
            System.out.println("Price : " + product.getPrice());
            System.out.println("Description : " + product.getDescription());
            System.out.println("Quality : " + product.getQuantity());
            System.out.println("Tax Slab : " + product.getTaxRate());
            System.out.println("Status : " + product.getStatus());

            productId.setText(product.getId()+""); // making string due to error
            price.setText(product.getPrice());
            desc.setText(product.getDescription());
            //quantity.setText(product.getQuantity());
            taxRate.setText(product.getTaxRate()+" %");
            status.setText(product.getStatus());
        }

        String priceNew = price.getText();
        String description = desc.getText(); // not mandatory
        String quantityNew = quantity.getText();
        String taxSlab = taxRate.getText();
        String statusNew = status.getText(); // not mandatory

        if(quantityNew.isEmpty() || quantityNew == null || quantityNew.equals(0) || quantityNew.equals('0')){
            //alert
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Input Error", "Please enter Quantity for "+name);
        }

        //set fields non-Editable
        productId.setEditable(false);
        desc.setEditable(false);
        taxRate.setEditable(false);
        status.setEditable(false);

        //Button cancelButton = new Button("Cancel"); => cancelButton not implemented Yet

        if (!name.isEmpty() && !priceNew.isEmpty() && !quantityNew.isEmpty() && !taxSlab.isEmpty()) {
            Products product = new Products(sNo , name, priceNew, description, quantityNew, taxSlab, statusNew);

            productsObservableListList.add(product);

        }

    }

    public void billingSaveBtnHandler(ActionEvent actionEvent) {
    }

    public void billingResetBtnHandler(ActionEvent actionEvent) {

        // Clear the input fields of 
        productId.clear();      // clear products data
        productName.clear();
        price.clear();
        desc.clear();
        quantity.clear();
        taxRate.clear();
        status.clear();

        buyersName.clear();      // clear buyers data
        buyersMobile.clear();
        buyersEmail.clear();
        buyersAddress.clear();

        productsObservableListList.clear();  //clear table data




    }

    public void billingCloseBtnHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() + ": closeBtnHandler ");
        customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Closing Update Bayer Page..", "Thank you!!");
        // navigation to home-view.fxml
        customUtility.navigationToNewPage(closeBillingBtn, basePath+"home-view.fxml");

    }

    public void method(){

    }


}
