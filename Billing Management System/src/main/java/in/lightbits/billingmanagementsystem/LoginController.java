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
        if(showPasswordCheckBox.isSelected()){
            String password = passwordField.getText();
            System.out.println("inside checkbox: "+password);
            showPasswordCheckBox.setText(password);
        }else {
            showPasswordCheckBox.setText("Show Password");
        }
    }


    @FXML
    public String handleLogin() {
        DataBaseIntraction dataBaseIntraction = new DataBaseIntraction();
        String username = usernameField.getText();
        String password = passwordField.getText();

        usernameField.setText(username);
        System.out.println(username);

        SessionManager.getInstance().setUsername(username);  // store username for later use

        Boolean authUser = dataBaseIntraction.authenticateUser(username, password);
        if (authUser) {
            customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, Mr. : " + username + "!");

            // Switch to another scene/window home-view
            customUtility.navigationToNewPage(loginButton,basePath+"home-view.fxml");

            System.out.println("Welcome :  " + username +" your login status : "+authUser);
        } else {
            customUtility.showAlertActionStatus(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
            System.out.println("Your Login Status : "+authUser);
            System.out.println("Login Failed, Invalid user : "+ username +" or password : "+ password);
        }
        return username;
    }

    public void handleClose(ActionEvent actionEvent) {
        customUtility.showAlertAskConfirmation();
    }


}
