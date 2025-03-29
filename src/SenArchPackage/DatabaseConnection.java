package SenArchPackage;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        try (InputStream input = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream("resources/config.properties")) {
            
            if (input == null) {
                throw new SQLException("Unable to find config.properties");
            }

            Properties props = new Properties();
            props.load(input);
            
            Class.forName(props.getProperty("db.driver"));
            
            return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
            );
        } catch (Exception e) {
            throw new SQLException("Database connection failed", e);
        }
    }
}