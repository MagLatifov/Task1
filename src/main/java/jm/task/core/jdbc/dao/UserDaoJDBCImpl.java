package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Util util;

    public UserDaoJDBCImpl() {
        try {
            util = new Util();
        } catch (SQLException e) {
            System.err.println("Не удалось подключиться к базе данных");
        } catch (IOException e) {
            System.err.println("Не удалось считать данные для подключения к базе данных");
            throw new RuntimeException();
        }
    }

    public void createUsersTable() {
        try (Statement statement  = util.getConnection().createStatement()){
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS Users(" +
                    "id BIGSERIAL NOT NULL PRIMARY KEY," +
                    "name VARCHAR(30) NOT NULL," +
                    "lastname VARCHAR(30) NOT NULL," +
                    "age INTEGER NOT NULL" +
                    ");");
        } catch (SQLException e) {
            for (StackTraceElement el : e.getStackTrace()) {
                System.err.println(el);
            }
        }
    }

    public void dropUsersTable() {
        try (Statement statement  = util.getConnection().createStatement()){
            statement.execute("DROP TABLE IF EXISTS Users;");
        } catch (SQLException e) {
            for (StackTraceElement el : e.getStackTrace()) {
                System.err.println(el);
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = util.getConnection().prepareStatement(
                "INSERT INTO Users (name, lastname, age) VALUES ((?), (?), (?));"
        )){
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            for (StackTraceElement el : e.getStackTrace()) {
                System.err.println(el);
            }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = util.getConnection().prepareStatement(
                "DELETE FROM Users WHERE id = (?);"
        )) {
            statement.setLong(1, id);
            statement.execute();
            System.out.println("User с id – " + id + " удалён из базы данных");
        } catch (SQLException e) {
            for (StackTraceElement el : e.getStackTrace()) {
                System.err.println(el);
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = util.getConnection().prepareStatement(
                "SELECT * FROM Users;"
        )) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("lastname"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            for (StackTraceElement el : e.getStackTrace()) {
                System.err.println(el);
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.execute("DELETE FROM Users");
        } catch (SQLException e) {
            for (StackTraceElement el : e.getStackTrace()) {
                System.err.println(el);
            }
        }
    }
}
