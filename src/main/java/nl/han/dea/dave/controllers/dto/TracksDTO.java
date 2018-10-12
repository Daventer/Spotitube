package nl.han.dea.dave.controllers.dto;

import java.util.List;

public class TracksDTO {
    private List<TrackDTO> tracks;

    public TracksDTO(List<TrackDTO> trackDTOS){
        this.tracks = trackDTOS;
    }

    public List<TrackDTO> getTracks(){
        return this.tracks;
    }
}
