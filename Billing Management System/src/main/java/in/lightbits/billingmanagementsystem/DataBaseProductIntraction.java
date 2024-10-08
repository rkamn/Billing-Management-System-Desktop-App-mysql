package in.lightbits.billingmanagementsystem;

import javafx.scene.control.Alert;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseProductIntraction {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/billing_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rakesh458458";

    CustomUtility customUtility = new CustomUtility();

    public Products getProductById(String productId){
        Products product = new Products();

        String shopQuery = "select * from billing_system.products where id = ? ";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");


            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement statement = con.prepareStatement(shopQuery)) {

                 con.setAutoCommit(false);

                statement.setString(1, productId);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    System.out.println("ID : " + id);

                    product.setName(resultSet.getString("name"));
                    product.setQuantity(resultSet.getString("quantity"));
                    product.setStatus(resultSet.getString("status"));

                    System.out.println("Product Name : " + product.getName());
                    System.out.println("Product Quantity : " + product.getQuantity());
                    System.out.println("Product Status : " + product.getStatus());

                }
                con.commit();
            }
        } catch (SQLException | ClassNotFoundException e) {

            e.printStackTrace();
            customUtility.showAlertActionStatus(Alert.AlertType.ERROR, "Database Error", "An error occurred while fetching data.");
        }
        return product;

    }



    public List<Products> searchProductByName(String searchBoxName) {
        List<Products> productsList = new ArrayList<>();

        String sqlProductSelectQuery = "select * from billing_system.products where name = ? ";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement statement = con.prepareStatement(sqlProductSelectQuery)) {

                statement.setString(1, searchBoxName);
                ResultSet resultSet = statement.executeQuery();
                //updateProductController.getDataFromResultSet(resultSet);

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");

                    //buyerName.setText(resultSet.getString("name"));
                    String name = resultSet.getString("name");
                    String price = resultSet.getString("price");
                    String desc = resultSet.getString("description");
                    String quantity = resultSet.getString("quantity");
                    String taxRate = resultSet.getString("tax_slab");
                    String HSN = resultSet.getString("HSN");
                    String status = resultSet.getString("status");

                    productsList.add(new Products(id, name, price, desc, quantity, taxRate, HSN, status));

                    System.out.println("ID : " + id);
                    System.out.println("Name : " + name);
                    System.out.println("Price : " + price);
                    System.out.println("Description : " + desc);
                    System.out.println("Quantity : " + quantity);
                    System.out.println("Tax Slab : " + taxRate);
                    System.out.println("HSN : " + HSN);
                    System.out.println("Status : " + status);

                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return productsList;
    }

    //get all products detail
    public List<Products> getAllProducts() {
        List<Products> products = new ArrayList<>();
        String getAllProductsQuery = "SELECT * FROM billing_system.products";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(getAllProductsQuery)) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    System.out.println(id);
                    String name = rs.getString("name");
                    String price = rs.getString("price");
                    String description = rs.getString("description");
                    String quantity = rs.getString("quantity");
                    String taxRate = rs.getString("tax_slab");
                    String HSNNo = rs.getString("HSN");
                    String status = rs.getString("status");

                    products.add(new Products(id, name, price, description, quantity, taxRate, HSNNo ,status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    //update existing product by id
    public boolean updateProductById(int id, String name, String price, String description, String quantity, String tax_slab,String HSN, String status) {
        String updateProductQuery = "UPDATE billing_system.products SET name = ?, price = ?, description = ?,quantity = ?, tax_slab = ?,HSN = ?,status = ? WHERE id = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(updateProductQuery)) {

                stmt.setString(1, name);
                stmt.setString(2, price);
                stmt.setString(3, description);
                stmt.setString(4, quantity);
                stmt.setString(5, tax_slab);
                stmt.setString(6, HSN);
                stmt.setString(7, status);
                stmt.setInt(8, id);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //  return  false;
        }
        return true;
    }


    public  void updateQuantityOfProductInDatabase(String id, int quantity, String productStatus) {
        String updateQuantityQuery = "UPDATE billing_system.products SET quantity = ?, status =? WHERE id = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(updateQuantityQuery)) {

                    stmt.setInt(1, quantity);
                    stmt.setString(2,productStatus);
                    stmt.setString(3, id);
                    stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        //Delete existing product by id
        public boolean deleteProductById ( int id, String name){
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
    public void insertNewProductData(String name, String price, String description, String quantity, String tax_slab,String HSN, String status) {
        String insertNewProductSQLQuery =   "INSERT INTO billing_system.products ( name,price,description,quantity,tax_slab,HSN,status) values (?,?,?,?,?,?,?)";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(insertNewProductSQLQuery)) {
                stmt.setString(1, name);
                stmt.setString(2, price);
                stmt.setString(3, description);
                stmt.setString(4, quantity);
                stmt.setString(5, tax_slab);
                stmt.setString(6, HSN);
                stmt.setString(7, status);
                stmt.executeUpdate();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
