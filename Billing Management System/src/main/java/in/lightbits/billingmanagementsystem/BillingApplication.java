package in.lightbits.billingmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class BillingApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(BillingApplication.class.getResource("hello-view.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(BillingApplication.class.getResource("login-view.fxml"));

        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        primaryStage.setTitle("Welcome..");
        primaryStage.setScene(new Scene(root, 625, 385));
        primaryStage.show();
        System.out.println("show completed!");


    }

    public static void main(String[] args) {

        System.out.println("launch started...");
        launch();

    }
}