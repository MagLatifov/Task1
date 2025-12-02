package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserDaoHibernateImpl implements UserDao {

    private static final int MAX_USERS_GET_ROW = 1000;

    private final SessionFactory sessionFactory;

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(SQLQuery.CREATE_TABLE).executeUpdate();
            transaction.commit();
            log.info("Таблица users успешно создана или она существовала... ");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Произошла ошибка при создании табдицы users.\\n{}", e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(SQLQuery.DROP_TABLE).executeUpdate();
            transaction.commit();
            System.out.println("Таблица users успешно Удалена или она существовала... ");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Произошла ошибка при удалении таблицы users.\\n{}", e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = User.builder()
                            .name(name)
                            .lastName(lastName)
                            .age(age)
                        .build();
            session.save(user);
            transaction.commit();
            log.warn("Запись успешно создана: {}", name);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Произошла ошибка при создании записи.\\n{}", e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            int countDelRec = session.createNativeQuery(SQLQuery.DELETE)
                                    .setParameter(1, id)
                                    .executeUpdate();
            if (countDelRec > 0) {
                log.info("Запись успешно удалена <ID>: {}", id);
            } else {
                log.info("Нет записи в таблице с <ID>: {}", id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Произошла ошибка при удалении записи.\\n{}", e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User", User.class)
                    .setMaxResults(MAX_USERS_GET_ROW)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Произошла ошибка при получении записей из таблицы users.\\n{}", e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(SQLQuery.CLEAR_TABLE).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Произошла ошибка при очистки таблицы users.\\n{}", e.getMessage());
        }
    }
}
