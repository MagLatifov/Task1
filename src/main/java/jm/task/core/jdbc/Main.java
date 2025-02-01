package jm.task.core.jdbc;

import org.postgresql.Driver;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "admin";
        try (var connection = DriverManager.getConnection(url, username, password);
            var statement = connection.createStatement()){

        }
    }
}
