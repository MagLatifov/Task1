package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        userServiceImpl.createUsersTable();

        userServiceImpl.saveUser("John", "Doe", (byte) 35);
        userServiceImpl.saveUser("Sara", "Conor", (byte) 40);
        userServiceImpl.saveUser("Stephan", "Mayer", (byte) 21);
        userServiceImpl.saveUser("Stew", "Romano", (byte) 25);

        userServiceImpl.getAllUsers().forEach(System.out::println);

        userServiceImpl.removeUserById(1);

        userServiceImpl.getAllUsers().forEach(System.out::println);

        userServiceImpl.cleanUsersTable();

        userServiceImpl.getAllUsers().forEach(System.out::println);

        userServiceImpl.dropUsersTable();


    }
}
