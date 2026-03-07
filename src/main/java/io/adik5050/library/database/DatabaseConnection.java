package io.adik5050.library.database;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private  Properties properties;
    private final Connection connection;

    public DatabaseConnection(String configPath) throws Exception {
        properties = getConfigProperties(configPath);
        connection = getDatabaseConnection(properties);
        if(connection.isValid(1000)) System.out.println("Database Connected Successfully");
    }

    Properties getConfigProperties(String configPath) throws IOException {
        Properties properties = new Properties();
        properties.load(Files.newInputStream(Path.of(configPath), StandardOpenOption.READ));
        return properties;
    }

    Connection getDatabaseConnection(Properties properties) throws SQLException {
        String url = properties.getProperty("URL");
        String username = properties.getProperty("USERNAME");
        String password = properties.getProperty("PASSWORD");
        return  DriverManager.getConnection(url, username, password);
    }
}
