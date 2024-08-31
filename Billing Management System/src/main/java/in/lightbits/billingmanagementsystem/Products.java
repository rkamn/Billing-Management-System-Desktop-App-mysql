package in.lightbits.billingmanagementsystem;

public class Products {
    private String name;
    private String price;
    private String description;
    private String quantity;
    private String taxRate;
    private String HSN;
    private String status;


    private int id;

    public Products(){}

    public Products(int id, String name, String price, String description, String quantity, String taxRate,String HSN, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.taxRate = taxRate;
        this.HSN = HSN;
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

    public String getHSN() {
        return HSN;
    }

    public String getStatus() {
        return status;
    }

    public float getTotalPriceOfOneProduct(){
        float p = Float.parseFloat(price);
        float q = Float.parseFloat(quantity);
        return p*q;
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

    public void setHSN(String HSN) {
        this.HSN = HSN;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return name; // Display product name
    }
}
