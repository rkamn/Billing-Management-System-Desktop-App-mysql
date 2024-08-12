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
    private static final String DB_URL = "jdbc:mysql://localhost:3306/billing_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rakesh458458";
    @FXML
    private TextField searchBox;

    @FXML
    private TextField buyerName;
    @FXML
    private TextField buyerMobile;
    @FXML
    private TextField buyerEmail;
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
    public void searchBtnHandler() throws SQLException, IOException {
        //get data from database based on mobile
        String searchBoxMobile = searchBox.getText();
        if (searchBoxMobile.isEmpty()) {
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Input Error", "Please enter a mobile number.");
            return;
        }



        String sqlBayerSelectQuery = "select * from billing_system.buyers where mobile = ? ";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");


            try (Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
                 PreparedStatement statement = con.prepareStatement(sqlBayerSelectQuery)){

                statement.setString(1, searchBoxMobile);
                ResultSet resultSet  = statement.executeQuery();


                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    System.out.println("ID : " + id);

                    buyerId = id;
                    System.out.println("Current Buyer id : " + buyerId);
                    buyerName.setText(resultSet.getString("name"));
                    buyerMobile.setText(resultSet.getString("mobile"));
                    buyerEmail.setText(resultSet.getString("email"));
                    buyerGenderChoice.setValue(resultSet.getString("gender"));
                    System.out.println(buyerName);


                    System.out.println("Name : " + buyerName);
                    System.out.println("Mobile : " + buyerMobile);
                    System.out.println("Email : " + buyerEmail);
                    System.out.println("Gender : " + buyerGenderChoice);
                }
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            customUtility.showAlertActionStatus(Alert.AlertType.ERROR, "Database Error", "An error occurred while fetching data.");

        }
    }




//   @FXML
//    public void getDataFromResultSet(TextField name, TextField mobile, TextField email,TextField gender) throws NullPointerException, IOException {
//        System.out.println("+++++++++++++");
//        System.out.println(name);
//        try{
//            System.out.println(buyerName.getText());
//            buyerName.setText(name.getText());
//            System.out.println(buyerName);
//            buyerMobile.setText(buyerMobile.getText());
//            buyerEmail.setText(buyerEmail.getText());
//            buyerGenderChoice.setValue(buyerGenderChoice.getValue());
//        }catch (NullPointerException e){
//            e.printStackTrace();
//        }
//    }


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
            String gender = buyerGenderChoice.getValue();
            String email = buyerEmail.getText();


            // insert data to database at given id
            boolean success = dataBaseIntraction.updateBuyerById( id,  name,  mobile,  email,  gender);
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



}
