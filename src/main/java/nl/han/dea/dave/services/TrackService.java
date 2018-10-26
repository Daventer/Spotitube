package nl.han.dea.dave.services;

import nl.han.dea.dave.dto.TrackDTO;
import nl.han.dea.dave.dto.TracksDTO;
import nl.han.dea.dave.datasource.daos.TrackDao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class TrackService {

    private TrackDao trackDao;

    public TracksDTO allTracks() {
        return trackDao.getAllTracks();
    }

    // Communicate with the link table
    public TracksDTO tracksFromPlaylistId(int id) {
        // Make new arraylist of TrackDTOS
        ArrayList<TrackDTO> trackDTOS = new ArrayList<>();
        // Connect with DAO
        List trackIds = trackDao.getTracksIdsFromPlaylistid(id);

        // loop through the id's and call getTracksById
        for(int i = 0; i < trackIds.size(); i++){
            trackDTOS.add(getTrackById((Integer) trackIds.get(i)));
        }
        // Create new TracksDTO and return it
        return new TracksDTO(trackDTOS);
    }

    // get from filled tracks(objects)
    public TrackDTO getTrackById(int id) {
        TracksDTO tracksDTO = trackDao.getAllTracks();
        for (TrackDTO trackDTO : tracksDTO.getTracks()){
            if (trackDTO.getId() == id){
                return trackDTO;
            }
        }
        return null;
    }

    public boolean trackExists(int id){
        return getTrackById(id) != null;
    }

    public boolean trackExistsInPlaylist(int playlistId, int trackId){
        TracksDTO tracks = tracksFromPlaylistId(playlistId);
        for (TrackDTO track : tracks.getTracks()){
            if (track.getId() == trackId){
                return true;
            }
        }
        return false;
    }

    public boolean offlineAvailableIsTheSame(int trackId, Boolean offlineAvailable) {
        return getTrackById(trackId).getOfflineAvailable() == offlineAvailable;
    }

    public void addTrackToPlaylist(int playlistId, int trackId){
        trackDao.addTrackToPlaylist(playlistId, trackId);
    }

    public void updateTrack(TrackDTO trackDTO) {
        trackDao.updateTrack(trackDTO);
    }

    public void updateOfflineAvailable(int trackId, Boolean offlineAvailable) {
        trackDao.updateOfflineAvailable(trackId, offlineAvailable);
    }

    public void deleteTrackFromPlaylist(int playlistId, int trackId){
        trackDao.deleteTrackFromPlaylist(playlistId, trackId);
    }

    @Inject
    public void setTrackDao(TrackDao trackDao) {
        this.trackDao = trackDao;
    }
}
