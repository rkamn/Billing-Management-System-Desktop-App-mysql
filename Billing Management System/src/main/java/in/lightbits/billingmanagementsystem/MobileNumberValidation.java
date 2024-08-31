package in.lightbits.billingmanagementsystem;

public class MobileNumberValidation {

    public boolean checkMobileValidity(String mobile){
        boolean status = false;
        if(Character.getNumericValue(mobile.charAt(0)) == 0 && mobile.length() == 11){
            status = true;
        }else if(mobile.substring(0,2) == "91"  && mobile.length() == 12){
            status= true;
            System.out.println("length of "+mobile+" is "+mobile.length());
        }else if( mobile.length() == 10  && Character.getNumericValue(mobile.charAt(0)) > 5){
            status= true;
        }else{
            System.out.println("First Character of "+mobile+" is invalid");
        }
        return status;
    }
}
