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

public class DeleteProductController {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/billing_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rakesh458458";

    @FXML
    private Button searchProductBtn;
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
    private Button deleteProductBtn;
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
    public void searchProductBtnHandler(ActionEvent actionEvent) throws SQLException, IOException {
        //get data from database based on name or id
        String searchBoxName = productSearchField.getText();
        //int searchBoxId = Integer.parseInt(searchBoxName);  // check type
        if (searchBoxName.isEmpty()) {
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Input Error", "Please enter a product name or id.");
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






    public void deleteProductBtnHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent +" : handler called");
        // check productName
        if(productName.getText().isEmpty() || productName.getText() == null){
            // alert
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING,"Error", "Product not available in database.");
        }else{
            // set data on same it after getting data by id
            System.out.println( productName.getText()+" has buyer id : "+productId);
            System.out.println("delete product from the databse");

            int id = productId;
            String name = productName.getText();

            // insert data to database at given id
            boolean success = dataBaseProductIntraction.deleteProductById( id, name);
            System.out.println(success);
            if (success) {
                customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION,"Success", "Product information updated successfully.");
            } else {
                customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Error", "Failed to update product information.");
            }
        }
        resetProductBtnHandler(actionEvent);
    }

    public void handleFocusGainedProductSearch(MouseEvent mouseEvent) {
    }

    public void handleFocusLostProductSearch(MouseEvent mouseEvent) {
    }
    public void resetProductBtnHandler(ActionEvent actionEvent) {
        productName.clear();
        price.clear();
        desc.clear();
        quantity.clear();
        taxRate.clear();
    }

    public void closeProductBtnHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() + ": closeBtnHandler ");
        customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Closing delete product page..", "Thank you!!");
        // navigation to home-view.fxml
        customUtility.navigationToNewPage(closeProductBtn, basePath+"home-view.fxml");

    }
}
