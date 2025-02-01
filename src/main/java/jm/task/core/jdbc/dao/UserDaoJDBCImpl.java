package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = Util.getInstance().getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY ,
                    name TEXT,
                    lastname TEXT,
                    age INT                    
                )
                """;
        try (var preparedStatement = connection.createStatement()){
            preparedStatement.executeUpdate(sql);
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public void dropUsersTable() {
        String sql = """
                DROP TABLE IF EXISTS users
                """;
        try (var preparedStatement = connection.createStatement()){
            preparedStatement.executeUpdate(sql);
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = """
                INSERT INTO users (name, lastname, age)
                VALUES (? , ?, ?)
                """;
        try (var preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public void removeUserById(long id) {
        String sql = """
                DELETE FROM users WHERE id = ?
                """;
        try (var preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public List<User> getAllUsers() {
        String sql = """
                SELECT * FROM users
                """;
        List <User> ret = new ArrayList<>();
        try (var preparedStatement = connection.createStatement()){
            ResultSet result = preparedStatement.executeQuery(sql);
            while (result.next()){
                String name = result.getString("name");
                String lastname = result.getString("lastname");
                Byte age = result.getByte("age");
                User us = new User(name, lastname,age);
                ret.add(us);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return ret;
    }

    public void cleanUsersTable() {
        String sql = """
                TRUNCATE TABLE users
                """;
        try (var preparedStatement = connection.createStatement()){
            preparedStatement.executeUpdate(sql);
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }
}
