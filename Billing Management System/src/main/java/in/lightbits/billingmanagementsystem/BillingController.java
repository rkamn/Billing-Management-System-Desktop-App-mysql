package in.lightbits.billingmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.apache.pdfbox.jbig2.segments.Table;
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

    private int buyersId;


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
    private Button searchBuyerBtn;
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
    private String shopPin = "841409";
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
    boolean mobileNotAvailableInDatabase = false;


    @FXML
    private String basePath="/in/lightbits/billingmanagementsystem/";
    CustomUtility customUtility = new CustomUtility();
    DataBaseProductIntraction dataBaseProductIntraction = new DataBaseProductIntraction();
    DataBaseIntraction dataBaseIntraction = new DataBaseIntraction();
    List<Products> productsList = new ArrayList<>();
    List<Buyers> buyersList = new ArrayList<>();
    private ListView<Products> productListPDFView;


    private ObservableList<Products> productsObservableList;

    Shop shop = dataBaseIntraction.getShopDetailsByShopPincode(shopPin);
    InvoiceNumberGenerator invoiceNumberGenerator = new InvoiceNumberGenerator();
    String invoiceNum = invoiceNumberGenerator.generateInvoiceNumber();


    public BillingController() throws IOException {
    }

    @FXML
    public void initialize() {
        handleShopProfile();// display shop details on UI
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
    public void handleShopProfile(){
        shopName.setText(shop.getShopName());
        shopAddress.setText(shop.getShopAddress()+", "+shop.getShopPin()+", M: "+shop.getShopMobile());
        gstValue.setText(shop.getShopGST()+" "+shop.getShopEmail());

        System.out.println("Shop Name, Address & GST: "+shop.getShopName()+", "+shop.getShopAddress()+", "+shop.getShopPin()+", M: "+shop.getShopMobile()+", "+shop.getShopGST());

        //here this quantity is for product details
        quantity.setText("1"); // assign default product quantity to display
    }

    public void handleUserProfileName(){
        String username = SessionManager.getInstance().getUsername();
        Users user = dataBaseIntraction.getUsers(username);
        if(username != null){
            currentUser.setText(user.getFullName());
        }
        System.out.println("Current logged in User: "+user.getFullName());
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


    public void searchBuyerByMobileBtnHandler() {

        String mobile = buyersMobile.getText();
        MobileNumberValidation mobileNumberValidation = new MobileNumberValidation(mobile);
        Boolean mobileValidation = mobileNumberValidation.checkMobileValidity();
        if(mobileValidation){
            System.out.println(mobile+" is a valid number, Congratulation!!..");

            buyersList = dataBaseIntraction.searchBuyersByMobileNumber(mobile);  // search buyers in database
        if (!buyersList.isEmpty()) {
            for (Buyers buyer : buyersList) {
                System.out.println("ID : " + buyer.getId());
                System.out.println("Name : " + buyer.getName());
                System.out.println("Mobile : " + buyer.getMobile());
                System.out.println("Email : " + buyer.getEmail());
                System.out.println("Address : " + buyer.getAddress());


                buyersId = buyer.getId();
                buyersMobile.setText(buyer.getMobile());
                buyersName.setText(buyer.getName());
                buyersEmail.setText(buyer.getEmail());
                buyersAddress.setText(buyer.getAddress());

                buyersName.setEditable(false);
                buyersEmail.setEditable(false);
                buyersAddress.setEditable(false);

                mobileNotAvailableInDatabase =false; // meanse mobile number availavle in database

            }
        }
        else{

            mobileNotAvailableInDatabase = true; // mobile not available in database status

            buyersMobile.setText(mobile);

            buyersName.setEditable(true);
            buyersEmail.setEditable(true);
            buyersAddress.setEditable(true);

            searchBuyerBtn.setVisible(true);

        }
    }else{
            System.out.println("Invalid mobile: "+mobile);
            customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Invalid Input", mobile+ " is not a valid mobile, Please count digits");
        }
    }

    public void handleFocusGainedBuyerMobile(MouseEvent mouseEvent) {
    }

    public void handleFocusLostBuyerMobile(MouseEvent mouseEvent) {
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
            System.out.println("Quantity : " + product.getQuantity());
            System.out.println("Tax Slab : " + product.getTaxRate());
            System.out.println("Status : " + product.getStatus());

            productId.setText(product.getId()+""); // making string due to error
            price.setText(product.getPrice());
            desc.setText(product.getDescription());
            //quantity.setText(product.getQuantity()); // this quantity should be user input not DB quantity
            taxRate.setText(product.getTaxRate());  // +" %"
            status.setText(product.getStatus());
        }

        System.out.println("ID : " + productId.getId());
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
        paidAmount.clear();


        calculateTotalSummation();  // calculate your total amount

        updateSoldProductQuantityInDatabase(productId.getText()); // update quantity in DB when order placed

    }

    public void updateSoldProductQuantityInDatabase(String prodId){

        System.out.println("product id: "+ prodId);
         Products product = dataBaseProductIntraction.getProductById(prodId);
        System.out.println(product.getQuantity());
            int qtyInDatabase = Integer.parseInt(product.getQuantity());
            int qtyByUser = Integer.parseInt(quantity.getText());
            String productStatus;
            if( qtyInDatabase <=0 ){
                // do not add to bucket and set status 'no' => msg: product not available in the stock
                productStatus = "No";
                customUtility.showAlertActionStatus(Alert.AlertType.ERROR, "Error", "Insufficient Quantity");
                dataBaseProductIntraction.updateQuantityOfProductInDatabase(prodId, qtyInDatabase,productStatus );
            }
            else if( qtyInDatabase >= qtyByUser ){
                // add to bucket and reduce quantity and set status 'yes'
                productStatus = "Yes";
                dataBaseProductIntraction.updateQuantityOfProductInDatabase(prodId, (qtyInDatabase-qtyByUser),productStatus );
            }else{
                customUtility.showAlertActionStatus(Alert.AlertType.WARNING, "Error", "Insufficient Quantity available");
            }
    }

    public void billingSaveBtnHandler(ActionEvent actionEvent) throws IOException {

        checkBuyersDetailsNotEmpty();  // checks buyer when mobile not available in database

        paidAmountCalculation(); // paid and return amount calculation

        returnedAmountCalculation(); // returned and return amount calculation

        invoice();  // invoice display

        invoicePDF();  // invoice save in the computer

        saveInvoiceToDatabase();  // save invoice to database

        resetStatusAll();

    }

    public void resetStatusAll(){
        searchBuyerBtn.setVisible(true);
        billingResetBtnHandler();
    }

    public void checkBuyersDetailsNotEmpty(){
        //add buyer as a new buyer and add him to the database
        if(!buyersName.getText().isEmpty() && mobileNotAvailableInDatabase){
            //String gender = "null";
            String mobile = buyersMobile.getText();
            String name = buyersName.getText();
            String email = buyersEmail.getText();
            String address = buyersAddress.getText();

            dataBaseIntraction.insertNewBuyersData(name,mobile,email,address,"");
            mobileNotAvailableInDatabase = false;
        }else if(buyersName.getText().isEmpty()){
            customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Error", " One or more buyer's field is empty");
            if(!mobileNotAvailableInDatabase){
                System.out.println("You have not provided buyer's details..");
                assignDefaultValueToBuyer();// assign default value
            }
        }
    }
    public void assignDefaultValueToBuyer(){
        Buyers buyer = dataBaseIntraction.getBuyerByMobileNumber("0XXXXXXXXXX");
        buyersMobile.setText(buyer.getMobile());
        buyersName.setText(buyer.getName());
        buyersId = buyer.getId();
        buyersEmail.setText(buyer.getEmail());
        buyersAddress.setText(buyer.getAddress());
        buyersAddress.setText(buyer.getGender());

        System.out.println("Default ID : " + buyer.getId());
        System.out.println("Default Name : " + buyer.getName());
        System.out.println("Default Mobile : " + buyer.getMobile());
        System.out.println("Default Email : " + buyer.getEmail());
        System.out.println("Default Address : " + buyer.getAddress());
    }
    public boolean saveInvoiceToDatabase(){
        boolean status = false;
        try {
            String customerName = buyersName.getText();
            String mobile = buyersMobile.getText();
            float totalAmount = calculateTotalSummation();
            LocalDate date = LocalDate.now();
            float cgst = cgstOrSgst();
            float sgst = cgstOrSgst();
            float taxablePrice = totalTaxablePrice();
            buyersId = dataBaseIntraction.getBuyerByMobileNumber(mobile).getId();

            status = dataBaseIntraction.storeInvoiceDataToDatabase(buyersId, customerName, mobile, totalAmount, date, invoiceNum, cgst, sgst, taxablePrice);
            System.out.println("Invoice stored with number: " + invoiceNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
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
        alert.setHeaderText(generateInvoiceHeader());
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void generateInvoicePDF(ObservableList<Products> productsObservableList, File file) throws IOException {

        String mobile = buyersMobile.getText();
        Buyers buyer = dataBaseIntraction.getBuyerByMobileNumber(mobile);

        try (PDDocument document = new PDDocument()) {

            PDPage page = new PDPage();
            document.addPage(page);

            //below 2 lines added because PDType1Font.HELVETICA_BOLD is not supporting in my macbook,
            // it may support in windows and other devices uncomment and try
            File fontFile = new File(getClass().getResource("/fonts/font/NotoSans-Regular.ttf").toURI());

            PDType0Font myFont = PDType0Font.load(document, fontFile);


            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                // contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.setFont(myFont, 6);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Invoice No: " + invoiceNum);
                contentStream.newLine();
                contentStream.showText("N: " + shop.getShopName());
                contentStream.newLine();
                contentStream.showText("A: " + shop.getShopAddress()+", "+shop.getShopPin());
                contentStream.newLine();
                contentStream.showText("GST No. : " + shop.getShopGST() + "  Date: " + todaysDate.getText() + "  Time: " + timeNow.getText());
                contentStream.newLine();
                contentStream.showText("C. Name: " + buyer.getName() + "  Mob.: " + buyer.getMobile() + "  Addr: " + buyer.getAddress());
                contentStream.newLine();
                contentStream.showText("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                contentStream.newLine();
                contentStream.endText();

                float yPosition = 670;

                //contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.setFont(myFont, 6);

                String[] productDetails = {"S.No.", "Product", "HSN code","Price", "Quantity", "CGST %", "CGST Amt","SGST %", "SGST Amt", "Total"};
                float margin = 40;
                float yStart = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                //float yPosition = yStart;
                float rowHeight = 15;  // 20
                float tableHeight = rowHeight * productDetails.length;
                float cellMargin = 5f;

                // Draw the table headers
                for (int i = 0; i < productDetails.length; i++) {
//                    contentStream.addRect(margin + i * (tableWidth / productDetails.length), yPosition - rowHeight,
//                            tableWidth / productDetails.length, rowHeight);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin + i * (tableWidth / productDetails.length) + cellMargin,
                            yPosition-11);  // -15
                    contentStream.showText(productDetails[i]);
                    contentStream.endText();
                }


                yPosition = 655;
                int sNo = 0;
                int xPos = 40;
                float xPositionMul = 1.3f;
                for (Products product : productsObservableList) {

                    if(Integer.parseInt(product.getQuantity())==0){ continue; }

                    sNo++;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPos * 1 * xPositionMul, yPosition-11); // serial number
                    contentStream.showText(""+sNo);
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPos * 2 * xPositionMul, yPosition-11); // product name
                    contentStream.showText(product.getName());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPos * 3 * xPositionMul, yPosition-11);  // SSN number
                    contentStream.showText("HSN000" +sNo);
                    contentStream.endText();

                    int localTax = Integer.parseInt(product.getTaxRate());
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPos * 4 * xPositionMul, yPosition-11); // product price exclusive taxes
                    contentStream.showText("₹ " +(Float.parseFloat(product.getPrice()) - (Float.parseFloat(product.getPrice()) * localTax/100)));
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPos * 5 * xPositionMul, yPosition-11); // quanity
                    contentStream.showText(product.getQuantity());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPos * 6 * xPositionMul, yPosition-11); // CGST
                    contentStream.showText(""+Float.parseFloat(product.getTaxRate())/2);
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPos * 7 * xPositionMul, yPosition-11); // CGST amount
                    contentStream.showText("₹ " + ((Float.parseFloat(product.getPrice()) * localTax)/2) /100);
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPos * 8 * xPositionMul, yPosition-11); // SGST
                    contentStream.showText(""+Float.parseFloat(product.getTaxRate())/2);
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPos * 9 * xPositionMul, yPosition-11); //SGST amount
                    contentStream.showText(" ₹ " + ((Float.parseFloat(product.getPrice()) * localTax )/2) /100);
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPos * 10 * xPositionMul, yPosition-11); // sum of each product
                    contentStream.showText(" ₹ " + product.getTotalPriceOfOneProduct());
                    contentStream.endText();

                    yPosition -= rowHeight;
                }


                // Draw total amount
                double totalAmount = productsObservableList.stream().mapToDouble(Products::getTotalPriceOfOneProduct).sum();
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.setFont(myFont, 6);
                contentStream.newLine();
                contentStream.showText("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                contentStream.newLine();
                contentStream.showText("Total Amount Due: ₹" + totalAmount);
                contentStream.newLine();
                contentStream.showText("Total Amount Paid: ₹" + paidAmountCalculation());
                contentStream.newLine();
                contentStream.showText("Amount Due / Returned: ₹" + returnedAmountCalculation());
                contentStream.newLine();
                contentStream.showText("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                contentStream.newLine();
                contentStream.showText("Thank you for your purchase!");
                contentStream.endText();

                contentStream.stroke();
                contentStream.close();

            }

            document.save(file);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    public void invoice() {
       // String invoiceNum = invoiceNumberGenerator.generateInvoiceNumber();
        String invoiceHeader = generateInvoiceHeader();
        String invoice = generateInvoice(productsObservableList);   // generate invoice to other page
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invoice-"+invoiceNum);
        alert.setHeaderText(invoiceHeader);
        alert.setContentText(invoice);
        alert.showAndWait();
    }

    public String generateInvoiceHeader(){

        String mobile = buyersMobile.getText();
        Buyers buyer = dataBaseIntraction.getBuyerByMobileNumber(mobile);
        System.out.println("Current Buyer id: "+buyersId);

        StringBuilder invoiceHeader = new StringBuilder();

        invoiceHeader.append("N: ").append(shop.getShopName()).append("\n");//shopName.getText()
        invoiceHeader.append("A: ").append(shop.getShopAddress()).append(shop.getShopPin()).append("\n");//shopAddress.getText()
        invoiceHeader.append("GST No. : ").append(shop.getShopGST()).append("  Date: ").append(todaysDate.getText()).append("  Time: ").append(timeNow.getText()).append("\n");//gstValue.getText()

        invoiceHeader.append("C Name: "+buyer.getName()+"  Mob.: "+buyer.getMobile()+"  Adr: "+buyer.getAddress());

        System.out.println("INVOICE Header");
        System.out.println(buyer.getName());
        System.out.println(buyer.getAddress());
        System.out.println(buyer.getMobile());

        return invoiceHeader.toString();
    }


    public String generateInvoice(ObservableList<Products> productsObservableList){

        StringBuilder invoice = new StringBuilder();

        invoice.append("Invoice ").append("no.: "+invoiceNum + "\n");

        invoice.append("----------------------------\n");


        for (Products product : productsObservableList) {
            if(Integer.parseInt(product.getQuantity())==0){ continue; }
            invoice.append("Product: ").append(product.getName()).append("\n");
            invoice.append("Price per Unit: ₹").append(product.getPrice()).append("\n");
            invoice.append("Quantity: ").append(product.getQuantity()).append("\n");
            float cgst = (Float.parseFloat(product.getPrice()) * Float.parseFloat(product.getTaxRate())/100)/2;
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

    public void billingResetBtnHandler() {

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
        
        totalAmount.clear();
        paidAmount.clear();
        returnedAmount.clear();

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
    public float cgstOrSgst(){
        float cgstOrSgst = 0;
        for(Products product : productsObservableList){
            float p = Float.parseFloat(product.getPrice());
            float qty = Float.parseFloat(product.getQuantity());
            float tax = Float.parseFloat(product.getTaxRate());
            cgstOrSgst += qty * ((p * tax)/100)/2;
        }
        return cgstOrSgst;
    }
    public float totalTaxablePrice(){
        float taxablePrice = 0;
        for(Products product : productsObservableList){
            float p = Float.parseFloat(product.getPrice());
            float qty = Float.parseFloat(product.getQuantity());
            float tax = Float.parseFloat(product.getTaxRate());
            taxablePrice += (p - (p * tax) / 100) * qty ;
        }
        return taxablePrice;
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





}
