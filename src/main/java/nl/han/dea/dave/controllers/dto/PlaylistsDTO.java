package nl.han.dea.dave.controllers.dto;

import java.util.ArrayList;

public class PlaylistsDTO {
    private ArrayList<PlaylistDTO> playlists;
    private int length;

    public PlaylistsDTO(ArrayList<PlaylistDTO> playlists){
        this.playlists =  playlists;
    }

    public ArrayList<PlaylistDTO> getPlaylists() {
        return this.playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
