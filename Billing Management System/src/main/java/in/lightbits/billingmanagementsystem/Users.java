package in.lightbits.billingmanagementsystem;

/*

This class has been created for saving te user to database
this will be for user for loging user

 */
public class Users {
    private int id;
    private String fullName;
    private String username;
    private String password;


    public Users(){
    }

    public Users(int id, String fullName, String username, String password) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", fullName='" + fullName + "', username='" + username + "'}";
    }


}
