package nl.han.dea.dave.datasource.identities;

import nl.han.dea.dave.dto.PlaylistDTO;
import nl.han.dea.dave.dto.PlaylistsDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaylistIdentityMap {

    private static Map<Integer, PlaylistDTO> playlistMap = new HashMap<>();

    private PlaylistIdentityMap(){

    }

    public static void addPlaylist(PlaylistDTO playlist){
        playlistMap.put(playlist.getId(), playlist);
    }

    public static void updatePlaylistName(int id, String name){
        PlaylistDTO getPlaylist = playlistMap.get(id);
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setId(id);
        playlistDTO.setName(name);
        playlistDTO.setOwner(getPlaylist.getOwner());
        playlistDTO.setTracks(getPlaylist.getTracks());
        playlistDTO.setUserId(getPlaylist.getUserId());
        playlistMap.put(id, playlistDTO);
    }

    public static void updatePlaylistTracks(int id, List tracks){
        PlaylistDTO getPlaylist = playlistMap.get(id);
        PlaylistDTO playlistDTO = new PlaylistDTO();

        playlistDTO.setId(id);
        playlistDTO.setName(getPlaylist.getName());
        playlistDTO.setOwner(getPlaylist.getOwner());
        playlistDTO.setTracks(tracks);
        playlistDTO.setUserId(getPlaylist.getUserId());

        playlistMap.put(id, playlistDTO);
    }

    public static void updatePlaylistOwner(int id, boolean owner){
        PlaylistDTO getPlaylist = playlistMap.get(id);
        PlaylistDTO playlistDTO = new PlaylistDTO();

        playlistDTO.setId(getPlaylist.getId());
        playlistDTO.setName(getPlaylist.getName());
        playlistDTO.setOwner(owner);
        playlistDTO.setTracks(getPlaylist.getTracks());
        playlistDTO.setUserId(getPlaylist.getUserId());

        playlistMap.put(id, playlistDTO);
    }

    public static void deletePlaylist(int id){
        playlistMap.remove(id);
    }

    public static PlaylistDTO getPlaylist(int id){
        return playlistMap.get(id);
    }

    public static PlaylistsDTO getAllPlaylists(){
        List<PlaylistDTO> playlistDTOS = new ArrayList<>();
        for (int key : playlistMap.keySet()){
            playlistDTOS.add(getPlaylist(key));
        }
        return new PlaylistsDTO(playlistDTOS);
    }
}
