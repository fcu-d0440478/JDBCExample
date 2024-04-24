package com.example;

import java.sql.Statement;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.DriverManager;

public class JDBCExample2 {
    public static void main(String[] args) {
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=Eric;password=password";
        String dbName = "TestDB";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            // 嘗試選擇資料庫，如果不存在，創建一個新的資料庫
            stmt.executeUpdate("""
                    IF NOT EXISTS(SELECT * FROM sys.databases WHERE name = 'TestDB')
                    BEGIN
                        CREATE DATABASE TestDB
                    END
                        """);

            String sql = "USE " + dbName;
            stmt.executeUpdate(sql);

            String sqlStatements = """
                        CREATE TABLE Student (
                            StudentId VARCHAR(10),
                            StudentName VARCHAR(10),
                            StudentSex VARCHAR(10)
                        );

                        INSERT INTO Student VALUES ('01', 'Aaron', 'M');
                        INSERT INTO Student VALUES ('02', 'Adam', 'M');
                        INSERT INTO Student VALUES ('03', 'Gavin', 'M');
                        INSERT INTO Student VALUES ('04', 'Harold', 'M');
                        INSERT INTO Student VALUES ('05', 'Amanda', 'F');
                        INSERT INTO Student VALUES ('06', 'Irene', 'F');
                        INSERT INTO Student VALUES ('07', 'Joa', 'F');
                        INSERT INTO Student VALUES ('09', 'Julia', 'F');
                        INSERT INTO Student VALUES ('10', 'Amelia', 'F');
                        INSERT INTO Student VALUES ('11', 'Annie', 'F');
                        INSERT INTO Student VALUES ('12', 'Lydia', 'F');
                        INSERT INTO Student VALUES ('13', 'Molly', 'F');

                        CREATE TABLE Course (
                            CourseId VARCHAR(10),
                            CourseName NVARCHAR(30),
                            TeacherId VARCHAR(10)
                        );

                        INSERT INTO Course VALUES ('01', 'relational database', '02');
                        INSERT INTO Course VALUES ('02', 'programming', '01');
                        INSERT INTO Course VALUES ('03', 'statistics', '03');

                        CREATE TABLE Teacher (
                            TeacherId VARCHAR(10),
                            TeacherName VARCHAR(10)
                        );

                        INSERT INTO Teacher VALUES ('01', 'Zora');
                        INSERT INTO Teacher VALUES ('02', 'Yvonne');
                        INSERT INTO Teacher VALUES ('03', 'Xaviera');
                        INSERT INTO Teacher VALUES ('04', 'Adam');
                        INSERT INTO Teacher VALUES ('05', 'Zora');

                        CREATE TABLE Score (
                            StudentId VARCHAR(10),
                            CourseId VARCHAR(10),
                            Score DECIMAL(18,1)
                        );

                        INSERT INTO Score VALUES ('01', '01', 80);
                        INSERT INTO Score VALUES ('01', '02', 90);
                        INSERT INTO Score VALUES ('01', '03', 99);
                        INSERT INTO Score VALUES ('02', '01', 70);
                        INSERT INTO Score VALUES ('02', '02', 60);
                        INSERT INTO Score VALUES ('02', '03', 80);
                        INSERT INTO Score VALUES ('03', '01', 80);
                        INSERT INTO Score VALUES ('03', '02', 80);
                        INSERT INTO Score VALUES ('03', '03', 80);
                        INSERT INTO Score VALUES ('04', '01', 50);
                        INSERT INTO Score VALUES ('04', '02', 30);
                        INSERT INTO Score VALUES ('04', '03', 20);
                        INSERT INTO Score VALUES ('05', '01', 76);
                        INSERT INTO Score VALUES ('05', '02', 87);
                        INSERT INTO Score VALUES ('06', '01', 31);
                        INSERT INTO Score VALUES ('06', '03', 34);
                        INSERT INTO Score VALUES ('07', '02', 89);
                        INSERT INTO Score VALUES ('07', '03', 98)
                    """;

            String[] sqlStatementsArray = sqlStatements.split(";");

            for (String sqlStatement : sqlStatementsArray) {
                stmt.addBatch(sqlStatement);
            }
            stmt.executeBatch();
            System.out.println("所有Table和範例資料都成功執行創建了。");

            System.out.println("\n使用SQL查詢各科成績前三名的記錄(包含學生姓名、科別名稱及該科成績)");
            sql = """
                    SELECT s.StudentName, c.CourseName, sc.Score
                    FROM (
                        SELECT StudentId, CourseId, Score,
                        RANK() OVER(PARTITION BY CourseId ORDER BY Score DESC) as rn
                        FROM Score
                    ) as sc
                    JOIN Student s ON s.StudentId = sc.StudentId
                    JOIN Course c ON c.CourseId = sc.CourseId
                    WHERE sc.rn <= 3
                    ORDER BY c.CourseName, sc.Score DESC;
                    """;

            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String studentName = rs.getString("StudentName");
                    String courseName = rs.getString("CourseName");
                    double score = rs.getDouble("Score");

                    System.out.printf("StudentName: %-10s CourseName: %-20s Score: %.2f%n", studentName, courseName,
                            score);
                }
            }

            System.out.println("\n使用SQL查詢,不分科別,由高到低,依成績排序表Score取5列結果");
            sql = """
                    SELECT TOP 5 s.StudentName, c.CourseName, sc.Score
                    FROM Score sc
                    JOIN Student s ON s.StudentId = sc.StudentId
                    JOIN Course c ON c.CourseId = sc.CourseId
                    ORDER BY sc.Score DESC;
                        """;
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String studentName = rs.getString("StudentName");
                    String courseName = rs.getString("CourseName");
                    double score = rs.getDouble("Score");

                    System.out.printf("StudentName: %-10s CourseName: %-20s Score: %.2f%n", studentName, courseName,
                            score);
                }
            }

            System.out.println("\n使用SQL計算出表Teacher中,老師名字的相異計數,並寫出值");
            sql = """
                    SELECT COUNT(DISTINCT TeacherName) as DistinctTeacherCount
                    FROM Teacher;
                        """;
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    int distinctTeacherCount = rs.getInt("DistinctTeacherCount");

                    System.out.println("DistinctTeacherCount: " + distinctTeacherCount);
                }
            }
        } catch (BatchUpdateException e) {
            System.out.println("一個或多個SQL語句執行失敗。");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
