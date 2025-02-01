package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import org.postgresql.util.PSQLException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    public void createUsersTable() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "admin";
        String sql = """
                CREATE TABLE users (
                    id SERIAL PRIMARY KEY ,
                    name TEXT,
                    lastname TEXT,
                    age INT                    
                )
                """;
        try (var connection = DriverManager.getConnection(url, username, password);
             var preparedStatement = connection.prepareStatement(sql)){
            var result = preparedStatement.executeQuery();
            System.out.println(result);
            System.out.println("cre");
        }
        catch (SQLException e){
            System.out.println("cre");
        }
    }

    public void dropUsersTable() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "admin";
        String sql = """
                DROP TABLE users
                """;
        try (var connection = DriverManager.getConnection(url, username, password);
             var preparedStatement = connection.prepareStatement(sql)){
            var result = preparedStatement.executeQuery();
            System.out.println(result);
            System.out.println("del");
        }
        catch (SQLException e){
            System.out.println("del");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "admin";
        String sql = """
                INSERT INTO users (name, lastname, age)
                VALUES (? , ?, ?)
                """;
        try (var connection = DriverManager.getConnection(url, username, password);
             var preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            var result = preparedStatement.executeQuery();
            System.out.println(result);
            System.out.println("saved");
        }
        catch (SQLException e){
            System.out.println("saved");
        }
    }

    public void removeUserById(long id) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "admin";
        String sql = """
                DELETE FROM users WHERE id = ?
                """;
        try (var connection = DriverManager.getConnection(url, username, password);
             var preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            var result = preparedStatement.executeQuery();
            System.out.println("remId");
        }
        catch (SQLException e){
            System.out.println("remId");
        }
    }

    public List<User> getAllUsers() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "admin";
        String sql = """
                SELECT * FROM users
                """;
        List <User> ret = new ArrayList<>();
        try (var connection = DriverManager.getConnection(url, username, password);
             var preparedStatement = connection.prepareStatement(sql)){
            var result = preparedStatement.executeQuery();
            System.out.println("getted");
            while (result.next()){
                String name = result.getString("name");
                String lastname = result.getString("lastname");
                Byte age = result.getByte("age");
                User us = new User(name, lastname,age);
                ret.add(us);
            }
        }
        catch (SQLException e){
            System.out.println("del");
        }
        return ret;
    }

    public void cleanUsersTable() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "admin";
        String sql = """
                TRUNCATE TABLE users
                """;
        try (var connection = DriverManager.getConnection(url, username, password);
             var preparedStatement = connection.prepareStatement(sql)){
            var result = preparedStatement.executeQuery();
            System.out.println(result);
            System.out.println("trun");
        }
        catch (SQLException e){
            System.out.println("trun");
        }
    }
}
