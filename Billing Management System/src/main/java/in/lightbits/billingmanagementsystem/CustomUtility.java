package in.lightbits.billingmanagementsystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CustomUtility {

    public void allCloseButtonHandler(){

    }


    public void navigationToNewPage(Button userSentContainerBtn, String basePath){
        try {
            Parent loader = FXMLLoader.load(getClass().getResource(basePath));
            Stage stage = (Stage) userSentContainerBtn.getScene().getWindow();
            Scene homeScene = new Scene(loader);
            stage.setTitle("Billing Management System : Lightbits Infotech pvt ltd");
            stage.setScene(homeScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void showAlertActionStatus(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showAlertAskConfirmation() {
        // Create an alert of type CONFIRMATION
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Please Confirm your Choice..");
        alert.setContentText("Are you sure, you want logout ?");

        // Show the dialog and wait for the user to respond
        Optional<ButtonType> result = alert.showAndWait();

        // Process the user's response
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("User chose Yes.");
            //exit application
            javafx.application.Platform.exit();
            System.exit(0);

        } else {
            System.out.println("User chose No.");
        }
    }


}
