package nl.han.dea.dave.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Repository {

    private static final Logger LOGGER =  Logger.getLogger(Repository.class.getName());
    private Connection connection;

    public void newConnection() {
        try {
            this.connection = new Database().connect();
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    public PreparedStatement preparedStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    public ResultSet executeQuery(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    public int executeUpdate(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeUpdate();
    }

    public void closeConnection(ResultSet resultSet, PreparedStatement preparedStatement) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
        }
    }


}
