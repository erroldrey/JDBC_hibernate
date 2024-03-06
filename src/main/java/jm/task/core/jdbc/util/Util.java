package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String PASSWORD_KEY = "db.password";
    private static final String USER_NAME_KEY = "db.username";
    private static final String URL_KEY = "db.url";

    private Util() {

    }

    public static Connection getJDBC() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.getKey(URL_KEY),
                    PropertiesUtil.getKey(USER_NAME_KEY),
                    PropertiesUtil.getKey(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getHibernate() {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(User.class);
            return configuration.buildSessionFactory();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }
}
