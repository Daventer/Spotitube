package nl.han.dea.dave.controllers;

import nl.han.dea.dave.services.TrackService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tracks")
public class TracksController {

    private TrackService trackService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allTracks(){
        return Response.ok(trackService.allTracks()).build();
    }

    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }
}
