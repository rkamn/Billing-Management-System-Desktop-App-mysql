package in.lightbits.billingmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
            customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Login Successful", "Welcome Sir/Ma'am, Your Username : " + username);

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
