package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        User user = new User("Иван", "Иванов", (byte) 90);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        userService.removeUserById(2);
        userService.createUsersTable();
        List<User> users = userService.getAllUsers();
        for (User person : users) {
            System.out.println(person);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
