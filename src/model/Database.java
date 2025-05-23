package model;

import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cookingpapa?characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection koneksiDatabase() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found (Koneksi.java): " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection Error (Koneksi.java): " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
}