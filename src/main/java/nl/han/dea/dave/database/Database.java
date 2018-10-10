package nl.han.dea.dave.database;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection connection;
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String DBURL = "jdbc:mysql://localhost/spotitube";

    public Connection connect() throws SQLException {
        connection = null;
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance());
            // load and register JDBC driver for MySQL
            connection = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return connection;
    }
}
