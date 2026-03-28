package com.example.gardenapp;

import java.sql.*;
public class LoginDatabase {
    private static final String URL = "jdbc:sqlite:garden.db";
    private static Connection conn;

    public static void connect() {
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public static void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "email TEXT," +
                "password TEXT NOT NULL" +
                ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Users table ready!");
        } catch (SQLException e) {
            System.out.println("Create table failed: " + e.getMessage());
        }
    }

    public static boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // true if user exists
        } catch (SQLException e) {
            System.out.println("Login check failed: " + e.getMessage());
            return false;
        }
    }

    public static boolean addUser(String username, String email, String password) {
        String sql = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Add user failed: " + e.getMessage());
            return false;
        }
    }
}
