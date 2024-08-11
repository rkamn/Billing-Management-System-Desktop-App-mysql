package in.lightbits.billingmanagementsystem;

public class Products {
    private String name;
    private String price;
    private String description;
    private String quantity;
    private String taxRate;
    private String status;


    private int id;


    public Products(int id, String name, String price, String description, String quantity, String taxRate, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.taxRate = taxRate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTaxRate() {
        return taxRate;
    }
    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }
}
