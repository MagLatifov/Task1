package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.SQLException;
@Slf4j
public class Main {

    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        /*UserService userService = new UserServiceImpl(new UserDaoJDBCImpl());

        try (Connection con = Util.getConnection()) {
            System.out.println("Connected to database: " + con.getMetaData().getDatabaseProductName());
            System.out.println(" ---------- -------------- ---------------");
            userService.createUsersTable();
            userService.saveUser("Magomed", "Magomedovich", (byte)34);
            userService.saveUser("Patimat", "Kalimatovna", (byte)56);
            userService.saveUser("Ramazan", "Perbudagovich", (byte)45);
            System.out.println(" ---------- ----- Список User до удаления ------ ---------------");

            for (User user : userService.getAllUsers()) {
                System.out.println(user);
            }

            userService.removeUserById(2);
            System.out.println(" ---------- ----- Список User после удаления записи ------ ---------------");

            for (User user : userService.getAllUsers()) {
                System.out.println(user);
            }

            userService.cleanUsersTable();
            userService.dropUsersTable();
        } catch (SQLException thrw) {
            log.error("Проищошла ошибка при соелинении с БД : ", thrw);
        }*/

        //----- Hibernate ----------
        UserService userService = new UserServiceImpl(new UserDaoHibernateImpl(Util.getSessionFactory()));
        userService.createUsersTable();
        userService.saveUser("Magomed", "Magomedovich", (byte)34);
        userService.saveUser("Patimat", "Kalimatovna", (byte)56);
        userService.saveUser("Ramazan", "Perbudagovich", (byte)45);
        System.out.println(userService.getAllUsers());
        userService.removeUserById(3);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.shutDownSessionFactory();

    }
}
