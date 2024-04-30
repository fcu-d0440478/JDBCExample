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
            stmt.executeUpdate("IF NOT EXISTS(SELECT * FROM sys.databases WHERE name = '" + dbName
                    + "') CREATE DATABASE " + dbName);
            stmt.executeUpdate("USE " + dbName);

            String[] tables = { "Student", "Course", "Teacher", "Score" };
            for (String table : tables) {
                ResultSet rs = stmt
                        .executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = '" + table + "'");
                if (!rs.next()) {
                    stmt.addBatch(getCreateTableSQL(table));
                    stmt.addBatch(getInsertDataSQL(table));
                }
            }
            stmt.executeBatch();

            printQueryResult(stmt, "\n使用SQL查詢各科成績前三名的記錄(包含學生姓名、科別名稱及該科成績)", getTopThreeScoresSQL());
            printQueryResult(stmt, "\n使用SQL查詢,不分科別,由高到低,依成績排序表Score取5列結果", getTopFiveScoresSQL());
            System.out.println("\n使用SQL計算出表Teacher中,老師名字的相異計數,並寫出值");
            String sql = getDistinctTeacherCountSQL();
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    int distinctTeacherCount = rs.getInt(1);
                    System.out.println("DistinctTeacherCount: " + distinctTeacherCount);
                }
            }

        } catch (BatchUpdateException e) {
            System.out.println("一個或多個SQL語句執行失敗。");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void printQueryResult(Statement stmt, String message, String sql) throws SQLException {
        System.out.println(message);
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String studentName = rs.getString("StudentName");
                String courseName = rs.getString("CourseName");
                double score = rs.getDouble("Score");
                System.out.printf("StudentName: %-10s CourseName: %-20s Score: %.2f%n", studentName, courseName, score);
            }
        }
    }

    private static String getDistinctTeacherCountSQL() {
        return "SELECT COUNT(DISTINCT TeacherName) as DistinctTeacherCount FROM Teacher";
    }

    private static String getTopFiveScoresSQL() {
        return """
                    SELECT TOP 5 s.StudentName, c.CourseName, sc.Score
                    FROM Score sc
                    JOIN Student s ON s.StudentId = sc.StudentId
                    JOIN Course c ON c.CourseId = sc.CourseId
                    ORDER BY sc.Score DESC;
                """;
    }

    private static String getTopThreeScoresSQL() {
        return """
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
    }

    private static String getInsertDataSQL(String table) {
        switch (table) {
            case "Student":
                return """
                        INSERT INTO Student VALUES
                        ('01', 'Aaron', 'M'),
                        ('02', 'Adam', 'M'),
                        ('03', 'Gavin', 'M'),
                        ('04', 'Harold', 'M'),
                        ('05', 'Amanda', 'F'),
                        ('06', 'Irene', 'F'),
                        ('07', 'Joa', 'F'),
                        ('09', 'Julia', 'F'),
                        ('10', 'Amelia', 'F'),
                        ('11', 'Annie', 'F'),
                        ('12', 'Lydia', 'F'),
                        ('13', 'Molly', 'F');
                        """;
            case "Course":
                return """
                        INSERT INTO Course VALUES
                        ('01', 'relational database', '02'),
                        ('02', 'programming', '01'),
                        ('03', 'statistics', '03');
                        """;
            case "Teacher":
                return """
                        INSERT INTO Teacher VALUES
                        ('01', 'Zora'),
                        ('02', 'Yvonne'),
                        ('03', 'Xaviera'),
                        ('04', 'Adam'),
                        ('05', 'Zora');
                        """;
            case "Score":
                return """
                        INSERT INTO Score VALUES
                        ('01', '01', 80),
                        ('01', '02', 90),
                        ('01', '03', 99),
                        ('02', '01', 70),
                        ('02', '02', 60),
                        ('02', '03', 80),
                        ('03', '01', 80),
                        ('03', '02', 80),
                        ('03', '03', 80),
                        ('04', '01', 50),
                        ('04', '02', 30),
                        ('04', '03', 20),
                        ('05', '01', 76),
                        ('05', '02', 87),
                        ('06', '01', 31),
                        ('06', '03', 34),
                        ('07', '02', 89),
                        ('07', '03', 98);
                        """;
            default:
                return "";
        }
    }

    private static String getCreateTableSQL(String table) {
        switch (table) {
            case "Student":
                return "CREATE TABLE Student (StudentId VARCHAR(10), StudentName VARCHAR(10), StudentSex VARCHAR(10))";
            case "Course":
                return "CREATE TABLE Course (CourseId VARCHAR(10), CourseName NVARCHAR(30), TeacherId VARCHAR(10))";
            case "Teacher":
                return "CREATE TABLE Teacher (TeacherId VARCHAR(10), TeacherName VARCHAR(10))";
            case "Score":
                return "CREATE TABLE Score (StudentId VARCHAR(10), CourseId VARCHAR(10), Score DECIMAL(18,1))";
            default:
                return "";
        }
    }
}
