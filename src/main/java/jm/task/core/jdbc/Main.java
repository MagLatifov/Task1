package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDao userdao = new UserDaoJDBCImpl();
        UserService userService = new UserServiceImpl(userdao);

        try (Connection con = Util.getConnection()) {
            log.info("Connected to database: " + con.getMetaData().getDatabaseProductName());
            log.info(" ---------- -------------- ---------------");
            userService.createUsersTable();
            userService.saveUser("Magomed", "Magomedovich", (byte)34);
            userService.saveUser("Patimat", "Kalimatovna", (byte)56);
            userService.saveUser("Ramazan", "Perbudagovich", (byte)45);
            log.info(" ---------- ----- Список User до удаления ------ ---------------");

            printList(userService.getAllUsers());

            userService.removeUserById(2);
            log.info(" ---------- ----- Список User после удаления записи ------ ---------------");

            printList(userService.getAllUsers());

            userService.cleanUsersTable();
            userService.dropUsersTable();
        } catch (SQLException thrw) {
            log.error("Произошла ошибка при соединение с БД : ", thrw);
        }

    }

    public static void printList(List<User> usr) {
        int i = 1;
        for (User user : usr) {
            log.info("Запись #{}: {}", i, user);
            i++;
        }
    }
}
