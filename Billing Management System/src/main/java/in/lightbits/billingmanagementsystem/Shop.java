package in.lightbits.billingmanagementsystem;

public class Shop {
    private int shopId;
    private String shopGST;
    private String shopName;
    private String shopAddress;
    private String shopMobile;
    private String shopPin;
    private String shopOwner;
    private  String ShopEmail;

    public Shop(){};

    public Shop(int shopId, String shopGST, String shopName, String shopAddress, String shopMobile, String shopPin, String shopOwner, String ShopEmail) {
        this.shopId = shopId;
        this.shopGST = shopGST;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopMobile = shopMobile;
        this.shopPin = shopPin;
        this.shopOwner = shopOwner;
        this.ShopEmail=ShopEmail;
    }

    public int getShopId() {
        return shopId;
    }

    public String getShopGST() {
        return shopGST;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public String getShopMobile() {
        return shopMobile;
    }

    public String getShopPin() {
        return shopPin;
    }

    public String getShopOwner() {
        return shopOwner;
    }

    public String getShopEmail() {
        return ShopEmail;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public void setShopGST(String shopGST) {
        this.shopGST = shopGST;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public void setShopMobile(String shopMobile) {
        this.shopMobile = shopMobile;
    }

    public void setShopPin(String shopPin) {
        this.shopPin = shopPin;
    }

    public void setShopOwner(String shopOwner) {
        this.shopOwner = shopOwner;
    }

    public void setShopEmail(String shopEmail) {
        ShopEmail = shopEmail;
    }
}
