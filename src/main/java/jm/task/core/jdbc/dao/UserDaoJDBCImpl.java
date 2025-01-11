package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.QueryLoader;
import jm.task.core.jdbc.util.Util;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//import static jm.task.core.jdbc.util.Util.connection;
//import static jm.task.core.jdbc.util.Util.getConnection;
@Slf4j
public class UserDaoJDBCImpl implements UserDao {
    @SneakyThrows
    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {


            statement.executeUpdate(QueryLoader.getQuery("SQL_CREATE"));
            log.info("Табилца успешно создана");

        } catch (SQLException e) {
            log.error("Ошибка создания таблицы. Код ошибки:{}. SQL состояние:{}",
                    e.getErrorCode(),e.getSQLState());
            throw new SQLException("Ошибка выполнения SQL запроса!");
        }

    }

    @SneakyThrows
    public void dropUsersTable() {
        try (Connection connection =Util.getConnection();
             Statement statement =connection.createStatement()) {

            statement.executeUpdate(QueryLoader.getQuery("SQL_DROP"));
            log.info("Таблица успешно удалена");

        } catch (SQLException e) {
            log.error("Ошибка удаления таблицы.Код ошибки:{}. SQL состояние:{}",
                    e.getErrorCode(),e.getSQLState());
            throw new SQLException("Ошибка выполнения SQL запроса!");
        }

    }

    @SneakyThrows
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection =Util.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(QueryLoader.getQuery("SQL_SAVE"))){

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            log.info("Пользователь успешно сохранен");

        } catch (SQLException e) {
            log.error("Ошибка сохранения пользователя. Код ошибки{}. SQL состояние{}",
                    e.getErrorCode(),e.getSQLState());
            throw new SQLException("Ошибка выполнения SQL запроса!");
        }
    }

    @SneakyThrows
    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueryLoader.getQuery("SQL_REMOVE"))){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();



        } catch (SQLException e) {
            log.error("Ошибка удаления пользователя с ID{}.", id);
            throw new SQLException("Ошибка выполнения SQL запроса!");

        }

    }

    @SneakyThrows
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QueryLoader.getQuery("SQL_GET_ALL"));
             ResultSet resultSet = preparedStatement.executeQuery()){

            while (resultSet.next()){
                long id = resultSet.getLong("id");
                String name =resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");

                User user =new User( name,lastName, age);
                user.setId(id);
                users.add(user);
            }

        } catch (SQLException e) {
            log.error("Ошибка вывода пользователей. Код ошибки{},SQL состояние{}",
                    e.getErrorCode(),e.getSQLState());
            throw new SQLException("Ошибка выполнения SQL запроса!");

        }
        return users;
    }

    @SneakyThrows
    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement Statement =
                    connection.createStatement();

            Statement.executeUpdate(QueryLoader.getQuery("SQL_CLEAN"));
            log.info("Таблица успешно очищена.");

        } catch (SQLException e) {
            log.error("Ошибка очистки таблицы. Код ошибки{}. SQL состояние{}",
                    e.getErrorCode(),e.getSQLState());
            throw new SQLException("Ошибка выполнения SQL запроса!");

        }

    }
}
