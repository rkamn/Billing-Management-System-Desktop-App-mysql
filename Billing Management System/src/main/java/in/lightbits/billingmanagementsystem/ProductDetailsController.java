package in.lightbits.billingmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailsController {
    @FXML
    private Button printProductBtn;
    @FXML
    private Button closeProductBtn;
    @FXML
    private TableView<Products> productsTable;
    @FXML
    private TableColumn<Products, Integer> productId;
    @FXML
    private TableColumn<Products, String> productName;
    @FXML
    private TableColumn<Products, String> price ;
    @FXML
    private TableColumn<Products, String> desc;
    @FXML
    private TableColumn<Products, String> quantity;
    @FXML
    private TableColumn<Products, String> taxRate;
    @FXML
    private TableColumn<Products, String> HSN;
    @FXML
    private TableColumn<Products, String> status;
    //private int id;
    DataBaseProductIntraction dataBaseProductIntraction = new DataBaseProductIntraction();

    @FXML
    private String basePath="/in/lightbits/billingmanagementsystem/";
    CustomUtility customUtility = new CustomUtility();
    List<Products> productsList = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialize the columns
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        taxRate.setCellValueFactory(new PropertyValueFactory<>("taxRate"));
        HSN.setCellValueFactory(new PropertyValueFactory<>("HSN"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        // populate data in the table
        ObservableList<Products> observableProductsList = FXCollections.observableArrayList(getAllProductsDetails());
        for(Products product : observableProductsList){  // printing in buyer details in console
            System.out.println(product.getId()+"----"+product.getName()+"---"+product.getDescription()+"---"+product.getPrice()+"---"+product.getHSN());
        }
        productsTable.setItems(observableProductsList);
    }


    public List<Products> getAllProductsDetails() {
        productsList = dataBaseProductIntraction.getAllProducts();
        if(!productsList.isEmpty()){
            System.out.println("inside all products ..");
            customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION,"Success", "Products details given below...");
        }else {
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Error", "No Product Found.");
        }
        return productsList;
    }

    public void closeProductBtnHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() + ": closeBtnHandler ");
        customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Closing Product Details Page..", "Thank you!!");

        // navigation to home-view.fxml
        customUtility.navigationToNewPage(closeProductBtn, basePath+"home-view.fxml");

    }
}
