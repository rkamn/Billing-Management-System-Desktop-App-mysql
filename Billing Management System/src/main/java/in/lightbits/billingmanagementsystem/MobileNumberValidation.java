package in.lightbits.billingmanagementsystem;

public class MobileNumberValidation {
    private String mobile;

    public MobileNumberValidation() {
    }
    public MobileNumberValidation(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean checkMobileValidity(){
        boolean status = false;
        if(mobile.length() >= 10 && mobile.length() <= 12 && checkFirstCharacterOfMobile()){
            status= true;
            System.out.println("length of "+mobile+" is "+mobile.length());
        }
        return status;
    }

    public boolean checkFirstCharacterOfMobile(){
        boolean status = false;
        if(mobile.charAt(0) !='0'|| mobile.charAt(0) !='6' || mobile.charAt(0) !='7' || mobile.charAt(0) !='8' || mobile.charAt(0) !='9'){
            status= true;
        }else{
            System.out.println("First Character of "+mobile+" is invalid");
        }
        return status;
    }


}
