package io.adik5050.library.database.dao;

import io.adik5050.library.database.DatabaseConnection;
import io.adik5050.library.database.DatabaseOperations;
import io.adik5050.library.database.entities.book.Book;
import io.adik5050.library.database.entities.book.BookStatus;
import io.adik5050.library.database.entities.book.BookType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    DatabaseConnection databaseConnection;
    DatabaseOperations databaseOperations;

    public BookDao(DatabaseConnection databaseConnection, DatabaseOperations databaseOperations) {
        this.databaseConnection = databaseConnection;
        this.databaseOperations = databaseOperations;
        createBookTable();
    }

    private void createBookTable() {
        String createBookTableQuery =
                "CREATE TABLE IF NOT EXISTS " +
                        "adidb.book(" +
                        " uuid VARCHAR(36) PRIMARY KEY," +
                        " name VARCHAR(100) NOT NULL," +
                        " author VARCHAR(100)," +
                        " type varchar(50), " +
                        " status VARCHAR(30)" +
                        ")";

        databaseOperations.executeUpdateStatement(createBookTableQuery);
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

    public void deleteBook(List<Book> books) {
        Connection newConnection = null;
        PreparedStatement preparedStatement = null;
        String deleteBookQuery = "DELETE FROM adidb.book where uuid = ?";

        try {
            newConnection = databaseConnection.getDatabaseConnection();
            preparedStatement = newConnection.prepareStatement(deleteBookQuery);
            for(Book book: books) {
                preparedStatement.setString(1, book.uuid());
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
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
                books.add(Book.fromResultSet(resultSet));
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
                books.add(Book.fromResultSet(resultSet));
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

    public List<Book> getAllBooksByType(BookType bookType) {
        Connection newConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Book> books = new ArrayList<>();
        String getAllBooksQueryByType = "SELECT * FROM adidb.book WHERE type = '" + bookType.name() + "'";
        try {
            newConnection = databaseConnection.getDatabaseConnection();
            statement = newConnection.createStatement();
            statement.executeQuery(getAllBooksQueryByType);
            resultSet = statement.executeQuery(getAllBooksQueryByType);
            while(resultSet.next()) {
                books.add(Book.fromResultSet(resultSet));
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

    public List<Book> searchBookByName(String bookName) {
        Connection newConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Book> books = new ArrayList<>();
        String searchBookByNameQuery = "SELECT * FROM adidb.book WHERE name LIKE ?";
        try {
            newConnection = databaseConnection.getDatabaseConnection();
            preparedStatement = newConnection.prepareStatement(searchBookByNameQuery);
            preparedStatement.setString(1, "%" + bookName + "%");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                books.add(Book.fromResultSet(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.closeAutoClosable(resultSet);
            databaseConnection.closeAutoClosable(preparedStatement);
            databaseConnection.closeAutoClosable(newConnection);
        }
    }
    public List<Book> searchBookByNameAndStatus(String bookName, BookStatus status) {
        Connection newConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Book> books = new ArrayList<>();
        String searchBookByNameQuery = "SELECT * FROM adidb.book WHERE name LIKE ? WHERE status = ?";
        try {
            newConnection = databaseConnection.getDatabaseConnection();
            preparedStatement = newConnection.prepareStatement(searchBookByNameQuery);
            preparedStatement.setString(1, "%" + bookName + "%");
            preparedStatement.setString(2, "'" + status.name() + "'");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                books.add(Book.fromResultSet(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.closeAutoClosable(resultSet);
            databaseConnection.closeAutoClosable(preparedStatement);
            databaseConnection.closeAutoClosable(newConnection);
        }
    }


    public List<Book> searchBookByAuthor(String authorName) {
        Connection newConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Book> books = new ArrayList<>();
        String searchBookByNameQuery = "SELECT * FROM adidb.book WHERE author LIKE ?";
        try {
            newConnection = databaseConnection.getDatabaseConnection();
            preparedStatement = newConnection.prepareStatement(searchBookByNameQuery);
            preparedStatement.setString(1, "%" + authorName + "%");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                books.add(Book.fromResultSet(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.closeAutoClosable(resultSet);
            databaseConnection.closeAutoClosable(preparedStatement);
            databaseConnection.closeAutoClosable(newConnection);
        }
    }

    public void updateBookStatus(Book book, BookStatus status) {
        Connection newConnection = null;
        PreparedStatement preparedStatement = null;
        String updateBookStatusQuery = "UPDATE adidb.book SET status = ? WHERE  uuid = ?";

        try {
            newConnection = databaseConnection.getDatabaseConnection();
            preparedStatement = newConnection.prepareStatement(updateBookStatusQuery);
            preparedStatement.setString(1, status.name());
            preparedStatement.setString(2, book.uuid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseConnection.closeAutoClosable(newConnection);
            databaseConnection.closeAutoClosable(preparedStatement);
        }
    }
}
