package io.adik5050.library.database;

import io.adik5050.library.database.dao.BookDao;
import io.adik5050.library.database.dao.UserDao;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseOperations {

    DatabaseConnection databaseConnection;
    public DatabaseOperations(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void executeUpdateStatement(String query) {
        Connection newConnection = null;
        Statement statement = null;
        try {
            newConnection = databaseConnection.getDatabaseConnection();
            statement = newConnection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.closeAutoClosable(statement);
            databaseConnection.closeAutoClosable(newConnection);
        }
    }

    public UserDao getUserDao() {
        return new UserDao(databaseConnection, this);
    }

    public BookDao getBookDao() {
        return new BookDao(databaseConnection, this);
    }
}