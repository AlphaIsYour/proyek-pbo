package model;

import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cookingpapa?characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection koneksiDatabase() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Database connected successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found (Database.java): " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection Error (Database.java): " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        }
        return conn;
    }
}