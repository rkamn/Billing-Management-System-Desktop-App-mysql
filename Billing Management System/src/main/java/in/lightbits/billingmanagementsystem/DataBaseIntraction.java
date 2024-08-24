package in.lightbits.billingmanagementsystem;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.*;

public class DataBaseIntraction {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/billing_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rakesh458458";
    CustomUtility customUtility = new CustomUtility();



    //authenticate user
    boolean authenticateUser(String username, String password) {
        boolean isAuthenticated = false;
        try {
            // Optional but good practice for older versions
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                System.out.println(sql);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);
                    statement.setString(2, password);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            isAuthenticated = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAuthenticated;
    }


    public Buyers getBuyerByMobileNumber(String mobile) {

        Buyers buyer = new Buyers();
        String sqlBayerSelectQuery = "select * from billing_system.buyers where mobile = ? ";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");


            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement statement = con.prepareStatement(sqlBayerSelectQuery)) {

                statement.setString(1, mobile);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    System.out.println("ID : " + id);

                    buyer.setId(resultSet.getInt("id"));
                    buyer.setName(resultSet.getString("name"));
                    buyer.setMobile(resultSet.getString("mobile"));
                    buyer.setEmail(resultSet.getString("email"));
                    buyer.setGender(resultSet.getString("gender"));
                    buyer.setAddress(resultSet.getString("address"));

                    System.out.println("Name : " + buyer.getName());
                    System.out.println("Mobile : " + buyer.getMobile());
                    System.out.println("Email : " + buyer.getEmail());
                    System.out.println("Gender : " + buyer.getGender());
                    System.out.println("Address : " + buyer.getAddress());
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            customUtility.showAlertActionStatus(Alert.AlertType.ERROR, "Database Error", "An error occurred while fetching data.");

        }
        return buyer;
    }

    public Shop getShopDetailsByShopPincode(String pincode) {
        Shop shop = new Shop();

        String shopQuery = "select * from billing_system.shop where pincode = ? ";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");


            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement statement = con.prepareStatement(shopQuery)) {

                statement.setString(1, pincode);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("shopId");
                    System.out.println("ID : " + id);

                    //buyer.setId(resultSet.getInt("id"));
                    shop.setShopName(resultSet.getString("shopName"));
                    shop.setShopAddress(resultSet.getString("shopAddress"));
                    shop.setShopGST(resultSet.getString("shopGST"));
                    shop.setShopMobile(resultSet.getString("shopMobile"));
                    shop.setShopPin(resultSet.getString("pincode"));
                    shop.setShopOwner(resultSet.getString("shopOwner"));
                    shop.setShopEmail(resultSet.getString("email"));

                    System.out.println("Shop Name : " + shop.getShopName());
                    System.out.println("Mobile : " + shop.getShopMobile());
                    System.out.println("Email : " + shop.getShopEmail());
                    System.out.println("Address : " + shop.getShopAddress());
                    System.out.println("GST : " + shop.getShopGST());
                    System.out.println("Owner : " + shop.getShopOwner());
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            customUtility.showAlertActionStatus(Alert.AlertType.ERROR, "Database Error", "An error occurred while fetching data.");
        }
        return shop;
    }



    public List<Buyers> getAllBuyersDetails1() {
        List<Buyers> buyers = new ArrayList<>();
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

                    buyers.add(new Buyers(id, name, mobile, email, gender, address));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buyers;
    }


    public List<Buyers> getAllBuyersByMobileNumbers() {
        List<Buyers> buyers = new ArrayList<>();
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

                    buyers.add(new Buyers(id, name, mobile, email, gender, address));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buyers;
    }


    //update existing buyer by id
    public boolean updateBuyerById(int id, String name, String mobile, String email, String address, String gender) {
        //String updateBuyerQuery =   "INSERT INTO billing_system.buyers ( id, name, mobile, email, gender) values (?,?,?,?,?)";
        String updateBuyerQuery = "UPDATE billing_system.buyers SET name = ?, mobile = ?, email = ?, address = ? ,gender = ? WHERE id = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(updateBuyerQuery)) {

                stmt.setString(1, name);
                stmt.setString(2, mobile);
                stmt.setString(3, email);
                stmt.setString(4, address);
                stmt.setString(5, gender);
                stmt.setInt(6, id);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
          //  return  false;
        }
        return true;
    }





    //Delete existing buyer by id
    public boolean deleteBuyerById(int id, String mobile) {
        String deleteQuery = "DELETE FROM billing_system.buyers WHERE id = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //  return  false;
        }
        return true;
    }




    //insert new buyer entry to db
    public void insertNewBuyersData(String name, String mobile, String email, String address, String gender) {
        String insertNewBuyerSQLQuery =   "INSERT INTO billing_system.buyers ( name, mobile, email,address, gender) values (?,?,?,?,?)";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertNewBuyerSQLQuery)) {
            stmt.setString(1, name);
            stmt.setString(2, mobile);
            stmt.setString(3, email);
            stmt.setString(4, address);
            stmt.setString(5, gender);
            stmt.executeUpdate();
        }
    }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean storeInvoiceDataToDatabase(int buyer_id, String name, String mobile , float total, LocalDate date, String invoice, float cgst, float sgst, float taxable_price) {
        boolean status = false;
        String invoiceInsertQuery = "INSERT INTO invoices (buyer_id, name, mobile, total, date, invoice, cgst, sgst, taxable_price) VALUES (?, ?, ?, ?, ?,?,?,?, ?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement statement = conn.prepareStatement(invoiceInsertQuery)) {

                statement.setInt(1, buyer_id);
                statement.setString(2, name);
                statement.setString(3, mobile);
                statement.setDouble(4, total);
                statement.setDate(5, java.sql.Date.valueOf(date));
                statement.setString(6, invoice);
                statement.setDouble(7, cgst);
                statement.setDouble(8, sgst);
                statement.setDouble(9, taxable_price);

                statement.executeUpdate();
                status = true;

             }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        return status;

    }



    public List<Buyers> searchBuyersByMobileNumber(String mobile) {

        List<Buyers> buyersList = new ArrayList<>();

        String sqlBayerSelectQuery = "select * from billing_system.buyers where mobile = ? ";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);

                 PreparedStatement statement = con.prepareStatement(sqlBayerSelectQuery)){

                statement.setString(1, mobile);
                ResultSet resultSet  = statement.executeQuery();
                if(resultSet.next()){
                    System.out.println(mobile+" mobile available");
//
//                while (resultSet.next()) {
                    int id = resultSet.getInt("id");

                    //buyerName.setText(resultSet.getString("name"));
                    String name = resultSet.getString("name");
                    String mobileInDB = resultSet.getString("mobile");
                    String email = resultSet.getString("email");
                    String gender = resultSet.getString("gender");
                    String address = resultSet.getString("address");

                    buyersList.add(new Buyers(id, name, mobileInDB, email, gender,address));

                    System.out.println("ID : " + id);
                    System.out.println("Name : " + name);
                    System.out.println("Mobile : " + mobileInDB);
                    System.out.println("Email : " + email);
                    System.out.println("Gender : " + gender);

                    }
     //           }
                else {
                    System.out.println(mobile+" mobile not available in database");
                    if(mobile.isEmpty()){
                        customUtility.showAlertActionStatus(Alert.AlertType.INFORMATION, "Error",  "Please provide mobile number");
                    }
                    }
                }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            }
        return buyersList;
    }



}
