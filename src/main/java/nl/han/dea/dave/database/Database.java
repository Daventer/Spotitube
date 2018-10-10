package nl.han.dea.dave.database;

import nl.han.dea.dave.enums.EnvironmentType;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection connection;
    private Environment environment = new Environment();
    private String userName = environment.getEnvVariable(EnvironmentType.USERNAME);
    private String password = environment.getEnvVariable(EnvironmentType.PASSWORD);
    private String dbUrl = environment.getEnvVariable(EnvironmentType.DBURL);

    public Connection connect() throws SQLException {
        connection = null;
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance());
            // load and register JDBC driver for MySQL
            connection = DriverManager.getConnection(dbUrl, userName, password);
            return connection;
        } catch (SQLException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return connection;
    }
}
