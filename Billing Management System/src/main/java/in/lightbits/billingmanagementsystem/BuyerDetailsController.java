package in.lightbits.billingmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuyerDetailsController {
    @FXML
    private Button printBtn;
    @FXML
    private Button closeBtn;

    @FXML
    private TableView<Buyers> buyersTable;
    @FXML
    private TableColumn<Buyers, Integer> buyersId ;
    @FXML
    private TableColumn<Buyers, String> buyersName ;
    @FXML
    private TableColumn<Buyers, String> buyersMobile;
    @FXML
    private TableColumn<Buyers, String> buyersEmail;
    @FXML
    private TableColumn<Buyers, String> buyersGender;
    @FXML
    private TableColumn<Buyers, String> buyersAddress;
    @FXML
    private String basePath="/in/lightbits/billingmanagementsystem/";
    CustomUtility customUtility = new CustomUtility();
    DataBaseIntraction dataBaseIntraction = new DataBaseIntraction();

    @FXML
    public void initialize() {
        // Initialize the columns
        buyersId.setCellValueFactory(new PropertyValueFactory<>("id"));
        buyersName.setCellValueFactory(new PropertyValueFactory<>("name"));
        buyersMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        buyersEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        buyersGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        buyersAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        // populate data in the table
        ObservableList<Buyers> observableBuyersList = FXCollections.observableArrayList(dataBaseIntraction.getAllBuyersDetails());
        for(Buyers buyer : observableBuyersList){  // printing in buyer details in console
            System.out.println(buyer.getId()+"----"+buyer.getName()+"---"+buyer.getMobile());
        }
        buyersTable.setItems(observableBuyersList);
    }

    public void printBtnHandler(ActionEvent actionEvent) {
        boolean success = false;
        List<Buyers> buyersList =  dataBaseIntraction.getAllBuyersDetails();
        if(!buyersList.isEmpty()){
            for(Buyers buyer : buyersList){

                    buyersId.setText(buyer.getId()+"");
                    buyersName.setText(buyer.getName());
                    buyersMobile.setText(buyer.getMobile());
                    buyersEmail.setText(buyer.getEmail());
                    buyersEmail.setText(buyer.getGender());
                    buyersAddress.setText(buyer.getAddress());

                System.out.println("Buyer ID : " + buyer.getId());
                System.out.println("Name : " + buyer.getName());
                System.out.println("Mobile : " + buyer.getMobile());
                System.out.println("Email : " + buyer.getEmail());
                System.out.println("Gender : " + buyer.getGender());
                System.out.println("Address : " + buyer.getAddress());
            }
            success = true;
        }
        System.out.println("inside print all buyers ..");
        if (success) {
            customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION,"Success", "Buyers detail printed successfully.");
        } else {
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Error", "Failed to print buyers information.");
        }
    }

    public void closeBtnHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() + ": closeBtnHandler ");
        customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Closing Bayer Details Page..", "Thank you!!");
        // navigation to home-view.fxml
        customUtility.navigationToNewPage(closeBtn, basePath+"home-view.fxml");
    }
}
