package nl.han.dea.dave.datasource.identities;

import nl.han.dea.dave.controllers.dto.TrackDTO;
import nl.han.dea.dave.controllers.dto.TracksDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackIdentityMap{

    private TrackIdentityMap(){
        
    }

    private static Map<Integer, TrackDTO> trackMap = new HashMap<>();

    public static void addTrack(TrackDTO track){
        trackMap.put(track.getId(), track);
    }

    public static void updateOfflineAvailable(int trackId, Boolean offlineAvailable){
        TrackDTO getTrack = trackMap.get(trackId);
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setId(trackId);
        trackDTO.setTitle(getTrack.getTitle());
        trackDTO.setDescription(getTrack.getDescription());
        trackDTO.setPublicationDate(getTrack.getPublicationDate());
        trackDTO.setAlbum(getTrack.getAlbum());
        trackDTO.setPlaycount(getTrack.getPlaycount());
        trackDTO.setDuration(getTrack.getDuration());
        trackDTO.setPerformer(getTrack.getPerformer());
        trackDTO.setOfflineAvailable(offlineAvailable);

        trackMap.put(trackId, trackDTO);
    }

    public static void deleteTrack(int id){
        trackMap.remove(id);
    }

    public static TrackDTO getTrack(int id){
        return trackMap.get(id);
    }

    public static TracksDTO getAllTracks(){
        List<TrackDTO> tracksDTOS = new ArrayList<>();
        for (int key : trackMap.keySet()){
            tracksDTOS.add(getTrack(key));
        }
        return new TracksDTO(tracksDTOS);
    }

    public static void deleteTrackFromPlaylist(int playlistId, int trackId) {
        List<TrackDTO> oldTracks = PlaylistIdentityMap.getPlaylist(playlistId).getTracks();
        List<TrackDTO> newTracks = new ArrayList<>();

        for(TrackDTO track : oldTracks){
            if(track.getId() != trackId){
                newTracks.add(track);
            }
        }

        PlaylistIdentityMap.updatePlaylistTracks(playlistId, newTracks);
    }
}
