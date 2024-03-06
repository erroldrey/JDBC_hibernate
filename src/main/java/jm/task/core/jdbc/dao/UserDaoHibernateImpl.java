package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private final SessionFactory sessionFactory = Util.getHibernate();

    @Override
    public void createUsersTable() {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users (\n" +
                "            id BIGSERIAL PRIMARY KEY ,\n" +
                "            name VARCHAR (100) ,\n" +
                "            last_name VARCHAR(150) ,\n" +
                "            age SMALLINT NOT NULL \n" +
                "            )").executeUpdate();
        session.getTransaction().commit();
        session.close();

        System.out.println("Таблица создана!");
    }


    @Override
    public void dropUsersTable() {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS Users").executeUpdate();
        session.getTransaction().commit();
        session.close();
        System.out.println("Таблица удалена!");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = null;
        try {
            session = sessionFactory.openSession();

            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            assert session != null;
            session.getTransaction().rollback();
        } finally {
            assert session != null;
            session.close();
        }
        System.out.println("Пользователь с именем " + name + " успешно добавлен!");
    }

    @Override
    public void removeUserById(long id) {
        Long myId = id;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.get(User.class, myId);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        userList = session.createQuery("from User ", User.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from User").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
