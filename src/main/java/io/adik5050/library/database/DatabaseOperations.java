package io.adik5050.library.database;

import io.adik5050.library.database.entities.book.Book;
import io.adik5050.library.database.entities.book.BookStatus;
import io.adik5050.library.database.entities.user.User;
import io.adik5050.library.database.entities.user.UserRole;

import java.sql.*;
import java.util.ArrayList;
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

    public List<Book> getAllBooks() {
        Connection newConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Book> books = new ArrayList<>();
        String getAllBooksQuery = "SELECT * FROM adidb.book";
        try {
            newConnection = databaseConnection.getDatabaseConnection();
            statement = newConnection.createStatement();
            statement.executeQuery(getAllBooksQuery);
            resultSet = statement.executeQuery(getAllBooksQuery);
            while(resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.closeAutoClosable(resultSet);
            databaseConnection.closeAutoClosable(statement);
            databaseConnection.closeAutoClosable(newConnection);
        }
    }

    public List<Book> getAllBooksByStatus(BookStatus bookStatus) {
        Connection newConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Book> books = new ArrayList<>();
        String getAllBooksQueryByStatus = "SELECT * FROM adidb.book WHERE status = '" + bookStatus.name() + "'";
        try {
            newConnection = databaseConnection.getDatabaseConnection();
            statement = newConnection.createStatement();
            statement.executeQuery(getAllBooksQueryByStatus);
            resultSet = statement.executeQuery(getAllBooksQueryByStatus);
            while(resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.closeAutoClosable(resultSet);
            databaseConnection.closeAutoClosable(statement);
            databaseConnection.closeAutoClosable(newConnection);
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        String uuid = resultSet.getString("uuid");
        String name = resultSet.getString("name");
        String author = resultSet.getString("author");
        String type = resultSet.getString("type");
        String status = resultSet.getString("status");
        return new Book(uuid, name, author, type, status);
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
}