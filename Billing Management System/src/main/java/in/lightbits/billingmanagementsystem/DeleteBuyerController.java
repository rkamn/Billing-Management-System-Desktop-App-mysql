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

public class DeleteBuyerController{

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
    private Button deleteBtn;
    @FXML
    private Button resetBtn;
    @FXML
    private Button closeBtn;
    private int buyerId;
    @FXML
    private String basePath="/in/lightbits/billingmanagementsystem/";
    CustomUtility customUtility = new CustomUtility();

    DataBaseIntraction dataBaseIntraction = new DataBaseIntraction();

    public void searchBtnHandler(ActionEvent actionEvent) throws SQLException, IOException {
        String searchBoxMobile = searchBox.getText();
        if (searchBoxMobile.isEmpty()) {
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Input Error", "Please enter a mobile number.");
            return;
        }

        //dataBaseIntraction.fetchDataByMobile(searchBoxMobile);

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

                    //  updateBuyerController.getDataFromResultSet(name,mobileInDB,email,gender);
                }
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            customUtility.showAlertActionStatus(Alert.AlertType.ERROR, "Database Error", "An error occurred while fetching data.");

        }
    }

    public void deleteBtnHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent +" : handler called");
        // check mobilenumber
        if(buyerMobile.getText().isEmpty() || buyerMobile.getText() == null){
            // alert
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING,"Error", "Mobile number not available in database.");
        }else{
            // set data on same it after getting data by id
            System.out.println( buyerMobile.getText()+" has buyer id : "+buyerId);
            System.out.println("set data on same id");

            int id = buyerId;
            String mobile = buyerMobile.getText();

            // insert data to database at given id
            boolean success = dataBaseIntraction.deleteBuyerById( id, mobile);
            System.out.println(success);
            if (success) {
                customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION,"Success", "User information updated successfully.");
            } else {
                customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Error", "Failed to update user information.");
            }
        }
        resetBtnHandler(actionEvent);
    }


    public void resetBtnHandler(ActionEvent actionEvent) {
        searchBox.clear();
        buyerName.clear();
        buyerEmail.clear();
        buyerMobile.clear();
    }

    public void closeBtnHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() + ": closeBtnHandler ");
        customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Closing Bayer Delete Page..", "Thank you!!");

        // navigation to new-buyer-view.fxml
        customUtility.navigationToNewPage(closeBtn, basePath+"home-view.fxml");


    }

    public void handleFocusGainedSearchMobile(MouseEvent mouseEvent) {
    }

    public void handleFocusGainedBuyerEmail(MouseEvent mouseEvent) {
    }

    public void handleFocusGainedBuyerMobile(MouseEvent mouseEvent) {
    }

    public void handleFocusGainedBuyerName(MouseEvent mouseEvent) {
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
