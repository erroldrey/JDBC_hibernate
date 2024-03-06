package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String sqlCreate = """
             CREATE TABLE IF NOT EXISTS Users (
            id BIGSERIAL PRIMARY KEY UNIQUE ,
            name VARCHAR (10) NOT NULL ,
            last_name VARCHAR(15) NOT NULL ,
            age SMALLINT NOT NULL 
            );
            """;
    private static final String sqlDrop = """
            DROP TABLE IF EXISTS Users;
            """;
    private static final String sqlSaveUser = """
                      INSERT INTO Users (name, last_name, age)
                      VALUES 
                      (?, ?, ?)
                      
            """;
    private static final String sqlRemoveUserById = """
            DELETE FROM Users 
            WHERE id = ?
            """;
    private static final String sqlGetAllUsers = """
            SELECT * 
            FROM Users
            """;
    private static final String sqlCleanTable = """
            DELETE FROM Users
            """;


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() throws SQLException {
        try (Connection connection = Util.getJDBC();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlCreate)) {
            preparedStatement.execute();
            System.out.println("Таблица создана!");
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Connection connection = Util.getJDBC();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlDrop)) {
            preparedStatement.execute();
            System.out.println("Таблица удалена!");
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (Connection connection = Util.getJDBC();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSaveUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.println("User с именем " + name + " успешно добавлен!");
        }
    }

    public void removeUserById(long id) throws SQLException {
        try (Connection connection = Util.getJDBC();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlRemoveUserById)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getJDBC();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlGetAllUsers)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Пользователи добавлены!");
        return userList;
    }


    public void cleanUsersTable() throws SQLException {
        try (Connection connection = Util.getJDBC();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlCleanTable)) {
            preparedStatement.execute();
            System.out.println("Таблица очищена!");
        }
    }
}