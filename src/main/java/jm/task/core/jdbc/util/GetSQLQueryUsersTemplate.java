package jm.task.core.jdbc.util;

public class GetSQLQueryUsersTemplate {
    public static final String CREATE_TABLE = """
                CREATE TABLE IF NOT EXISTS users (
                    id BIGSERIAL PRIMARY KEY,
                    name VARCHAR(60) NOT NULL,
                    lastName VARCHAR(60) NOT NULL,
                    age SMALLINT NOT NULL
                );
                """;
    public static final String DROP_TABLE = """
                DROP TABLE IF EXISTS users;
                """;
    public static final String INSERT_ONE_USER = """
                INSERT INTO users (name, lastName, age)
                VALUES (?, ?, ?)
                """;
    public static final String DELETE_USER_BY_ID = """
                DELETE FROM users
                WHERE id = ?;
                """;
    public static final String SELECT_ALL_USERS = """
                SELECT * FROM users;
                """;

    public static final String DELETE_ALL_USERS = """
                DELETE FROM users;
                """;
}