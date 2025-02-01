package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//public class UserDaoJDBCImpl implements UserDao {
//    private static Connection connection = null;
//
//    static {
//        try {
//            connection = Util.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public UserDaoJDBCImpl() {
//
//    }
//
//    public void createUsersTable() {
//        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users" +
//                    "(id bigserial PRIMARY KEY, name VARCHAR(255), lastname VARCHAR(255), age smallint)");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void dropUsersTable() {
//        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate("DROP TABLE IF EXISTS Users");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void saveUser(String name, String lastName, byte age) {
//        try (PreparedStatement statement = connection.prepareStatement("insert into Users (name, lastname, age) values (?, ?, ?)")) {
//            statement.setString(1, name);
//            statement.setString(2, lastName);
//            statement.setByte(3, age);
//            statement.executeUpdate();
//            System.out.println("User с именем " + name + " добавлен в базу данных");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void removeUserById(long id) {
//        try (PreparedStatement statement = connection.prepareStatement("delete from Users where id" + (" = ?"))) {
//            statement.setLong(1, id);
//            statement.executeUpdate();
//            System.out.println("User с ID " + id + " удален из базы данных");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public List<User> getAllUsers() {
//        try (Statement statement = connection.createStatement()){
//            ResultSet set  = statement.executeQuery("select * from Users");
//            ArrayList<User> users = new ArrayList<>();
//            while (set.next()){
//                User user = new User(set.getString("name"), set.getString("lastname"), set.getByte("age"));
//                user.setId(set.getLong("id"));
//                users.add(user);
//            }
//            return users;
//        }
//        catch (SQLException e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public void cleanUsersTable() {
//        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate("TRUNCATE TABLE Users");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
