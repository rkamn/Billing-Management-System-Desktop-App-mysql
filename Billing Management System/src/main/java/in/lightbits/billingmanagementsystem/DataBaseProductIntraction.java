package in.lightbits.billingmanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseProductIntraction {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/billing_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rakesh458458";




    public List<Products> searchProductByName(String searchBoxName){
        List<Products> productsList = new ArrayList<>();

        String sqlProductSelectQuery = "select * from billing_system.products where name = ? ";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
                 PreparedStatement statement = con.prepareStatement(sqlProductSelectQuery)){

                statement.setString(1, searchBoxName);
                ResultSet resultSet  = statement.executeQuery();
                //updateProductController.getDataFromResultSet(resultSet);

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");

                    //buyerName.setText(resultSet.getString("name"));
                    String name = resultSet.getString("name");
                    String price = resultSet.getString("price");
                    String desc = resultSet.getString("description");
                    String quantity = resultSet.getString("quantity");
                    String taxRate = resultSet.getString("tax_slab");
                    String status = resultSet.getString("status");

                    productsList.add(new Products(id, name, price, desc, quantity,taxRate,status));

                    System.out.println("ID : " + id);
                    System.out.println("Name : " + name);
                    System.out.println("Price : " + price);
                    System.out.println("Description : " + desc);
                    System.out.println("Quantity : " + quantity);
                    System.out.println("Tax Slab : " + taxRate);
                    System.out.println("Status : " + status);

                    }
                }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            }
        return productsList;
    }








    //update existing buyer by id
    public boolean updateProductById(int id, String name, String price, String description, String quantity, String tax_slab, String status) {
        String updateProductQuery = "UPDATE billing_system.products SET name = ?, price = ?, description = ?,quantity = ?, tax_slab = ?,status = ? WHERE id = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(updateProductQuery)) {

                stmt.setString(1, name);
                stmt.setString(2, price);
                stmt.setString(3, description);
                stmt.setString(4, quantity);
                stmt.setString(5, tax_slab);
                stmt.setString(6, status);
                stmt.setInt(7, id);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //  return  false;
        }
        return true;
    }





    //Delete existing buyer by id
    public boolean deleteProductById(int id, String name) {
        String deleteProductQuery = "DELETE FROM billing_system.products WHERE id = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(deleteProductQuery)) {

                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //  return  false;
        }
        return true;
    }





    //insert new product entry to db
    public void insertNewProductData(String name, String price, String description, String quantity, String tax_slab, String status) {
        String insertNewProductSQLQuery =   "INSERT INTO billing_system.products ( name,price,description,quantity,tax_slab,status) values (?,?,?,?,?,?)";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(insertNewProductSQLQuery)) {
                stmt.setString(1, name);
                stmt.setString(2, price);
                stmt.setString(3, description);
                stmt.setString(4, quantity);
                stmt.setString(5, tax_slab);
                stmt.setString(6, status);
                stmt.executeUpdate();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
