package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    private static final Logger log = LoggerFactory.logger(Main.class);

    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserDao userDao = new UserDaoJDBCImpl();

        try (Connection con = Util.getConnection()) {
            System.out.println("Connected to database: " + con.getMetaData().getDatabaseProductName());
            System.out.println(" ---------- -------------- ---------------");
            userDao.createUsersTable();
            userDao.saveUser("Magomed", "Magomedovich", (byte)34);
            userDao.saveUser("Patimat", "Kalimatovna", (byte)56);
            userDao.saveUser("Ramazan", "Perbudagovich", (byte)45);
            System.out.println(" ---------- ----- Список User до удаления ------ ---------------");

            for (User user : userDao.getAllUsers()) {
                System.out.println(user);
            }

            userDao.removeUserById(2);
            System.out.println(" ---------- ----- Список User после удаления записи ------ ---------------");

            for (User user : userDao.getAllUsers()) {
                System.out.println(user);
            }

            userDao.cleanUsersTable();
            userDao.dropUsersTable();
        } catch (SQLException thrw) {
            log.error("Проищошла ошибка при соелинении с БД : ", thrw);
        }

    }
}
