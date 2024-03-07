package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String PASSWORD_KEY = "db.password";
    private static final String USER_NAME_KEY = "db.username";
    private static final String URL_KEY = "db.url";

    private static Connection connect;

    private static SessionFactory factory;

    private Util() {
    }

    public static SessionFactory getFactory() {
        if (factory == null) createFactory();
        return factory;
    }

    private static void createFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting(Environment.URL, "jdbc:postgresql://localhost:5432/postgres")
                .applySetting(Environment.DRIVER, "org.postgresql.Driver")
                .applySetting(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect")
                .applySetting(Environment.USER, "postgres")
                .applySetting(Environment.PASS, "1234")
                .applySetting(Environment.HBM2DDL_CHARSET_NAME, "none")
                .applySetting(Environment.SHOW_SQL, "true")
                .applySetting(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread")
                .build();

        Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClass(User.class)
                .buildMetadata();

        factory = metadata.buildSessionFactory();
    }

    /* public static Connection getJDBC() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.getKey(URL_KEY),
                    PropertiesUtil.getKey(USER_NAME_KEY),
                    PropertiesUtil.getKey(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
    public static Connection getConnection() {
        if (connect == null) {
            try {
                connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "1234");
                System.out.println("Connect create");
            } catch (SQLException e) {
                System.out.println("Проблемы подключения к Базе Данных.");
                System.out.println(e);
            }
        }
        return connect;
//
//        public static SessionFactory getHibernate () {
//            try {
//                Configuration configuration = new Configuration();
//                configuration.addAnnotatedClass(User.class);
//                return (Connection) configuration.buildSessionFactory();
//            } catch (HibernateException e) {
//                throw new RuntimeException(e);
//            }
//        }


    }
}
