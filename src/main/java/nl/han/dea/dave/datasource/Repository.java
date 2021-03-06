package nl.han.dea.dave.datasource;

import nl.han.dea.dave.logger.ExceptionLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Repository {

    private static ExceptionLogger logger = new ExceptionLogger(Repository.class.getName());
    private Connection connection;

    public void newConnection() {
        try {
            this.connection = new Database().connect();
        } catch (SQLException e) {
            logger.serveLogger(e);
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

    public int getTotalFromQuery(String query){
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int total = 0;

        try {
            newConnection();
            preparedStatement = preparedStatement(query);
            resultSet = executeQuery(preparedStatement);

            while (resultSet.next()) {
                total = resultSet.getInt("total");
            }

        } catch (SQLException e) {
            logger.serveLogger(e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }

        return total;
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
            logger.serveLogger(e);
        }
    }


}
