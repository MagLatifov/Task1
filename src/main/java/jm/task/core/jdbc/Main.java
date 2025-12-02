package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        // реализуйте алгоритм здесь

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
