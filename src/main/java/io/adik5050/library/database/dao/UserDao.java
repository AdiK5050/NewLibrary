package io.adik5050.library.database.dao;

import io.adik5050.library.database.DatabaseConnection;
import io.adik5050.library.database.DatabaseOperations;
import io.adik5050.library.database.entities.user.User;
import io.adik5050.library.database.entities.user.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    DatabaseConnection databaseConnection;
    DatabaseOperations databaseOperations;

    public UserDao(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
        createUserTable();
    }

    private void createUserTable() {
        Connection newConnection = null;
        Statement statement = null;
        String createUserTableQuery =
                "CREATE TABLE IF NOT EXISTS " +
                        "adidb.user(" +
                        " uuid VARCHAR(36) PRIMARY KEY," +
                        " name VARCHAR(100) NOT NULL,"+
                        " username VARCHAR(30)," +
                        " password VARCHAR(30)," +
                        " role VARCHAR(30)" +
                        ")";
        try {
            newConnection = databaseConnection.getDatabaseConnection();
            statement = newConnection.createStatement();
            statement.executeUpdate(createUserTableQuery);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.closeAutoClosable(statement);
            databaseConnection.closeAutoClosable(newConnection);
        }
    }

    public void insertUser(List<User> users) {
        Connection newConnection = null;
        PreparedStatement preparedStatement = null;

        String insertUserPreparedQuery = "INSERT INTO adidb.book(uuid, name, author, type, status) values(?,?,?,?,?)";

        try {
            newConnection = databaseConnection.getDatabaseConnection();
            preparedStatement = newConnection.prepareStatement(insertUserPreparedQuery);
            for(User user: users) {
                preparedStatement.setString(1, user.uuid());
                preparedStatement.setString(2, user.name());
                preparedStatement.setString(3, user.username());
                preparedStatement.setString(4, user.password());
                preparedStatement.setString(5, user.role());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.closeAutoClosable(preparedStatement);
            databaseConnection.closeAutoClosable(newConnection);
        }
    }

    public List<User> getAllUsers() {
        Connection newConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        String getAllUsersQuery = "SELECT * FROM adidb.user";
        try {
            newConnection = databaseConnection.getDatabaseConnection();
            statement = newConnection.createStatement();
            statement.executeQuery(getAllUsersQuery);
            resultSet = statement.executeQuery(getAllUsersQuery);
            while(resultSet.next()) users.add(getUserFromResultSet(resultSet));
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.closeAutoClosable(resultSet);
            databaseConnection.closeAutoClosable(statement);
            databaseConnection.closeAutoClosable(newConnection);
        }
    }

    public List<User> getAllUsersByRole(UserRole userRole) {
        Connection newConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        String getAllUsersByRoleQuery = "SELECT * FROM adidb.user WHERE role = '" + userRole.name() + "'";
        try {
            newConnection = databaseConnection.getDatabaseConnection();
            statement = newConnection.createStatement();
            statement.executeQuery(getAllUsersByRoleQuery);
            resultSet = statement.executeQuery(getAllUsersByRoleQuery);
            while(resultSet.next()) users.add(getUserFromResultSet(resultSet));
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.closeAutoClosable(resultSet);
            databaseConnection.closeAutoClosable(statement);
            databaseConnection.closeAutoClosable(newConnection);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        String uuid = resultSet.getString("uuid");
        String name = resultSet.getString("name");
        String username = resultSet.getString("username");
        String password = "";
        String role = resultSet.getString("role");
        return new User(uuid, name, username, password, role);
    }

    public void deleteUser(User user) {
        Connection newConnection = null;
        PreparedStatement preparedStatement = null;
        String deleteUserQuery = "DELETE FROM adidb.user where uuid = '" + user.uuid() +"'";

        try {
            newConnection = databaseConnection.getDatabaseConnection();
            preparedStatement = newConnection.prepareStatement(deleteUserQuery);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            databaseConnection.closeAutoClosable(preparedStatement);
            databaseConnection.closeAutoClosable(newConnection);
        }
    }
}
