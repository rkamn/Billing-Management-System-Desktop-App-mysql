package in.lightbits.billingmanagementsystem;

public class SessionManager {
    private static SessionManager instance;

    private String username;
    private String fullName;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    public static void setInstance(SessionManager newInstance) {
        instance = newInstance;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "SessionManager{" +
                "username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
