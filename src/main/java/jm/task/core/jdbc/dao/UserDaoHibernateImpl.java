package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.Query;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
@Slf4j
public class UserDaoHibernateImpl implements UserDao {
    @Override
    public void createUsersTable() {
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            session.createNativeQuery(SQLQueries.SQLCreate).executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("Ошибка создания таблицы: " + e.getMessage() );
        }
        log.info("Таблица пользователей успешно создана");
    }

    @Override
    public void dropUsersTable() {
        try(Session session =Util.getSession()) {
            session.beginTransaction();
            session.createNativeQuery(SQLQueries.SQLDrop).executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("Ошибка удаления таблицы: "+ e.getMessage());
        }
        log.info("Таблица пользователей успешно удалена!");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }catch (Exception e) {
            log.error("Ошибка при сохранении пользователя: " + e.getMessage());
        }
        log.info("User с именем {} добавлен в таблицу",name);
    }

    @Override
    public void removeUserById(long id) {
        User user = new User();
        user.setId(id);
        try(Session session= Util.getSession()){
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("Ошибка удаления пользователя");

        }
        log.info("Пользователь c ID {} удален!",id );

    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            session.createQuery("delete  from User").executeUpdate();
            session.getTransaction().commit();
            log.info("Таблица очищена");
        }

    }
}
