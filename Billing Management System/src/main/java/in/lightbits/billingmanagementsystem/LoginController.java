package in.lightbits.billingmanagementsystem;

//import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class LoginController {
    @FXML
    private TextField usernameField;

   // @FXML
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML
    private Button loginButton;

    @FXML
    private Button closeButton;


    @FXML
    private String basePath="/in/lightbits/billingmanagementsystem/";

    CustomUtility customUtility = new CustomUtility();


    public LoginController(){
       //initializeComponents();
       //setEventHandlers();
    }

    public void handlePasswordCheckBox(ActionEvent actionEvent) {
        System.out.println("inside checkbox");
        // write your logic here
        if(showPasswordCheckBox.isSelected()){
            passwordField.setPromptText(passwordField.getText());
        }
    }


    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        DataBaseIntraction dataBaseIntraction = new DataBaseIntraction();
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println(usernameField);

        Boolean authUser = dataBaseIntraction.authenticateUser(username, password);
        if (authUser) {
            customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, Mr. : " + username + "!");

            // Switch to another scene/window home-view
            customUtility.navigationToNewPage(loginButton,basePath+"home-view.fxml");
//            try {
//                Parent homeView = FXMLLoader.load(getClass().getResource("/in/lightbits/billingmanagementsystem/home-view.fxml"));
//
//                Stage stage = (Stage) loginButton.getScene().getWindow();
//                Scene homeScene = new Scene(homeView);
//                stage.setScene(homeScene);
//                stage.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            System.out.println("Welcome :  " + username +" your login status : "+authUser);
        } else {
            customUtility.showAlertActionStatus(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
            System.out.println("Your Login Status : "+authUser);
            System.out.println("Login Failed, Invalid user : "+ username +" or password : "+ password);
        }
    }

    public void handleClose(ActionEvent actionEvent) {

        customUtility.showAlertAskConfirmation();
    }


}
