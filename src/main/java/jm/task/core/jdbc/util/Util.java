package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util {
    // реализуйте настройку соеденения с БД

    private final static String DB_URL = "db.url";
    private final static String DB_USERNAME = "db.username";
    private final static String DB_PASSWORD = "db.password";

    private Util() {
        throw new AssertionError("Создавать экземпляр класса Util нельзя!!!");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PropertiesUtil.getProp(DB_URL),
                PropertiesUtil.getProp(DB_USERNAME),
                PropertiesUtil.getProp(DB_PASSWORD));
    }
}
