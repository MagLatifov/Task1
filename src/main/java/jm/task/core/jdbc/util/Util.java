package jm.task.core.jdbc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:postgresql://localhost:5432/Task1";
    private static final String USERNAME = "postgres";
    private static  final  String PASSWORD = "sakura";

    static Logger logger = LoggerFactory.getLogger(Util.class);


    public static Connection getConnection(){

        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            logger.info("Connection to Task1 DB succeeded!");
        } catch (SQLException  | ClassNotFoundException e) {
            logger.info("Connection failed...");
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection!=null) {
            try {
                connection.close();
                logger.info("Connection to Task1 DB closed!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
