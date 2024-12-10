package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (Id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                " Name VARCHAR(20), LastName VARCHAR(20), Age TINYINT)";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Database has been created!");
        } catch (SQLException e) {
            System.out.println("Connection failed in creating table!");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS users";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Database has been dropped!");
        } catch (SQLException e) {
            System.out.println("Connection failed in drop table!");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println("User has been saved!");
        } catch (SQLException e) {
            System.out.println("Connection failed in saveUser!");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) throws SQLException {
        String sql = "DELETE FROM users WHERE Id = ?";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);

            ps.executeUpdate();

            System.out.println("User has been removed!");
        } catch (SQLException e) {
            System.out.println("Connection failed in removeUserById");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Connection failed in getAllUsers");
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() throws SQLException {
        String sql = "DELETE FROM users";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Database has been cleaned!");
        } catch (SQLException e) {
            System.out.println("Connection failed in cleanUsersTable");
            e.printStackTrace();
        }
    }
}
