# 環境建置

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
