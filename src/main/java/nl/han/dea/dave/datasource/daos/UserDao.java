package nl.han.dea.dave.datasource.daos;

import nl.han.dea.dave.dto.UserDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDao extends Dao {
    private static final Logger LOGGER = Logger.getLogger(Dao.class.getName());

    public UserDTO getUser(String userName) {

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String query = "select * from users where user=?";
        UserDTO userDTO = null;

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setString(1, userName);

            resultSet = getRepository().executeQuery(preparedStatement);

            while (resultSet.next()) {
                String user = resultSet.getString("user");
                String password = resultSet.getString("password");
                userDTO = new UserDTO(user, password);
            }

        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
        } finally {
            closeConnection(resultSet, preparedStatement);
        }

        return userDTO;
    }

    public List<UserDTO> getUsers() {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String query = "select * from users";
        List<UserDTO> userDTOS = new ArrayList<>();

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            resultSet = getRepository().executeQuery(preparedStatement);

            while (resultSet.next()) {
                String user = resultSet.getString("user");
                String password = resultSet.getString("password");

                UserDTO userDTO = new UserDTO(user, password);
                userDTOS.add(userDTO);
            }

        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
        } finally {
            closeConnection(resultSet, preparedStatement);
        }

        return userDTOS;
    }

    public int getUserIdFromToken(String token) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String query = "select * from users where token=?";
        int userId = 0;

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setString(1, token);

            resultSet = getRepository().executeQuery(preparedStatement);

            while (resultSet.next()) {
                userId = resultSet.getInt("id");
            }

        } catch (
                SQLException e) {
            LOGGER.severe(e.getMessage());
        } finally {
            closeConnection(resultSet, preparedStatement);
        }

        return userId;
    }

    public void setNewToken(String userName, String token) {
        PreparedStatement preparedStatement = null;
        String query = "" +
                "UPDATE users SET token=? " +
                "WHERE user=?" +
                "";
        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, userName);
            getRepository().executeUpdate(preparedStatement);
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
        } finally {
            closeConnection(null, preparedStatement);
        }
    }
}
