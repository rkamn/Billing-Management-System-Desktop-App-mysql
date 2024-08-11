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

    private static final String DB_URL = "jdbc:mysql://localhost:3306/billing_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rakesh458458";
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
    private TableColumn<Products, String> status;
    //private int id;

    @FXML
    private String basePath="/in/lightbits/billingmanagementsystem/";
    CustomUtility customUtility = new CustomUtility();
    List<Products> products = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialize the columns
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        taxRate.setCellValueFactory(new PropertyValueFactory<>("taxRate"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        // populate data in the table
        ObservableList<Products> observableProductsList = FXCollections.observableArrayList(getAllProductsDetails());
        for(Products product : observableProductsList){  // printing in buyer details in console
            System.out.println(product.getId()+"----"+product.getName()+"---"+product.getDescription()+"---"+product.getPrice());
        }
        productsTable.setItems(observableProductsList);

    }




    public List<Products> getAllProductsDetails() {
        String getAllProductsQuery = "SELECT id, name, price, description, quantity, tax_slab, status FROM billing_system.products";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(getAllProductsQuery)) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    System.out.println(id);
                    String name = rs.getString("name");
                    String price = rs.getString("price");
                    String description = rs.getString("description");
                    String quantity = rs.getString("quantity");
                    String taxRate = rs.getString("tax_slab");
                    String status = rs.getString("status");
//
//                    buyersId.setText(String.valueOf(rs.getInt("id")));
//                    buyersName.setText(rs.getString("name"));
//                    buyersMobile.setText(rs.getString("mobile"));
//                    buyersEmail.setText(rs.getString("email"));
//                    buyersEmail.setText(rs.getString("gender"));
//                    buyersAddress.setText(rs.getString("address"));

                    products.add(new Products(id, name, price, description, quantity, taxRate,status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public boolean printAllProductsOnA4Page(){



        return true;
    }

    public void printProductBtnHandler(ActionEvent actionEvent) {
        getAllProductsDetails();

        boolean success = printAllProductsOnA4Page();

        System.out.println("inside print all buyers ..");

        if (success) {
            customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION,"Success", "Products detail printed successfully.");
        } else {
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Error", "Failed to print products information.");
        }


    }

    public void closeProductBtnHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() + ": closeBtnHandler ");
        customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Closing Product Details Page..", "Thank you!!");

        // navigation to home-view.fxml
        customUtility.navigationToNewPage(closeProductBtn, basePath+"home-view.fxml");

    }
}
