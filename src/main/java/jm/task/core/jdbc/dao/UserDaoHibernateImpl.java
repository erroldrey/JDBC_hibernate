package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.GetSQLQueryUsersTemplate;
import jm.task.core.jdbc.util.Util;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.spi.PersistenceUnitTransactionType;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (
                Session session = Util.getFactory().openSession();
        ) {
            transaction = session.beginTransaction();
            session.createSQLQuery(GetSQLQueryUsersTemplate.CREATE_TABLE).executeUpdate();
            transaction.commit();

            System.out.println("Таблица 'users' успешно создана.");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();

            System.out.println("Проблема с созданием таблицы в Базе Данных.");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (
                Session session = Util.getFactory().openSession();
        ) {
            transaction = session.beginTransaction();
            session.createSQLQuery(GetSQLQueryUsersTemplate.DROP_TABLE).executeUpdate();
            transaction.commit();

            System.out.println("Таблица 'users' успешно была удалена.");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();

            System.out.println("Проблема с удаление таблицы в Базе Данных.");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (
                Session session = Util.getFactory().openSession();
        ) {
            transaction = session.beginTransaction();
            session.createSQLQuery(GetSQLQueryUsersTemplate.INSERT_ONE_USER)
                    .setParameter(1, name)
                    .setParameter(2, lastName)
                    .setParameter(3, age)
                    .executeUpdate();
            transaction.commit();

            System.out.println("User с именем " + name + " успешно добавлен.");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();

            System.out.println("Произошли проблемы с добавлением User.");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;
        try (
                Session session = Util.getFactory().openSession();
        ) {

            transaction = session.beginTransaction();
            session.createSQLQuery(GetSQLQueryUsersTemplate.DELETE_USER_BY_ID)
                    .setParameter(1, id)
                    .executeUpdate();
            transaction.commit();

            System.out.println("Пользователь удален.");

        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();

            System.out.println("Произошла ошибка удаления пользователя.");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Transaction transaction = null;
        try (
                Session session = Util.getFactory().openSession();
                ScrollableResults result = session
                        .createSQLQuery(GetSQLQueryUsersTemplate.SELECT_ALL_USERS)
                        .scroll();
        ) {
            transaction = session.beginTransaction();
            fillListUsers(result, list);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();

            System.out.println("Произошла ошибка получения пользователей.");
            e.printStackTrace();
        }
        return list;
    }

    private void fillListUsers(ScrollableResults result, List<User> list) {
        for (; result.next(); ) {
            Object[] resultLine = result.get();

            Long id = ((BigInteger) resultLine[0]).longValue();
            String name = (String) resultLine[1];
            String lastName = (String) resultLine[2];
            Byte age = ((Short) resultLine[3]).byteValue();

            User user = new User(name, lastName, age);
            user.setId(id);

            list.add(user);
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (
                Session session = Util.getFactory().openSession();
        ) {
            transaction = session.beginTransaction();
            session.createSQLQuery(GetSQLQueryUsersTemplate.DELETE_ALL_USERS).executeUpdate();
            transaction.commit();

            System.out.println("Таблица User успешно очищена.");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();

            System.out.println("Произошла ошибка очищения таблицы");
            e.printStackTrace();
        }
    }
}