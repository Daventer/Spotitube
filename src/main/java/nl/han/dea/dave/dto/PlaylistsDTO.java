package nl.han.dea.dave.dto;

import java.util.List;

public class PlaylistsDTO {
    private List<PlaylistDTO> playlists;
    private int length;

    public PlaylistsDTO(List<PlaylistDTO> playlists){
        this.playlists =  playlists;
    }

    public List<PlaylistDTO> getPlaylists() {
        return this.playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
