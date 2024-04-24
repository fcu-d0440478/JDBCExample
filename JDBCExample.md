# JDBCExample

這個 Java 程式是一個使用 JDBC 連接到 SQL Server 的範例。

## 程式碼解釋

```Java
String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=TestDB;user=Eric;password=password;TrustServerCertificate=true";
```

首先，程式碼定義了一個連接字串 `connectionUrl`，用於指定資料庫伺服器的位置、資料庫名稱、使用者名稱、密碼，以及是否信任伺服器的 SSL 憑證。這段字串是一個用於連接 SQL Server 資料庫的 JDBC 連接字串。以下是各部分的解釋：
`jdbc:sqlserver://localhost:1433`：這部分指定了資料庫伺服器的位置和通訊埠。在這個例子中，資料庫伺服器位於本地主機（localhost），並且使用預設的 SQL Server 通訊埠 1433。
`databaseName=TestDB`：這部分指定了要連接的資料庫的名稱。在這個例子中，資料庫的名稱是 TestDB。
`user=Eric`：這部分指定了用於連接資料庫的使用者名稱。在這個例子中，使用者名稱是 Eric。
`password=password`：這部分指定了用於連接資料庫的密碼。在這個例子中，密碼是 password。
`TrustServerCertificate=true`：這部分指定了是否信任伺服器的 SSL 憑證。在這個例子中，設定為 true，表示客戶端將信任伺服器的 SSL 憑證。這可以用於測試或開發環境，但在生產環境中可能會帶來安全風險。
這個連接字串會被用於建立一個到 SQL Server 資料庫的連接，並且可以用於執行 SQL 查詢或更新資料庫。

然後，程式碼使用 `DriverManager.getConnection()` 方法來建立一個到資料庫的連接，並使用 `con.createStatement()` 方法來建立一個 SQL 語句物件。

接著，程式碼執行了以下的 SQL 操作：

1. **創建資料庫**：使用 `CREATE DATABASE` 語句來創建一個名為 `TestDB` 的資料庫。
2. **使用新創建的資料庫**：使用 `USE` 語句來切換到 `TestDB` 資料庫。
3. **創建表格**：使用 `CREATE TABLE` 語句來創建一個名為 `Employees` 的表格，這個表格有四個欄位：`id`、`name`、`position` 和 `salary`。
4. **插入範例資料**：使用 `INSERT INTO` 語句來插入一些範例資料到 `Employees` 表格。

最後，程式碼執行了一個參數化查詢，**可避免 SQL Injection**，並選擇所有薪水大於 69000 的員工，並將他們的資訊輸出到控制台。

如果在執行過程中發生任何錯誤，程式碼會捕獲 `SQLException` 異常，並將異常的堆疊蹤跡輸出到控制台。

## 其中，Try 的部分補充如下

在 Java 中，try() 語句中的括號被稱為資源宣告區域，用於宣告需要自動關閉的資源。在 try() 語句結束時，這些資源會被自動關閉，這是 Java 7 引入的 try-with-resources 語法。使用 try-with-resources 語法可以讓你的程式碼更簡潔，並且可以確保資源在不再需要時被正確地關閉。
