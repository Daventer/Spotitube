package nl.han.dea.dave.daos;

import nl.han.dea.dave.controllers.dto.UserDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao extends Dao {

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

        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(resultSet, preparedStatement);
        }

        return userDTO;
    }

    public ArrayList<UserDTO> getUsers() {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String query = "select * from users";
        ArrayList<UserDTO> userDTOS = new ArrayList<>();

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
            e.printStackTrace();
        } finally {
            closeConnection(resultSet, preparedStatement);
        }

        return userDTOS;
    }
}
