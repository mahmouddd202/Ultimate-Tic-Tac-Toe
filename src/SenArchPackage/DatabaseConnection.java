package SenArchPackage;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    // Private constructor
    private DatabaseConnection() throws SQLException {
        try (InputStream input = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream("resources/config.properties")) {

            if (input == null) {
                throw new SQLException("Unable to find config.properties");
            }

            Properties props = new Properties();
            props.load(input);

            Class.forName(props.getProperty("db.driver"));

            this.connection = DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            );
        } catch (Exception e) {
            throw new SQLException("Database connection failed", e);
        }
    }

    // Public static method to get the instance
    public static synchronized DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Method to get the connection
    public Connection getConnection() {
        return connection;
    }
}