package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
//import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
//import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

/*Описание задачи:

Необходимо ознакомиться с заготовкой и доработать приложение, которое взаимодействует с базой оперируя пользователем ( класс User ) и проверить свои методы заранее написанными JUnit тестами. По итогу все тесты должны быть пройдены. Разрешается посмотреть реализацию тестов.

Для запуска теста необходимо найти класс в папке test ( показано в предыдущей лекции ) и при нажатии на него правой кнопкой мыши запустить, выбрав Run "Имя класса"

Класс UserHibernateDaoImpl в рамках этой задачи не затрагивается (остаётся пустой)

User представляет из себя сущность с полями:

  Long id

  String name

  String lastName

  Byte age

Архитектура приложения создана с опорой на паттерн проектирования MVC (частично, у нас не WEB приложение)

Требования к классам приложения:

1.Классы dao/service должны реализовывать соответствующие интерфейсы

2.Класс dao должен иметь конструктор пустой/по умолчанию

3.Все поля должны быть private

4.service переиспользует методы dao

5.Обработка всех исключений, связанных с работой с базой данных должна находиться в dao

6.Класс Util должен содержать логику настройки соединения с базой данных

Необходимые операции:

1.Создание таблицы для User(ов) – не должно приводить к исключению, если такая таблица уже существует

2.Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует

3. Очистка содержания таблицы

4. Добавление User в таблицу

5.Удаление User из таблицы ( по id )

6.Получение всех User(ов) из таблицы

Алгоритм работы приложения:

В методе main класса Main должны происходить следующие операции:

1.Создание таблицы User(ов)

2.Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )

3.Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)

4.Очистка таблицы User(ов)

5.Удаление таблицы
*/

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    private final static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
        userService.createUsersTable();
        User user1 = new User("Belek", "Salchak", (byte) 19);
        User user2 = new User("Tom", "Cruz", (byte) 25);
        User user3 = new User("Bred", "Pitt", (byte) 16);
        User user4 = new User("Ivan", "Ivanov", (byte) 26);
        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        List<User> userList = userService.getAllUsers();
        for(User user : userList){
            System.out.println(user.toString());
        }

        userService.removeUserById(3);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
