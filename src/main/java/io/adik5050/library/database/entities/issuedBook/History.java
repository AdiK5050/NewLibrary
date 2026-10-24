package io.adik5050.library.database.entities.issuedBook;

import java.sql.ResultSet;
import java.sql.SQLException;

public record History(
        int sno,
        String book_uuid,
        String user_uuid,
        String action,
        String timeStamp
) {
    public static History fromResultSet(ResultSet resultSet) throws SQLException {
        int sno = resultSet.getInt("sno");
        String book_uuid = resultSet.getString("book_uuid");
        String user_uuid = resultSet.getString("user_uuid");
        String action = resultSet.getString("action");
        String timeStamp = resultSet.getString("timeStamp");
        return new History(sno, book_uuid, user_uuid, action, timeStamp);
    }
}
