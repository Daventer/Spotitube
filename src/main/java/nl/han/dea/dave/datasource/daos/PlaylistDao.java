package nl.han.dea.dave.datasource.daos;

import nl.han.dea.dave.controllers.dto.PlaylistDTO;
import nl.han.dea.dave.controllers.dto.PlaylistRequestDTO;
import nl.han.dea.dave.controllers.dto.PlaylistsDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDao extends Dao {

    public PlaylistDTO getPlaylist(int playlistId) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        PlaylistDTO playlistDTO = null;
        String query = "select * from playlists where id=?";

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setInt(1, playlistId);
            resultSet = getRepository().executeQuery(preparedStatement);

            while (resultSet.next()) {
                playlistDTO = new PlaylistDTO(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getBoolean("owner"), null);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(resultSet, preparedStatement);
        }

        return playlistDTO;
    }

    public void updatePlaylistName(int playlistId, String name) {
        PreparedStatement preparedStatement = null;
        String query = "" +
                "UPDATE playlists SET name=? " +
                "WHERE id=?" +
                "";
        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, playlistId);
            getRepository().executeUpdate(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void addPlaylist(PlaylistRequestDTO playlistDTO) {
        PreparedStatement preparedStatement = null;
        String query = "Insert into playlists (name, owner) values (?, ?)";

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setString(1, playlistDTO.getName());
            preparedStatement.setBoolean(2, playlistDTO.isOwner());
            getRepository().executeUpdate(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void deletePlaylist(int playlistId) {
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM playlists WHERE id = ?";

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setInt(1, playlistId);
            getRepository().executeUpdate(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void deleteTracksFromPlaylist(int playlistId) {
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM linktracktoplaylist WHERE playlist = ?";

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setInt(1, playlistId);
            getRepository().executeUpdate(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(null, preparedStatement);
        }
    }

    public PlaylistsDTO getAllPlaylistsFromUser(int userId) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
//        String query = "select * from playlists where id=?";
        String query = "select * from playlists";
        ArrayList<PlaylistDTO> playlistDTOS = new ArrayList<>();
        PlaylistsDTO playlistsDTO = null;

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
//            preparedStatement.setInt(1, userId);
            resultSet = getRepository().executeQuery(preparedStatement);

            while (resultSet.next()) {
                PlaylistDTO playlistDTO = new PlaylistDTO(resultSet.getInt("id"), resultSet.getString("name"), false, null);
                if (userId == resultSet.getInt("user")){
                    playlistDTO.setOwner(true);
                }
                playlistDTOS.add(playlistDTO);
            }

            playlistsDTO = new PlaylistsDTO(playlistDTOS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(resultSet, preparedStatement);
        }

        return playlistsDTO;
    }
}
