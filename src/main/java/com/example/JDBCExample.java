package com.example;

import java.sql.*;

public class JDBCExample {
    public static void main(String[] args) {
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=TestDB;user=Eric;password=password;TrustServerCertificate=true";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            // 創建資料庫
            String sql = "CREATE DATABASE TestDB";
            stmt.executeUpdate(sql);

            // 使用新創建的資料庫
            sql = "USE TestDB";
            stmt.executeUpdate(sql);

            // 創建表格
            sql = "CREATE TABLE Employees (id INT IDENTITY(1,1) NOT NULL PRIMARY KEY, name NVARCHAR(50), position NVARCHAR(50), salary INT)";
            stmt.executeUpdate(sql);

            // 插入範例資料
            sql = "INSERT INTO Employees (name, position, salary) VALUES ('John Doe', 'Software Engineer', 70000), ('Jane Smith', 'Project Manager', 85000), ('Mike Johnson', 'QA Engineer', 65000)";
            stmt.executeUpdate(sql);

            // 執行參數化查詢
            sql = "SELECT * FROM Employees WHERE salary > ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, 69000);
                ResultSet rs = pstmt.executeQuery();

                // 輸出結果
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Position: "
                            + rs.getString("position") + ", Salary: " + rs.getInt("salary"));
                }
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
