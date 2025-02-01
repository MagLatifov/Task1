package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    private void executeTransaction(Runnable action) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            action.run();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при выпонении транзакции" + e.getMessage());
        }
    }

    @Override
    public void createUsersTable() {
        executeTransaction(() -> {
            Session session = HibernateUtil.getSession();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, name VARCHAR(255), lastname VARCHAR(255), age SMALLINT)").executeUpdate();
            System.out.println("Таблица создана");
        });
    }

    @Override
    public void dropUsersTable() {
        executeTransaction(() -> {
            Session session = HibernateUtil.getSession();
            session.createNativeQuery("DROP TABLE users").executeUpdate();
            System.out.println("Таблица удалена");
        });
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        executeTransaction(() -> {
            Session session = HibernateUtil.getSession();
            session.save(user);
            System.out.println("User с именем " + name + " добавлен в таблицу");
        });
    }

    @Override
    public void removeUserById(long id) {
        executeTransaction(() -> {
            Session session = HibernateUtil.getSession();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                System.out.println("User c id = " + id + "успешно удален");
            }
        });
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        executeTransaction(() -> {
            Session session = HibernateUtil.getSession();
            session.createNativeQuery("DELETE FROM users").executeUpdate();
            System.out.println("Таблица очищена");
        });
    }
}


