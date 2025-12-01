package jm.task.core.jdbc.util;

import org.hibernate.annotations.common.util.impl.LoggerFactory;

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

    //private final static SessionFactory sessionFactory;

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

}
