# JDBCExample

這個 Java 程式是一個使用 JDBC 連接到 SQL Server 的範例。

## 程式碼解釋

`String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=TestDB;user=Eric;password=password;TrustServerCertificate=true";`

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

### 其中，Try 的部分補充如下

在 Java 中，try() 語句中的括號被稱為資源宣告區域，用於宣告需要自動關閉的資源。在 try() 語句結束時，這些資源會被自動關閉，這是 Java 7 引入的 try-with-resources 語法。使用 try-with-resources 語法可以讓你的程式碼更簡潔，並且可以確保資源在不再需要時被正確地關閉。

# JDBCExample2

這個 Java 程式碼示範了如何使用 Java 的 JDBC（Java Database Connectivity）API 來與 SQL Server 資料庫進行互動。以下是這段程式碼的主要功能：

1. **建立資料庫連接**：程式碼首先建立一個到 SQL Server 資料庫的連接。連接的 URL、使用者名稱和密碼都在程式碼中指定。

2. **創建資料庫**：如果指定的資料庫不存在，程式碼會創建一個新的資料庫。

3. **創建表格並插入資料**：程式碼會創建四個表格（Student, Course, Teacher, Score），並插入一些範例資料。

4. **執行 SQL 查詢**：程式碼會執行幾個 SQL 查詢，包括查詢各科成績前三名的記錄、不分科別由高到低依成績排序的前五名學生，以及計算出 Teacher 表中老師名字的相異計數。

這個程式碼是一個很好的範例，展示了如何使用 Java 的 JDBC API 來與資料庫進行互動，包括創建資料庫、創建表格、插入資料，以及執行 SQL 查詢。

# 資料庫環境建置

1.先去下載 MSSQL Express 2022 版本
https://www.microsoft.com/zh-tw/sql-server/sql-server-downloads

2.再來下載 SQL Server Management Studio (SSMS)
https://learn.microsoft.com/zh-tw/sql/ssms/download-sql-server-management-studio-ssms?view=sql-server-ver16

3.挑整設定，開啟 SSMS(SQL Server Management Studio)。用 windows 登入後右鍵資料庫的登入項，然後新增登入(新增使用者)

![image](https://github.com/fcu-d0440478/JDBCExample/blob/master/picture_file/%E5%9C%961.png)

4.開啟 SQL Server 認證，並輸入登入名稱(使用者名稱，這裡為 Eric)
![image](https://github.com/fcu-d0440478/JDBCExample/blob/master/picture_file/%E5%9C%962.png)

5.選到伺服器角色，勾選 sysadmin，這樣等等就能夠 Create Database。
![image](https://github.com/fcu-d0440478/JDBCExample/blob/master/picture_file/%E5%9C%963.png)

6.接著右鍵資料庫屬性，到安全性下的標籤，在勾選伺服器驗證那裡選擇 SQL Server 及 Windows 驗證模式
![image](https://github.com/fcu-d0440478/JDBCExample/blob/master/picture_file/%E5%9C%964.png)
![image](https://github.com/fcu-d0440478/JDBCExample/blob/master/picture_file/%E5%9C%965.png)

7.到 windows 搜尋 SQL Server 2022 設定管理員
![image](https://github.com/fcu-d0440478/JDBCExample/blob/master/picture_file/%E5%9C%966.png)

8.在 SQL Server 網路組態下的 MSSQLSERVER 的通訊協定，將 TCP/IP 右鍵內容
![image](https://github.com/fcu-d0440478/JDBCExample/blob/master/picture_file/%E5%9C%967.png)

將 IP/ALL 的頁籤下 TCP 通訊埠設定為 1433

![image](https://github.com/fcu-d0440478/JDBCExample/blob/master/picture_file/%E5%9C%968.png)

最後到 SQL Server 服務將 SQL Server 右鍵重新啟動就完成資料庫的設定了
![image](https://github.com/fcu-d0440478/JDBCExample/blob/master/picture_file/%E5%9C%969.png)
