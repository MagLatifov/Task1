package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.common.util.impl.LoggerFactory;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.jboss.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util {
    // реализуйте настройку соеденения с БД

    private final static Logger log = LoggerFactory.logger(Util.class);

    private final static String DB_URL = "db.url";
    private final static String DB_USERNAME = "db.username";
    private final static String DB_PASSWORD = "db.password";

    @Getter
    private final static SessionFactory sessionFactory = createSessionFactory();

    private Util() {
        throw new AssertionError("Создавать экземпляр класса Util нельзя!!!");
    }

    //соединения по JDBC
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PropertiesUtil.getProp(DB_URL),
                PropertiesUtil.getProp(DB_USERNAME),
                PropertiesUtil.getProp(DB_PASSWORD));
    }

    // соединение к БД через Hibernate
    public static SessionFactory createSessionFactory() {
           try {
               Configuration configuration = new Configuration().configure();
               configuration.addAnnotatedClass(User.class);
               StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                       .applySettings(configuration.getProperties());
               SessionFactory sFactory = configuration.buildSessionFactory(builder.build());
               log.info("Соединение с БД через Hibernate прошло успешно.");
               return sFactory;
           } catch (Exception e) {
               log.error("Произошла ошибка при соединении через Hibernate.", e);
               throw new ExceptionInInitializerError("Ошибка при создании SessionFactory");
           }
    }

    //закрытие фабрики сессий
    public static void shutDownSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            log.info("Соединение с БД через Hibernate полностью закрыто.");
        }
    }
}