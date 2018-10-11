package nl.han.dea.dave.datasource.daos;

import nl.han.dea.dave.controllers.dto.TrackDTO;
import nl.han.dea.dave.controllers.dto.TracksDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackDao extends Dao {

    public TracksDTO getAllTracks() {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String query = "select * from tracks";
        ArrayList<TrackDTO> trackDTOS = new ArrayList<>();

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            resultSet = getRepository().executeQuery(preparedStatement);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String performer = resultSet.getString("performer");
                int duration = resultSet.getInt("duration");
                String album = resultSet.getString("album");
                int playcount = resultSet.getInt("playcount");
                String publicationDate = resultSet.getString("publicationdate");
                String description = resultSet.getString("description");
                boolean offlineAvaliable = resultSet.getBoolean("offlineavailable");

                TrackDTO trackDTO = new TrackDTO(id, title, performer, duration, album, playcount, publicationDate, description, offlineAvaliable);
                trackDTOS.add(trackDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(resultSet, preparedStatement);
        }

        return new TracksDTO(trackDTOS);
    }

    public ArrayList getTracksIdsFromPlaylistid(int id) {
        // query for playlist id
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM linktracktoplaylist where playlist = ?";
        ArrayList allIds = new ArrayList();

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setString(1, ""+id);
            resultSet = getRepository().executeQuery(preparedStatement);

            while (resultSet.next()) {
                allIds.add(resultSet.getInt("track"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(resultSet, preparedStatement);
        }

        return allIds;
    }


    public void addTracktoPlaylist(int playlistId, int trackId) {
        PreparedStatement preparedStatement = null;
        String query = "insert into linktracktoplaylist values (?, ?)";

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, trackId);
            getRepository().executeUpdate(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void updateTrack(TrackDTO trackDTO) {
        PreparedStatement preparedStatement = null;
        String query = "" +
                "UPDATE tracks SET album=?, description=?, duration=?, offlineavailable=?, performer=?, playcount=?, publicationdate=?, title=? "+
                "WHERE id=?" +
                "";
        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setString(1, trackDTO.getAlbum());
            preparedStatement.setString(2, trackDTO.getDescription());
            preparedStatement.setInt(3, trackDTO.getDuration());
            preparedStatement.setBoolean(4, trackDTO.getOfflineAvailable());
            preparedStatement.setString(5, trackDTO.getPerformer());
            preparedStatement.setInt(6, trackDTO.getPlaycount());
            preparedStatement.setString(7, trackDTO.getPublicationDate());
            preparedStatement.setString(8, trackDTO.getTitle());
            preparedStatement.setInt(9, trackDTO.getId());
            getRepository().executeUpdate(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void updateOfflineAvailable(int trackId, Boolean offlineAvailable) {
        PreparedStatement preparedStatement = null;
        String query = "" +
                "UPDATE tracks SET offlineavailable=? " +
                "WHERE id=?" +
                "";
        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setBoolean(1, offlineAvailable);
            preparedStatement.setInt(2, trackId);
            getRepository().executeUpdate(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void deleteTrackFromPlaylist(int playlistId, int trackId) {
        PreparedStatement preparedStatement = null;
        String query = "" +
                "Delete from linktracktoplaylist " +
                "WHERE playlist=? AND track=?" +
                "";
        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, trackId);
            getRepository().executeUpdate(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(null, preparedStatement);
        }
    }
}
