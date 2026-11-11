package io.adik5050.library.database.dao;

import io.adik5050.library.database.DatabaseConnection;
import io.adik5050.library.database.DatabaseOperations;
import io.adik5050.library.database.entities.issuedBook.Action;
import io.adik5050.library.database.entities.issuedBook.History;

import java.sql.*;

public class HistoryDao {

    DatabaseConnection databaseConnection;

    public HistoryDao(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    private void createHistoryTable() {
        Connection newConnection = null;
        Statement statement = null;
        String createHistoryTableQuery = "CREATE TABLE adidb.history(uuid INT NOT NULL AUTO_INCREMENT PRIMARY KEY, book_uuid VARCHAR(36), user_uuid VARCHAR(36), action VARCHAR(20), timeStamp TIMESTAMP);";
        try {
            newConnection = databaseConnection.getDatabaseConnection();
            statement = newConnection.createStatement();
            statement.executeUpdate(createHistoryTableQuery);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.closeAutoClosable(statement);
            databaseConnection.closeAutoClosable(newConnection);
        }
    }

    public void insertHistory(String book_uuid, String user_uuid, Action action) {
        Connection newConnection = null;
        PreparedStatement preparedStatement = null;
        String insertQuery = "INSERT INTO adidb.history(book_uuid, user_uuid, action) VALUES(?, ?, ?);";
        try {
            newConnection = databaseConnection.getDatabaseConnection();
            preparedStatement = newConnection.prepareStatement(insertQuery);
            preparedStatement.setString(1, book_uuid);
            preparedStatement.setString(2, user_uuid);
            preparedStatement.setString(3, action.name());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            databaseConnection.closeAutoClosable(newConnection);
            databaseConnection.closeAutoClosable(preparedStatement);
        }
    }

    public History getHistoryByBookId(String book_uuid) {
        Connection newConnection = null;
        PreparedStatement preparedStatement = null;
        String getHistoryByBookIdQuery = "SELECT * FROM adidb.history WHERE book_uuid = ? ORDER BY timeStamp DESC LIMIT 1;";

        try {
            newConnection = databaseConnection.getDatabaseConnection();
            preparedStatement = newConnection.prepareStatement(getHistoryByBookIdQuery);
            preparedStatement.setString(1, book_uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            return History.fromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.closeAutoClosable(newConnection);
            databaseConnection.closeAutoClosable(preparedStatement);
        }
    }
}
