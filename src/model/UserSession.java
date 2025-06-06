package model;

public class UserSession {
    private static UserSession instance;
    private int userId;
    private String username;
    private String nama;
    
    private UserSession() {
        // Private constructor to prevent instantiation
    }
    
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public String getNama() {
        return nama;
    }
    
    public void clearSession() {
        this.userId = 0;
        this.username = null;
        this.nama = null;
    }
} 