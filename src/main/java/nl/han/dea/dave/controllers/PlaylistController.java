package nl.han.dea.dave.controllers;

import nl.han.dea.dave.controllers.dto.PlaylistDTO;
import nl.han.dea.dave.controllers.dto.PlaylistRequestDTO;
import nl.han.dea.dave.controllers.dto.TrackDTO;
import nl.han.dea.dave.services.PlaylistService;
import nl.han.dea.dave.services.TrackService;
import nl.han.dea.dave.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistController {

    private PlaylistService playlistService;
    private TrackService trackService;
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(playlistService.allPlaylists()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response editPlaylist(@PathParam("id") int playlistId, @QueryParam("token") String token, PlaylistRequestDTO playlistDTO){
        if (playlistService.playlistExist(playlistId) && userService.tokenIsValid(token)){
            playlistService.updatePlaylistName(playlistId, playlistDTO.getName());
            return Response.ok(playlistService.allPlaylists()).build();
        }
        return Response.ok(401).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistRequestDTO playlistDTO){
        if (userService.tokenIsValid(token)){
            playlistDTO.setOwner(true);
            playlistService.addPlaylist(playlistDTO);
            return Response.ok(playlistService.allPlaylists()).build();
        }
        return Response.ok(401).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response deletePlaylist(@PathParam("id") int playlistId, @QueryParam("token") String token){
        if (userService.tokenIsValid(token)){
            playlistService.deletePlaylist(playlistId);
            return Response.ok(playlistService.allPlaylists()).build();
        }
        return Response.ok(401).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/tracks")
    public Response setTracks(@PathParam("id") int number, @QueryParam("token") String token) {
        if (!userService.tokenIsValid(token)) {
            return Response.ok(401).build();
        }
        return Response.ok(playlistService.tracksByPlaylistId(number)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/tracks")
    public Response addTrackToPlaylist(@PathParam("id") int playlistId, @QueryParam("token") String token, TrackDTO trackDTO) {
        int trackId = trackDTO.getId();
        Boolean offlineAvailable = trackDTO.getOfflineAvailable();
        // check token else return 401 error
        if (userService.tokenIsValid(token)) {
            // check if track exists and if track doesn't already exists inside the playlist
            if (trackService.trackExists(trackId) && !trackService.OfflineAvailableTheSame(trackId, offlineAvailable)) {
                trackService.updateOfflineAvailable(trackId, offlineAvailable);
            }
            if (!trackService.trackExistsInPlaylist(playlistId, trackId)){
                trackService.addTrackToPlaylist(playlistId, trackId);
            }
            return Response.ok(playlistService.tracksByPlaylistId(playlistId)).build();
        } else {
            return Response.ok(401).build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{playlistId}/tracks/{trackId}")
    public Response deleteTrackFromPlaylist(@PathParam("playlistId") int playlistId,@PathParam("trackId") int trackId,@QueryParam("token") String token) {
        // check token else return 401 error
        if (userService.tokenIsValid(token)) {
            // check if track exists and if track exists inside the playlist
            if (trackService.trackExists(trackId) && trackService.trackExistsInPlaylist(playlistId, trackId)) {
                trackService.deleteTrackFromPlaylist(playlistId, trackId);
            }
            return Response.ok(playlistService.tracksByPlaylistId(playlistId)).build();
        } else {
            return Response.ok(401).build();
        }
    }

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
