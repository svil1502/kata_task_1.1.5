package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS users(" +
                    "ID BIGINT NOT NULL AUTO_INCREMENT, NAME VARCHAR(100), " +
                    "LASTNAME VARCHAR(100), AGE TINYINT, PRIMARY KEY (ID) )");
            System.out.println("Таблица создана");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {
            stat.executeUpdate("DROP TABLE IF EXISTS users");
            System.out.println("Таблица удалена");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = getConnection();
             PreparedStatement preStat = connection.prepareStatement("INSERT" +
                     " INTO users (NAME, LASTNAME, AGE) VALUES(?, ?, ?)")) {
            preStat.setString(1, name);
            preStat.setString(2, lastName);
            preStat.setByte(3, age);
            preStat.executeUpdate();
            System.out.println("User was added!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Connection connection = getConnection();
             PreparedStatement preStat = connection.prepareStatement("DELETE" +
                     " FROM users WHERE ID=?")) {
            preStat.setLong(1, id);
            preStat.executeUpdate();
            System.out.println("User was removed!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {
            ResultSet resultSet = stat.executeQuery("SELECT ID, " +
                    "NAME, LASTNAME, AGE FROM users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                userList.add(user);
            }
            System.out.println("List of users is ready!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {
            stat.executeUpdate("DELETE FROM users");
            System.out.println("Table was cleaned!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
