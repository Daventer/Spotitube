package nl.han.dea.dave.controllers.dto;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

public class TracksDTO {
    private ArrayList<TrackDTO> tracks = new ArrayList<>();

    public TracksDTO(ArrayList<TrackDTO> trackDTOS){
        this.tracks = trackDTOS;
    }
//
//    public void addTrack(TrackDTO trackDTO){
//        this.tracks.add(trackDTO);
//    }

    public ArrayList<TrackDTO> getTracks(){
        return this.tracks;
    }
}
