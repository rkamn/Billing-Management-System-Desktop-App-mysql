package in.lightbits.billingmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.font.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BillingController {
    @FXML
    private TextField buyersMobile;
    @FXML
    private TextField buyersName;
    @FXML
    private TextField buyersEmail;
    @FXML
    private TextField buyersAddress;

    @FXML
    private TextField productId;
    @FXML
    private TextField productName;
    @FXML
    private TextField price;
    @FXML
    private TextField desc;
    @FXML
    private TextField quantity;
    @FXML
    private TextField taxRate;
    @FXML
    private TextField status;

    @FXML
    private TableView<Products> productsTable;

    @FXML
    private TableColumn<Products, Integer> serialNumColumn;
    @FXML
    private TableColumn<Products, String> productNameColumn;
    @FXML
    private TableColumn<Products, String> priceColumn ;
    @FXML
    private TableColumn<Products, String> descColumn;
    @FXML
    private TableColumn<Products, String> quantityColumn;
    @FXML
    private TableColumn<Products, String> taxRateColumn;
    @FXML
    private TableColumn<Products, String> statusColumn;
    @FXML
    private Button addProductBtn;
    @FXML
    private Button saveBillingBtn;
    @FXML
    private Button resetBillingBtn;
    @FXML
    private Button closeBillingBtn;

    @FXML
    private TextField totalAmount;
    @FXML
    private TextField paidAmount;
    @FXML
    private TextField returnedAmount;

    @FXML
    private Label shopName;
    @FXML
    private Label shopAddress;
    @FXML
    private Label gstValue;
    @FXML
    private Label currentUser;
    @FXML
    private Label todaysDate;
    @FXML
    private Label timeNow;

    private int countAddProductBtn = 0;

    private float totalAmt = 0;
    private float paidAmt = 0;
    private float returnedAmt = 0;

    @FXML
    private String basePath="/in/lightbits/billingmanagementsystem/";
    CustomUtility customUtility = new CustomUtility();
    DataBaseProductIntraction dataBaseProductIntraction = new DataBaseProductIntraction();
    DataBaseIntraction dataBaseIntraction = new DataBaseIntraction();
    List<Products> productsList = new ArrayList<>();
    List<Buyers> buyersList = new ArrayList<>();
    private ListView<Products> productListPDFView;

    private ObservableList<Products> productsObservableList;


    @FXML
    public void initialize() {
        handleUserProfileName();  // set username of logged in user
        currentTimeDateProfileImage(); // set current time and date and profile image

        // Initialize the columns
        serialNumColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        taxRateColumn.setCellValueFactory(new PropertyValueFactory<>("taxRate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // table data
//        ObservableList<Products> observableProductsList = FXCollections.observableArrayList(getAllProductsDetails());
//        for(Products product : observableProductsList){  // printing in buyer details in console
//            System.out.println(product.getId()+"----"+product.getName()+"---"+product.getDescription()+"---"+product.getPrice());
//        }
        productsObservableList = FXCollections.observableArrayList();
        productsTable.setItems(productsObservableList);
    }


    public void searchBuyerByMobileBtnHandler(ActionEvent actionEvent) {
        String mobile = buyersMobile.getText();
        String email = buyersEmail.getText();
        String name = buyersName.getText();
        String address = buyersAddress.getText();

        buyersList = dataBaseIntraction.searchBuyersByMobileNumber(mobile);  // search buyers in databse
        for(Buyers buyer : buyersList){
            System.out.println("ID : " + buyer.getId());
            System.out.println("Name : " + buyer.getName());
            System.out.println("Mobile : " + buyer.getMobile());
            System.out.println("Email : " + buyer.getEmail());
            System.out.println("Address : " + buyer.getAddress());

            buyersMobile.setText(buyer.getMobile());
            buyersName.setText(buyer.getName());
            buyersEmail.setText(buyer.getEmail());
            buyersAddress.setText(buyer.getAddress());

        }

    }


    public void addProductBtnHandler(ActionEvent actionEvent) {
        countAddProductBtn++;
        int sNo = countAddProductBtn;   // generated serial num
        String name = productName.getText();

        productsList = dataBaseProductIntraction.searchProductByName(name);   // search product in Database

        for (Products product : productsList){
            System.out.println("ID : " + product.getId());
            System.out.println("Name : " + product.getName());
            System.out.println("Price : " + product.getPrice());
            System.out.println("Description : " + product.getDescription());
            System.out.println("Quality : " + product.getQuantity());
            System.out.println("Tax Slab : " + product.getTaxRate());
            System.out.println("Status : " + product.getStatus());

            productId.setText(product.getId()+""); // making string due to error
            price.setText(product.getPrice());
            desc.setText(product.getDescription());
            //quantity.setText(product.getQuantity());
            taxRate.setText(product.getTaxRate()+" %");
            status.setText(product.getStatus());
        }

        String priceNew = price.getText();
        String description = desc.getText(); // not mandatory
        String quantityNew = quantity.getText();
        String taxSlab = taxRate.getText();
        String statusNew = status.getText(); // not mandatory

        if(quantityNew.isEmpty() || quantityNew == null || quantityNew.equals(0) || quantityNew.equals('0')){
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Quantity Input Error", "Please enter Quantity for "+name);
        }

        //Button cancelButton = new Button("Cancel"); => cancelButton not implemented Yet

        if (!name.isEmpty() && !priceNew.isEmpty() && !quantityNew.isEmpty() && !taxSlab.isEmpty()) {
            System.out.println("Quantity : "+quantityNew);

            Products foundProduct = findProductByName(productsObservableList, name);  // find duplicate by name
            if (foundProduct != null) {
                System.out.println("Duplicate Product found: " + foundProduct.getName());
                // Further actions with foundProduct
                int quantity= Integer.parseInt(foundProduct.getQuantity()) + Integer.parseInt(quantityNew);
                quantityNew = quantity+"";
                foundProduct.setQuantity(quantityNew);
                System.out.println("Updated Quantity : "+quantityNew);

                Products updatedProduct = new Products(sNo , name, priceNew, description, quantityNew, taxSlab, statusNew);
                productsObservableList.removeIf(product -> product.getName().equals(name));
                productsObservableList.add(updatedProduct);
            }else{
                Products product = new Products(sNo , name, priceNew, description, quantityNew, taxSlab, statusNew);
                productsObservableList.add(product);
            }
        }

        //set fields non-Editable
        productId.setEditable(false);
        desc.setEditable(false);
        taxRate.setEditable(false);
        status.setEditable(false);

        productName.clear(); // makes productName empty to search new name

        calculateTotalSummation();  // calculate your total amount

    }

    public void billingSaveBtnHandler(ActionEvent actionEvent) {


        paidAmountCalculation(); // paid and return amount calculation
        returnedAmountCalculation(); // returned and return amount calculation

        invoice();  // invoice display

        invoicePDF();  // invoice save in the computer




        //saveInvoiceToDatabase();  // save invoice to database
    }

    public void invoicePDF(){
        //generate invoice pdf
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Invoice");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(productsTable.getScene().getWindow());

        if (file != null) {
            try {
                generateInvoicePDF(productsObservableList, file);
                showAlertForPDF("Invoice saved", "The invoice has been successfully saved to " + file.getAbsolutePath());
            } catch (IOException e) {
                showAlertForPDF("Error", "Failed to save the invoice: " + e.getMessage());
            }
        }
    }
    private void showAlertForPDF(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("PDF header");
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void generateInvoicePDF(ObservableList<Products> productsObservableList, File file) throws IOException {

        try (PDDocument document = new PDDocument()) {

            PDPage page = new PDPage();
            document.addPage(page);

            //below 2 lines added becouse PDType1Font.HELVETICA_BOLD is not supporting in my macbook,
            // it may support in windows and other devices uncomment and try
            File fontFile = new File(getClass().getResource("/fonts/open-sans/NotoSans-Regular.ttf").toURI());
            PDType0Font font = PDType0Font.load(document, fontFile);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
               // contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.setFont(font, 14);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("INVOICE PDF Generated");
                //contentStream.showText(invoiceHeader);
                contentStream.endText();

                float yPosition = 650;

                //contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.setFont(font, 12);

                for (Products product : productsObservableList) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText("Product: " + product.getName());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition - 30);
                    contentStream.showText("Price: ₹" + product.getPrice());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition - 15);
                    contentStream.showText("Quantity: " + product.getQuantity());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition - 45);
                    contentStream.showText("Total Price: ₹" + product.getTotalPriceOfOneProduct());
                    contentStream.endText();

                    yPosition -= 70;
                }

                // Draw total amount
                double totalAmount = productsObservableList.stream().mapToDouble(Products::getTotalPriceOfOneProduct).sum();
                contentStream.beginText();
                contentStream.newLineAtOffset(100, yPosition);
                //contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.setFont(font, 12);
                contentStream.showText("Total Amount Due: ₹" + totalAmount);
                contentStream.endText();
            }

            document.save(file);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    public void invoice(){
        String invoiceHeader = generateInvoiceHeader();
        String invoice = generateInvoice(productsObservableList);   // generate invoice to other page
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invoice");
        alert.setHeaderText(invoiceHeader);
        alert.setContentText(invoice);
        alert.showAndWait();
    }

    public String generateInvoiceHeader(){

        String mobile = buyersMobile.getText();
        Buyers buyer = dataBaseIntraction.getBuyerByMobileNumber(mobile);

        StringBuilder invoiceHeader = new StringBuilder();

        invoiceHeader.append("N: ").append(shopName.getText()).append("\n");
        invoiceHeader.append("A: ").append(shopAddress.getText()).append("\n");
        invoiceHeader.append("GST No. : ").append(gstValue.getText()).append("     Date: ").append(todaysDate.getText()).append("    Time: ").append(timeNow.getText()).append("\n");

        invoiceHeader.append("C Name: "+buyer.getName()+"    Mob.: "+buyer.getMobile()+"    Adr: "+buyer.getAddress());

        System.out.println("INVOICE Header");
        System.out.println(buyer.getName());
        System.out.println(buyer.getAddress());
        System.out.println(buyer.getMobile());

        return invoiceHeader.toString();
    }


    public String generateInvoice(ObservableList<Products> productsObservableList){

        StringBuilder invoice = new StringBuilder();
        invoice.append("INVOICE\n");
        invoice.append("----------------------------\n");


        for (Products product : productsObservableList) {
            invoice.append("Product: ").append(product.getName()).append("\n");
            invoice.append("Price per Unit: ₹").append(product.getPrice()).append("\n");
            invoice.append("Quantity: ").append(product.getQuantity()).append("\n");
            float cgst = Float.parseFloat(product.getPrice()) * Float.parseFloat(product.getTaxRate().substring(0,2))/100;
            invoice.append("CGST: ₹").append(cgst).append("\n");
            invoice.append("SGST: ₹").append(cgst).append("\n");
            invoice.append("Total Price: ₹").append(Float.parseFloat(product.getPrice()) * Float.parseFloat(product.getQuantity())).append("\n");
            invoice.append("----------------------------\n");

        }

        invoice.append("Total Amount Due: ₹").append(calculateTotalSummation()).append("\n");
        invoice.append("Total Amount Paid: ₹").append(paidAmountCalculation()).append("\n");
        invoice.append("Amount Due / Returned: ₹").append(returnedAmountCalculation()).append("\n");
        invoice.append("----------------------------\n");
        invoice.append("Thank you for your purchase!");

        System.out.println("INVOICE body ended ");

        return invoice.toString();
    }

    public void billingResetBtnHandler(ActionEvent actionEvent) {

        // Clear the input fields of 
        productId.clear();      // clear products data
        productName.clear();
        price.clear();
        desc.clear();
        quantity.clear();
        taxRate.clear();
        status.clear();

        buyersName.clear();      // clear buyers data
        buyersMobile.clear();
        buyersEmail.clear();
        buyersAddress.clear();

        productsObservableList.clear();  //clear table data

    }


    public void billingCloseBtnHandler(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType() + ": closeBtnHandler ");
        customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Closing Update Bayer Page..", "Thank you!!");
        // navigation to home-view.fxml
        customUtility.navigationToNewPage(closeBillingBtn, basePath+"home-view.fxml");
    }


    public static Products findProductByName(ObservableList<Products> productList, String name) {
        for (Products product : productList) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null; // Return null if no product is found
    }

    public float getTotalPriceOfOneProduct(){
        float p = Float.parseFloat(price.getText());
        float q = Float.parseFloat(quantity.getText());
        return p*q;
    }

    public float calculateTotalSummation(){
        float sum = 0;
        // total amount calculation
        for(Products product : productsObservableList){
            float p = Float.parseFloat(product.getPrice());
            float q = Float.parseFloat(product.getQuantity());
            sum += p * q;
        }
        totalAmt = sum;
        System.out.println("Total Amount: "+totalAmt);
        //String tAmount = totalAmount.getText();
        totalAmount.setText(totalAmt+"");
        totalAmount.setEditable(false);

        return totalAmt;
    }

    public float paidAmountCalculation(){
        // paid amount calculation
        String pAmount = paidAmount.getText();
        if (pAmount.isEmpty()) {
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Input Error", "You have not made the payment");
        }
        paidAmt = Integer.parseInt(pAmount);
        System.out.println("Amount Paid: "+paidAmt);
        return  paidAmt;
    }
    public float returnedAmountCalculation(){
        //returned amount calculation
        String rAmount = returnedAmount.getText();
        returnedAmt = totalAmt - paidAmt;
        System.out.println("Amount Returned: "+returnedAmt);
        returnedAmount.setText(returnedAmt+"");
        returnedAmount.setEditable(false);
        return returnedAmt;
    }



    public void handleUserProfileName(){
        String username = SessionManager.getInstance().getUsername();
        if(username != null){
            currentUser.setText(username);
        }
        System.out.println("Current logged in User: "+username);
    }

    public void currentTimeDateProfileImage(){
        LocalTime currentTime = LocalTime.now();

        // Format the time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        // Set the time to the Label
        timeNow.setText(formattedTime);

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(dateFormatter);

        // Set the date to the lebel
        if (todaysDate != null) {
            todaysDate.setText(formattedDate);
        }
    }


}
