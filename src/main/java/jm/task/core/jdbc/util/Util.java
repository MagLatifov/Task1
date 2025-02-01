package jm.task.core.jdbc.util;

import java.io.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {
    // реализуйте настройку соеденения с БД
    Properties props = new Properties();
    @Getter
    Connection connection;
    static SessionFactory sessionFactory;

    public Util() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/database.properties"));
        props.load(reader);
        connection = DriverManager.getConnection(props.getProperty("url"),
                                                 props.getProperty("username"),
                                                 props.getProperty("password"));
    }

    public static Session getSession() {
        if (sessionFactory == null){
            Configuration configuration = new Configuration().addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory.getCurrentSession();
    }

}
