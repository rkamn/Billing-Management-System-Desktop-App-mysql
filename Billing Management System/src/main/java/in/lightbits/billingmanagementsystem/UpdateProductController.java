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

public class UpdateProductController {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/billing_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rakesh458458";

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
    public void searchProductBtnHandler(ActionEvent actionEvent) throws SQLException, IOException {
        //get data from database based on name or id
        String searchBoxName = productSearchField.getText();
        //int searchBoxId = Integer.parseInt(searchBoxName);  // check type
        if (searchBoxName.isEmpty()) {
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Input Error", "Please enter a mobile number.");
            return;
        }

        String sqlProductSelectQuery = "select * from billing_system.products where name = ? ";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");


            try (Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
                 PreparedStatement statement = con.prepareStatement(sqlProductSelectQuery)){

                statement.setString(1, searchBoxName);
                ResultSet resultSet  = statement.executeQuery();


                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    System.out.println("ID : " + id);

                    productId = id;
                    System.out.println("Current Buyer id : " + productId);
                    productName.setText(resultSet.getString("name"));
                    price.setText(resultSet.getString("price"));
                    desc.setText(resultSet.getString("description"));
                    quantity.setText(resultSet.getString("quantity"));
                    taxRate.setText(resultSet.getString("tax_slab"));
                    status.setValue(resultSet.getString("status"));

                    System.out.println("Name : " + productName);
                    System.out.println("Mobile : " + price);
                    System.out.println("Email : " + desc);
                    System.out.println("Gender : " + quantity);
                    System.out.println("Email : " + taxRate);
                    System.out.println("Gender : " + status);

                }
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            customUtility.showAlertActionStatus(Alert.AlertType.ERROR, "Database Error", "An error occurred while fetching data.");

        }
    }




//   @FXML
//    public void getDataFromResultSet(String name, String priceDB, String description,String quantityDB, String tax,String statusDB) throws NullPointerException, IOException {
//        System.out.println("+++++++++++++");
//        System.out.println(name);
//        try{
//            System.out.println(productName.getText());
//            productName.setText(name);
//            price.setText(priceDB);
//            desc.setText(description);
//            quantity.setText(quantityDB);
//            taxRate.setText(tax);
//            desc.setText(description);
//            status.setValue(statusDB);
//        }catch (NullPointerException e){
//            e.printStackTrace();
//        }
//    }


//    public void getDataFromResultSet(ResultSet resultSet) throws SQLException {
//
//        while (resultSet.next()) {
//            int id = resultSet.getInt("id");
//            System.out.println("ID : " + id);
//
//            productId = id;
//            System.out.println("Current Buyer id : " + productId);
//            productName.setText("abc");
//            price.setText(resultSet.getString("price"));
//            desc.setText(resultSet.getString("description"));
//            quantity.setText(resultSet.getString("quantity"));
//            taxRate.setText(resultSet.getString("tax_slab"));
//            status.setValue(resultSet.getString("status"));
//
//            System.out.println("Name : " + productName);
//            System.out.println("Mobile : " + price);
//            System.out.println("Email : " + desc);
//            System.out.println("Gender : " + quantity);
//            System.out.println("Email : " + taxRate);
//            System.out.println("Gender : " + status);
//
//        }
//
//    }

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
            String statusValue = status.getValue();



            // insert data to database at given id
            boolean success = dataBaseProductIntraction.updateProductById(id, name,  pricePerUnit,  description,  quant, taxSlab, statusValue);
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
}
