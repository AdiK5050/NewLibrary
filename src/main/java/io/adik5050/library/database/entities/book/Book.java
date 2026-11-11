package io.adik5050.library.database.entities.book;

import java.sql.ResultSet;
import java.sql.SQLException;

public record Book (
        String uuid,
        String name,
        String author,
        String type,
        String status
) {
    public static Book fromResultSet(ResultSet resultSet) throws SQLException {
        String uuid = resultSet.getString("uuid");
        String name = resultSet.getString("name");
        String author = resultSet.getString("author");
        String type = resultSet.getString("type");
        String status = resultSet.getString("status");
        return new Book(uuid, name, author, type, status);
    }
}
