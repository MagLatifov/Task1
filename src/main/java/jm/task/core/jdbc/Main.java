package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;



public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Alex", "Ivanov", (byte) 22);

        userService.saveUser("Sara", "Miller", (byte) 55);

        userService.saveUser("Sam", "Trofimov", (byte) 6);

        userService.saveUser("Lena", "Sokolova", (byte) 18);

        for (User u : userService.getAllUsers()) {
            System.out.println(u.toString());
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
