package io.adik5050.library.database;

import io.adik5050.library.database.dao.BookDao;
import io.adik5050.library.database.dao.HistoryDao;
import io.adik5050.library.database.dao.UserDao;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseOperations {

    private final DatabaseConnection databaseConnection;
    public DatabaseOperations(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public UserDao getUserDao() {
        return new UserDao(databaseConnection);
    }

    public BookDao getBookDao() {
        return new BookDao(databaseConnection);
    }

    public HistoryDao getHistoryDao() { return new HistoryDao(databaseConnection);
    }
}