package io.adik5050.library.database;

import io.adik5050.library.database.entities.book.Book;
import io.adik5050.library.database.entities.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DatabaseOperations {

    DatabaseConnection databaseConnection;
    public DatabaseOperations(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
        createBookTable();
        createUserTable();
    }

    private void executeUpdateStatement(String query) {
        Connection newConnection = null;
        Statement statement = null;
        try {
            newConnection = databaseConnection.getDatabaseConnection();
            statement = newConnection.createStatement();
            statement.executeUpdate(query);
            System.out.println("BOOK TABLE CREATED");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.closeAutoClosable(statement);
            databaseConnection.closeAutoClosable(newConnection);
        }
    }

    public void createBookTable() {
        String createBookTableQuery =
                "CREATE TABLE IF NOT EXISTS " +
                        "adidb.book(" +
                        " uuid VARCHAR(36) PRIMARY KEY," +
                        " name VARCHAR(100) NOT NULL," +
                        " author VARCHAR(100)," +
                        " type varchar(50), " +
                        " status VARCHAR(30)" +
                        ")";

        executeUpdateStatement(createBookTableQuery);
    }

    public void createUserTable() {
        String createUserTableQuery =
                "CREATE TABLE IF NOT EXISTS " +
                        "adidb.user(" +
                        " uuid VARCHAR(36) PRIMARY KEY," +
                        " name VARCHAR(100) NOT NULL,"+
                        " username VARCHAR(30)," +
                        " password VARCHAR(30)," +
                        " role VARCHAR(30)" +
                        ")";
        executeUpdateStatement(createUserTableQuery);
    }

    public void insertBook(List<Book> books) {
        Connection newConnection = null;
        PreparedStatement preparedStatement = null;
        System.out.println(books.size());
        String insertBookPreparedQuery = "INSERT INTO adidb.book(uuid, name, author, type, status) values(?,?,?,?,?)";

        try {
            newConnection = databaseConnection.getDatabaseConnection();
            preparedStatement = newConnection.prepareStatement(insertBookPreparedQuery);
            for(Book book: books) {
                preparedStatement.setString(1, book.uuid());
                preparedStatement.setString(2, book.name());
                preparedStatement.setString(3, book.author());
                preparedStatement.setString(4, book.type());
                preparedStatement.setString(5, book.status());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.closeAutoClosable(preparedStatement);
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
}
