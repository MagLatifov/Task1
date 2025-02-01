package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@NoArgsConstructor
public class UserDaoHibernateImpl implements UserDao {
    Properties requests = new Properties();
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/requests.properties"));
            requests.load(reader);
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден");
        } catch (IOException e) {
            System.err.println("Ошибка загрузки");
        }
    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSession();

        session.beginTransaction();
        session.createSQLQuery(requests.getProperty("createUsersTable")).addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSession();

        session.beginTransaction();
        session.createSQLQuery(requests.getProperty("dropUsersTable")).addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSession();

        session.beginTransaction();
        session.persist(new User(name, lastName, age));

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSession();

        session.beginTransaction();

        session.delete(session.get(User.class, id));

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSession();
        session.beginTransaction();
        List<User> users = session.createQuery(requests.getProperty("getAllUsers"), User.class).list();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSession();
        session.beginTransaction();
        session.createQuery(requests.getProperty("cleanUsersTable")).executeUpdate();
        session.close();
    }
}
