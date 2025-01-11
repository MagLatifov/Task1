package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //реализуйте алгоритм здесь
        UserDao userDao = new UserDaoHibernateImpl();
        //UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        userDao.saveUser("Владислав", "Балякин", (byte) 22);
        userDao.saveUser("Данил", "Каримов", (byte) 22);
        userDao.saveUser("Анастасия", "Кубанская", (byte) 23);
        userDao.saveUser("Валерия", "Бубнова", (byte) 23);
        userDao.saveUser("Дмитрий", "Жаворонков", (byte) 23);
        List<User> users = userDao.getAllUsers();
        System.out.println("Cписок всех пользователей:");
        for (User user : users) {
            System.out.println(user.getId() + ": " + user.getName() + " " + user.getLastName() + ", возраст: " + user.getAge());
        }
        if (!users.isEmpty()) {
            for (int i = 3; i<=4;i++) {
                userDao.removeUserById(i);
            }
        }
        users = userDao.getAllUsers();
        System.out.println("Cписок всех оставшихся пользователей(сосисочная вечеринка):");
        for (User user : users) {
            System.out.println(user.getId() + ": " + user.getName() + " " + user.getLastName() + ", возраст: " + user.getAge());
        }
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
        System.out.println("Завершено выполнение \"main\"!");

    }
}
