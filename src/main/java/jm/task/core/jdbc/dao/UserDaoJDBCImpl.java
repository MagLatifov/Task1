package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Logger log = LoggerFactory.logger(UserDaoJDBCImpl.class);

    public UserDaoJDBCImpl() { }

    private boolean checkTable(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet resultSet = metaData.getTables(null, "public", "users", null)) {
            return resultSet.next();
        }
    }

    public void createUsersTable() {
        try (Connection con = Util.getConnection();
            PreparedStatement stm = con.prepareStatement(SQLQuery.CREATE_TABLE)) {
                stm.execute();
                log.info("Таблица users успешно создана или она уже существовала...");
        } catch (SQLException s) {
            throw new RuntimeException(s);
        }
    }

    public void dropUsersTable() {
        try (Connection con = Util.getConnection();
            PreparedStatement stm = con.prepareStatement(SQLQuery.DROP_TABLE)) {
                stm.execute();
                log.info("Таблица users успешно удалена или ее не было...");
        } catch (SQLException s) {
            throw new RuntimeException(s);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection con = Util.getConnection();
             PreparedStatement stm = con.prepareStatement(SQLQuery.INSERT)) {
                stm.setString(1, name);
                stm.setString(2, lastName);
                stm.setByte(3, age);
                stm.executeUpdate();
                log.info("Успешно добавлена запись: " + name);
        } catch (SQLException s) {
            throw new RuntimeException(s);
        }
    }

    public void removeUserById(long id) {
        try (Connection con = Util.getConnection();
             PreparedStatement stm = con.prepareStatement(SQLQuery.DELETE)) {
                stm.setLong(1, id);
                if (stm.executeUpdate() > 0) {
                    log.info("Запись успешно удаоена: <id = " + id + ">");
                } else log.warn("Невозможно удвлить запись, так как нету в таблице users запись с <id = " + id + ">");
        } catch (SQLException s) {
            throw new RuntimeException(s);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection con = Util.getConnection();
             PreparedStatement stm = con.prepareStatement(SQLQuery.SELECT_ALL + "users")) {
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                User usr = new User();
                usr.setId(result.getLong("id"));
                usr.setName(result.getString("name"));
                usr.setLastName(result.getString("lastName"));
                usr.setAge(result.getByte("age"));
                users.add(usr);
            }
        } catch (SQLException s) {
            throw new RuntimeException(s);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection con = Util.getConnection();
             PreparedStatement stm = con.prepareStatement(SQLQuery.CLEAR_TABLE)) {
             if (checkTable(con)) {
                 stm.executeUpdate();
                 log.info("Все записи с таблицы users удалены и счетчик сброшен...");
             } else log.warn("Нет таблицы для очистки записей...");
        } catch (SQLException s) {
            throw new RuntimeException(s);
        }
    }
}
