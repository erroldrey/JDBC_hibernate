package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();

        service.createUsersTable();

        User user1 = new User("Denis", "Sukonnikov", (byte) 28);
        User user2 = new User("Nataliya", "Sukonnikova", (byte) 26);
        User user3 = new User("Andrew", "Andreev", (byte) 18);
        User user4 = new User("Steph", "Curry", (byte) 35);

        List<User> users = service.getAllUsers();
        for (User u : users) {
            System.out.println(u);
        }
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}


