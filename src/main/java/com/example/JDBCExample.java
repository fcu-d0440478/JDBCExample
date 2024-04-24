package com.example;

import java.sql.*;

public class JDBCExample {
    public static void main(String[] args) {
        /*
         * 這段字串是一個用於連接 SQL Server 資料庫的 JDBC 連接字串。以下是各部分的解釋：
         * jdbc:sqlserver://localhost:1433：這部分指定了資料庫伺服器的位置和通訊埠。在這個例子中，資料庫伺服器位於本地主機（
         * localhost），並且使用預設的 SQL Server 通訊埠 1433。
         * databaseName=TestDB：這部分指定了要連接的資料庫的名稱。在這個例子中，資料庫的名稱是 TestDB。
         * user=Eric：這部分指定了用於連接資料庫的使用者名稱。在這個例子中，使用者名稱是 Eric。
         * password=password：這部分指定了用於連接資料庫的密碼。在這個例子中，密碼是 password。
         * TrustServerCertificate=true：這部分指定了是否信任伺服器的 SSL 憑證。在這個例子中，設定為
         * true，表示客戶端將信任伺服器的 SSL 憑證。這可以用於測試或開發環境，但在生產環境中可能會帶來安全風險。
         * 這個連接字串會被用於建立一個到 SQL Server 資料庫的連接，並且可以用於執行 SQL 查詢或更新資料庫。
         */
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=TestDB;user=Eric;password=password;TrustServerCertificate=true";

        /*
         * 在 Java 中，try() 語句中的括號被稱為資源宣告區域，用於宣告需要自動關閉的資源。
         * 在 try() 語句結束時，這些資源會被自動關閉，這是 Java7 引入的 try-with-resources 語法。
         * 使用 try-with-resources 語法可以讓你的程式碼更簡潔，並且可以確保資源在不再需要時被正確地關閉。
         */
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
