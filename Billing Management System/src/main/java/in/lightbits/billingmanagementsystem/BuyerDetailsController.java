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

    private static final String DB_URL = "jdbc:mysql://localhost:3306/billing_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rakesh458458";

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
    private int bid;
    @FXML
    private String basePath="/in/lightbits/billingmanagementsystem/";
    CustomUtility customUtility = new CustomUtility();
    //DataBaseIntraction dataBaseIntraction = new DataBaseIntraction();

    List<Buyers> buyers = new ArrayList<>();

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
        ObservableList<Buyers> observableBuyersList = FXCollections.observableArrayList(getAllBuyersDetails());
        for(Buyers buyer : observableBuyersList){  // printing in buyer details in console
            System.out.println(buyer.getId()+"----"+buyer.getName()+"---"+buyer.getMobile());
        }
        buyersTable.setItems(observableBuyersList);

    }




    public List<Buyers> getAllBuyersDetails() {
        String getAllBuyersQuery = "SELECT id, name, mobile, email,gender, address FROM billing_system.buyers";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(getAllBuyersQuery)) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    System.out.println(id);
                    String name = rs.getString("name");
                    String mobile = rs.getString("mobile");
                    String email = rs.getString("email");
                    String gender = rs.getString("gender");
                    String address = rs.getString("address");
//
//                    buyersId.setText(String.valueOf(rs.getInt("id")));
//                    buyersName.setText(rs.getString("name"));
//                    buyersMobile.setText(rs.getString("mobile"));
//                    buyersEmail.setText(rs.getString("email"));
//                    buyersEmail.setText(rs.getString("gender"));
//                    buyersAddress.setText(rs.getString("address"));

                    buyers.add(new Buyers(id, name, mobile, email, gender, address));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buyers;
    }

    public boolean printAllBuyersOnA4(){



        return true;
    }
    public void printBtnHandler(ActionEvent actionEvent) {

        getAllBuyersDetails();

        boolean success = printAllBuyersOnA4();
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
