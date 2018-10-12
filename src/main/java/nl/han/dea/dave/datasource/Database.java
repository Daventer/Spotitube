package nl.han.dea.dave.datasource;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Environment environment = new Environment();
    private String userName = environment.getEnvVariable(EnvironmentType.USERNAME);
    private String password = environment.getEnvVariable(EnvironmentType.PASSWORD);
    private String dbUrl = environment.getEnvVariable(EnvironmentType.DBURL);
    private String mysqlDriver = environment.getEnvVariable(EnvironmentType.MYSQLDRIVER);

    public Connection connect() throws SQLException {
        Connection connection = null;
        try {
            DriverManager.registerDriver((Driver) Class.forName(mysqlDriver).getDeclaredConstructor().newInstance());
            connection = DriverManager.getConnection(dbUrl, userName, password);
            return connection;
        } catch (SQLException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return connection;
    }
}
