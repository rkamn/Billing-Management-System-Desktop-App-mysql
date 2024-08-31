package in.lightbits.billingmanagementsystem;

public class GSTTaxSlab {

    public GSTTaxSlab() {
    }

    public GSTTaxSlab(String tax) {
        this.tax = tax;
    }

    private String tax;

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    @Override
    public String toString() {
        return "GSTTaxSlab{" +
                "tax='" + tax + '\'' +
                '}';
    }
}
