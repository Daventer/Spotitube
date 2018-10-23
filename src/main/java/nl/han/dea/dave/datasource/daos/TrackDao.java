package nl.han.dea.dave.datasource.daos;

import nl.han.dea.dave.controllers.dto.TrackDTO;
import nl.han.dea.dave.controllers.dto.TracksDTO;
import nl.han.dea.dave.datasource.identities.TrackIdentityMap;
import nl.han.dea.dave.logger.ExceptionLogger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDao extends Dao {
    private static ExceptionLogger logger = new ExceptionLogger(Dao.class.getName());

    public TracksDTO getAllTracks() {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String query = "select * from tracks";
        List<TrackDTO> trackDTOS = new ArrayList<>();

        if(!hashMapIsSmallerThenTracks()){
            return TrackIdentityMap.getAllTracks();
        }

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            resultSet = getRepository().executeQuery(preparedStatement);

            while (resultSet.next()) {
                TrackDTO trackDTO = new TrackDTO();
                trackDTO.setId(resultSet.getInt("id"));
                trackDTO.setTitle(resultSet.getString("title"));
                trackDTO.setPerformer(resultSet.getString("performer"));
                trackDTO.setDuration(resultSet.getInt("duration"));
                trackDTO.setAlbum(resultSet.getString("album"));
                trackDTO.setPlaycount(resultSet.getInt("playcount"));
                trackDTO.setPublicationDate(resultSet.getString("publicationdate"));
                trackDTO.setDescription(resultSet.getString("description"));
                trackDTO.setOfflineAvailable(resultSet.getBoolean("offlineavailable"));
                trackDTOS.add(trackDTO);
                addTrackToHashMap(trackDTO);
            }
        } catch (SQLException e) {
            logger.serveLogger(e);
        }finally {
            closeConnection(resultSet, preparedStatement);
        }

        return new TracksDTO(trackDTOS);
    }

    public List getTracksIdsFromPlaylistid(int id) {
        // query for playlist id
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM linktracktoplaylist where playlist = ?";
        List allIds = new ArrayList();

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setString(1, ""+id);
            resultSet = getRepository().executeQuery(preparedStatement);

            while (resultSet.next()) {
                allIds.add(resultSet.getInt("track"));
            }
        } catch (SQLException e) {
            logger.serveLogger(e);
        }finally {
            closeConnection(resultSet, preparedStatement);
        }

        return allIds;
    }

    public void addTrackToPlaylist(int playlistId, int trackId) {
        String query = "insert into linktracktoplaylist values (?, ?)";
        executeStatementFromPlaylistIdAndTrackId(playlistId, trackId, query);
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
            logger.serveLogger(e);
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
            TrackIdentityMap.updateOfflineAvailable(trackId, offlineAvailable);
        } catch (SQLException e) {
            logger.serveLogger(e);
        }finally {
            closeConnection(null, preparedStatement);
        }
    }

    public void deleteTrackFromPlaylist(int playlistId, int trackId) {
        String query = "" +
                "Delete from linktracktoplaylist " +
                "WHERE playlist=? AND track=?" +
                "";
        TrackIdentityMap.deleteTrackFromPlaylist(playlistId, trackId);
        executeStatementFromPlaylistIdAndTrackId(playlistId, trackId, query);
    }

    private void executeStatementFromPlaylistIdAndTrackId(int playlistId, int trackId, String query){
        PreparedStatement preparedStatement = null;

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, trackId);
            getRepository().executeUpdate(preparedStatement);
        } catch (SQLException e) {
            logger.serveLogger(e);
        }finally {
            closeConnection(null, preparedStatement);
        }
    }

    public int getTotalLengthOfTracks() {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String query = "select count(*) as total from tracks";
        int total = 0;

        try {
            getRepository().newConnection();
            preparedStatement = getRepository().preparedStatement(query);
            resultSet = getRepository().executeQuery(preparedStatement);

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

    public void addTrackToHashMap(TrackDTO trackDTO) {
        if (TrackIdentityMap.getTrack(trackDTO.getId()) == null) {
            TrackIdentityMap.addTrack(trackDTO);
        }
    }

    public boolean tracksExistsInHashmap(int id){
        return TrackIdentityMap.getTrack(id) != null;
    }

    public boolean hashMapIsSmallerThenTracks() {
        return TrackIdentityMap.getAllTracks().getTracks().size() < getTotalLengthOfTracks();
    }
}
