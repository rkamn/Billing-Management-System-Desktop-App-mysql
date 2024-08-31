package in.lightbits.billingmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class HomeController extends VBox {

    @FXML
    private Button btnHome;
    @FXML
    private Button btnNewBuyer;
    @FXML
    private Button btnUpdateBuyer;
    @FXML
    private Button btnBuyerDetails;
    @FXML
    private Button btnDeleteBuyer;
    @FXML
    private Button btnNewProduct;
    @FXML
    private Button btnUpdateProduct;
    @FXML
    private Button btnDeleteProduct;
    @FXML
    private Button btnProductDetails;
    @FXML
    private Button btnBilling;
    @FXML
    private Button btnLogout;

    @FXML
    private Button btnCloseApplication;

    @FXML
    private BorderPane rootPane;
    @FXML
    private Label myLabel1;
    @FXML
    private Label myLabel2;
    @FXML
    private Label myLabel3;
    @FXML
    private Label myLabel4;
    @FXML
    private Label myLabel5;
    @FXML
    private Label myLabel6;
    @FXML
    private Label myLabel7;
    @FXML
    private Label myLabel8;
    @FXML
    private Label myLabel9;
    @FXML
    private Label myLabel10;
    @FXML
    private Label myLabel11;
    @FXML
    private Label myLabel12;
    @FXML
    private Label myLabel13;
    @FXML
    private Label myLabel14;
    @FXML
    private Label myLabel15;
    @FXML
    private Label myLabel16;
    @FXML
    private Label myLabel17;
    @FXML
    private Label myLabel18;
    @FXML
    private Label myLabel19;
    @FXML
    private Label myLabel20;
    @FXML
    private Label myLabel21;

    @FXML
    private String basePath="/in/lightbits/billingmanagementsystem/";

    CustomUtility customUtility = new CustomUtility();


//    @FXML
//    private void initialize() {
//        // Ensure the button is invisible initially
//        if (btnHome != null) {
//            btnNewBuyer.setVisible(false);
//            btnUpdateBuyer.setVisible(false);
//            btnBuyerDetails.setVisible(false);
//            btnDeleteBuyer.setVisible(false);
//            btnNewProduct.setVisible(false);
//            btnUpdateProduct.setVisible(false);
//            btnProductDetails.setVisible(false);
//            btnDeleteProduct.setVisible(false);
//            btnBilling.setVisible(false);
//            btnLogout.setVisible(false);
//            btnCloseApplication.setVisible(false);
//
//            myLabel1.setVisible(false);
//            myLabel2.setVisible(false);
//            myLabel3.setVisible(false);
//            myLabel4.setVisible(false);
//            myLabel5.setVisible(false);
//            myLabel6.setVisible(false);
//            myLabel7.setVisible(false);
//            myLabel8.setVisible(true);  // Home text
//            myLabel9.setVisible(false);
//            myLabel10.setVisible(false);
//            myLabel11.setVisible(false);
//            myLabel12.setVisible(false);
//            myLabel13.setVisible(false);
//            myLabel14.setVisible(false);
//            myLabel15.setVisible(false);
//            myLabel16.setVisible(false);
//            myLabel17.setVisible(false);
//            myLabel18.setVisible(false);
//            myLabel19.setVisible(false);
//            myLabel20.setVisible(false);
//            myLabel21.setVisible(false);
//
//        } else {
//            System.err.println("Button reference is null.");
//        }
//    }
//



    public void btnHomeHandler(ActionEvent actionEvent) throws InterruptedException {
        System.out.println(actionEvent.getEventType() +": btnHomeHandler ");

            btnNewBuyer.setVisible(true);
            btnUpdateBuyer.setVisible(true);
            btnBuyerDetails.setVisible(true);
            btnDeleteBuyer.setVisible(true);
            btnNewProduct.setVisible(true);
            btnUpdateProduct.setVisible(true);
            btnProductDetails.setVisible(true);
            btnDeleteProduct.setVisible(true);
            btnBilling.setVisible(true);
            btnLogout.setVisible(true);
            btnCloseApplication.setVisible(true);

            myLabel1.setVisible(true);
            myLabel2.setVisible(true);
            myLabel3.setVisible(true);
            myLabel4.setVisible(true);
            myLabel5.setVisible(true);
            myLabel6.setVisible(true);
            myLabel7.setVisible(true);
            myLabel8.setVisible(true);  //home
            myLabel9.setVisible(true);
            myLabel10.setVisible(true);
            myLabel11.setVisible(true);
            myLabel12.setVisible(true);
            myLabel13.setVisible(true);
            myLabel14.setVisible(true);
            myLabel15.setVisible(true);
            myLabel16.setVisible(true);
            myLabel17.setVisible(true);
            myLabel18.setVisible(true);
            myLabel19.setVisible(true);
        myLabel20.setVisible(true);
        myLabel21.setVisible(true);

            // bussiness logic for home

    }


    public void btnNewBuyerHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() + ": btnNewBuyerHandler ");
       // navigation to new-buyer-view.fxml
        customUtility.navigationToNewPage(btnNewBuyer, basePath+"new-buyer-view.fxml");
    }

    public void btnUpdateBuyerHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() +": btnUpdateBuyerHandler ");
        // navigation to update-buyer-view.fxml
        customUtility.navigationToNewPage(btnUpdateBuyer, basePath+"update-buyer-view.fxml");
    }

    public void btnBuyerDetailsHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() +": btnBuyerDetailsHandler ");
        // navigation to buyer-details-view.fxml
        customUtility.navigationToNewPage(btnBuyerDetails, basePath+"buyer-details-view.fxml");
    }

    public void btnDeleteBuyerHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() +": btnDeleteBuyerHandler ");
        // navigation to delete-buyer-view.fxml
        customUtility.navigationToNewPage(btnBuyerDetails, basePath+"delete-buyer-view.fxml");
    }

    public void btnNewProductHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() + ": btnNewBuyerHandler ");
        // navigation to new-product-view.fxml
        customUtility.navigationToNewPage(btnNewProduct, basePath+"new-product-view.fxml");
    }

    public void btnUpdateProductHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() +": btnUpdateProductHandler ");
        // navigation to update-product-view.fxml
        customUtility.navigationToNewPage(btnUpdateProduct, basePath+"update-product-view.fxml");
    }

    public void btnDeleteProductHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() +": btnDeleteProductHandler ");
        // navigation to delete-product-view.fxml
        customUtility.navigationToNewPage(btnDeleteProduct, basePath+"delete-product-view.fxml");
    }

    public void btnProductDetailsHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() +": btnBillingHandler ");
        // navigation to product-details-view.fxml
        customUtility.navigationToNewPage(btnProductDetails, basePath+"product-details-view.fxml");
    }
    public void btnBillingHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() +": btnBillingHandler ");
        // navigation to product-details-view.fxml
        customUtility.navigationToNewPage(btnBilling, basePath+"billing-view.fxml");
    }

    @FXML
    public void btnLogoutHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() +": btnLogoutHandler ");
        customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Page Closing..", "Logout, Thank you!");

        // navigation to login-view.fxml
        customUtility.navigationToNewPage(btnLogout, basePath+"login-view.fxml");

    }

    @FXML
    public void btnCloseApplicationHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() +": btnCloseApplicationHandler ");

        customUtility.showAlertAskConfirmation();


    }


}
