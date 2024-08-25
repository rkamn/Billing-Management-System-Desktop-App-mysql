package in.lightbits.billingmanagementsystem;

public class Invoices {
    private int id;
    private int buyer_id;
    private String name;
    private String mobile;
    private String invoice;
    private String date;
    private double total;
    private double cgst;
    private double sgst;
    private double taxable_price;

    public Invoices(){}
    public Invoices(int id, int buyer_id, String name, String mobile, String invoice, String date, double total, double cgst, double sgst, double taxable_price) {
        this.id = id;
        this.buyer_id = buyer_id;
        this.name = name;
        this.mobile = mobile;
        this.invoice = invoice;
        this.date = date;
        this.total = total;
        this.cgst = cgst;
        this.sgst = sgst;
        this.taxable_price = taxable_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(int buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getCgst() {
        return cgst;
    }

    public void setCgst(double cgst) {
        this.cgst = cgst;
    }

    public double getSgst() {
        return sgst;
    }

    public void setSgst(double sgst) {
        this.sgst = sgst;
    }

    public double getTaxable_price() {
        return taxable_price;
    }

    public void setTaxable_price(double taxable_price) {
        this.taxable_price = taxable_price;
    }

    @Override
    public String toString() {
        return "Invoices{" +
                "id=" + id +
                ", buyer_id=" + buyer_id +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", invoice='" + invoice + '\'' +
                ", date='" + date + '\'' +
                ", total=" + total +
                ", cgst=" + cgst +
                ", sgst=" + sgst +
                ", taxable_price=" + taxable_price +
                '}';
    }
}
