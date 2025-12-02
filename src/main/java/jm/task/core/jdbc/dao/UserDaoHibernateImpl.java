package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserDaoHibernateImpl implements UserDao {
//    private static final Logger log = LoggerFactory.logger(UserDaoHibernateImpl.class);

//    public UserDaoHibernateImpl() { }

//    private boolean checkTable(SessionFactory sessionFactory, String tName) {
//        try (Session session = sessionFactory.openSession()) {
//            session.doReturningWork(connection -> {
//                DatabaseMetaData mData = connection.getMetaData();
//                try (ResultSet result = mData.getTables(null, "public", tName, null)) {
//                    return result.next();
//                }
//            });
//        } catch (Exception e) {
//           log.error("Ошибка при проверке существования таблицы " + tName, e);
//        }
//        return false;
//    }

    private final SessionFactory sessionFactory;

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        Session session = sessionFactory.getCurrentSession();
        try {
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
        Session session = sessionFactory.getCurrentSession();
        try {
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
        Session session = sessionFactory.getCurrentSession();
        try {
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
        Session session = sessionFactory.getCurrentSession();
        try {
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
        Session session = sessionFactory.getCurrentSession();
        try {
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User", User.class).getResultList();
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
        Session session = sessionFactory.getCurrentSession();
        try {
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
