package nl.han.dea.dave.datasource.daos;

import nl.han.dea.dave.dto.PlaylistDTO;
import nl.han.dea.dave.dto.PlaylistRequestDTO;
import nl.han.dea.dave.dto.PlaylistsDTO;
import nl.han.dea.dave.datasource.identities.PlaylistIdentityMap;
import nl.han.dea.dave.logger.ExceptionLogger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDao extends Dao {
    private static ExceptionLogger logger = new ExceptionLogger(Dao.class.getName());

    public PlaylistDTO getPlaylist(int playlistId) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        PlaylistDTO playlistDTO = null;
        String query = "select * from playlists where id=?";

        if (playlistExistsInHashmap(playlistId)){
            return PlaylistIdentityMap.getPlaylist(playlistId);
        }

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setInt(1, playlistId);
            resultSet = getRepository().executeQuery(preparedStatement);

            while (resultSet.next()) {
                playlistDTO = new PlaylistDTO();
                playlistDTO.setId(resultSet.getInt("id"));
                playlistDTO.setName(resultSet.getString("name"));
                playlistDTO.setOwner(resultSet.getBoolean("owner"));
                playlistDTO.setUserId(resultSet.getInt("user"));
                playlistDTO.setTracks(null);
                addPlaylistToHashMap(playlistDTO);
            }

        } catch (SQLException e) {
            logger.serveLogger(e);
        } finally {
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
            PlaylistIdentityMap.updatePlaylistName(playlistId, name);
        } catch (SQLException e) {
            logger.serveLogger(e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void addPlaylist(PlaylistRequestDTO playlistDTO, int userId) {
        PreparedStatement preparedStatement = null;
        String query = "Insert into playlists (name, owner, user) values (?, ?, ?)";

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setString(1, playlistDTO.getName());
            preparedStatement.setBoolean(2, playlistDTO.isOwner());
            preparedStatement.setInt(3, userId);
            getRepository().executeUpdate(preparedStatement);
        } catch (SQLException e) {
            logger.serveLogger(e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void deletePlaylist(int playlistId) {
        String query = "DELETE FROM playlists WHERE id = ?";

        executeUpdateStatementFromPlaylistId(playlistId, query);
        PlaylistIdentityMap.deletePlaylist(playlistId);
    }

    public void deleteTracksFromPlaylist(int playlistId) {
        String query = "DELETE FROM linktracktoplaylist WHERE playlist = ?";

        executeUpdateStatementFromPlaylistId(playlistId, query);
    }

    private void executeUpdateStatementFromPlaylistId(int playlistId, String query) {
        PreparedStatement preparedStatement = null;

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setInt(1, playlistId);
            getRepository().executeUpdate(preparedStatement);
        } catch (SQLException e) {
            logger.serveLogger(e);
        } finally {
            closeConnection(null, preparedStatement);
        }
    }

    public PlaylistsDTO getAllPlaylistsFromUser(int userId) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String query = "select * from playlists";
        ArrayList<PlaylistDTO> playlistDTOS = new ArrayList<>();
        PlaylistsDTO playlistsDTO = null;

        hashMapUpdateOwner(userId);

        if (!hashMapIsSmallerThenPlaylists()) {
            return PlaylistIdentityMap.getAllPlaylists();
        }

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            resultSet = getRepository().executeQuery(preparedStatement);

            while (resultSet.next()) {
                PlaylistDTO playlistDTO = new PlaylistDTO();
                playlistDTO.setId(resultSet.getInt("id"));
                playlistDTO.setName(resultSet.getString("name"));
                playlistDTO.setOwner(false);
                playlistDTO.setTracks(null);
                playlistDTO.setUserId(resultSet.getInt("user"));
                if (userId == resultSet.getInt("user")) {
                    playlistDTO.setOwner(true);
                }
                playlistDTOS.add(playlistDTO);
                addPlaylistToHashMap(playlistDTO);
            }

            playlistsDTO = new PlaylistsDTO(playlistDTOS);
        } catch (SQLException e) {
            logger.serveLogger(e);
        } finally {
            closeConnection(resultSet, preparedStatement);
        }

        return playlistsDTO;
    }

    public int getTotalLengthOfPlaylists() {
        String query = "select count(*) as total from playlists";
        return getRepository().getTotalFromQuery(query);
    }

    public void addPlaylistToHashMap(PlaylistDTO playlistDTO) {
        if (PlaylistIdentityMap.getPlaylist(playlistDTO.getId()) == null) {
            PlaylistIdentityMap.addPlaylist(playlistDTO);
        }
    }

    public boolean playlistExistsInHashmap(int id){
        return PlaylistIdentityMap.getPlaylist(id) != null;
    }

    public boolean hashMapIsSmallerThenPlaylists() {
        return PlaylistIdentityMap.getAllPlaylists().getPlaylists().size() < getTotalLengthOfPlaylists();
    }

    private void hashMapUpdateOwner(int userId) {
        List<PlaylistDTO> playlistDTOS = PlaylistIdentityMap.getAllPlaylists().getPlaylists();
        for(PlaylistDTO playlistDTO : playlistDTOS){
            if (checkIfPlaylistDTOUserIsTheSame(playlistDTO.getUserId(), userId)){
                PlaylistIdentityMap.updatePlaylistOwner(playlistDTO.getId(), true);
            }
            else {
                PlaylistIdentityMap.updatePlaylistOwner(playlistDTO.getId(), false);
            }
        }
    }

    private boolean checkIfPlaylistDTOUserIsTheSame(int playlistDTOUserId, int userId){
        return playlistDTOUserId == userId;
    }
}
