package nl.han.dea.dave;

import nl.han.dea.dave.controllers.PlaylistController;
import nl.han.dea.dave.controllers.dto.*;
import nl.han.dea.dave.services.PlaylistService;
import nl.han.dea.dave.services.TrackService;
import nl.han.dea.dave.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class PlaylistControllerTest {

    private final String TOKEN = "1234-1234-1234";
    private final int PLAYLISTID = 1;
    private final int TRACKID = 1;

    private PlaylistController playlistController;
    private PlaylistRequestDTO playlistRequestDTO;

    @BeforeEach
    public void setup() {
        // playlist
        playlistController = new PlaylistController();
        PlaylistService playlistService = Mockito.mock(PlaylistService.class);
        playlistController.setPlaylistService(playlistService);
        playlistRequestDTO = new PlaylistRequestDTO();

        // userService
        UserService userService = Mockito.mock(UserService.class);
        playlistController.setUserService(userService);

        // trackService
        TrackService trackService = Mockito.mock(TrackService.class);
        playlistController.setTrackService(trackService);

        // All playlists
        ArrayList<PlaylistDTO> playLists = new ArrayList<>();
        playLists.add(new PlaylistDTO());
        PlaylistsDTO playlistsDTO = new PlaylistsDTO(playLists);

        // MOCKITO
        // control token
        Mockito.when(userService.tokenIsValid(TOKEN)).thenReturn(true);
        // get userId
        int userId = 1;
        Mockito.when(userService.getUserIdFromToken(TOKEN)).thenReturn(userId);
        // get all plalists from user
        Mockito.when(playlistService.allPlaylistsFromUser(userId)).thenReturn(playlistsDTO);
        // PlaylistExists
        Mockito.when(playlistService.playlistExist(PLAYLISTID)).thenReturn(true);
        // Check if track exists
        Mockito.when(trackService.trackExists(TRACKID)).thenReturn(true);
        // OfflineAvaliableIsNotTheSame
        Mockito.when(trackService.offlineAvailableIsTheSame(TRACKID, true)).thenReturn(false);
        // Track doesn't exists in playlist yet
        Mockito.when(trackService.trackExistsInPlaylist(PLAYLISTID, TRACKID)).thenReturn(false);
    }

    @Test
    public void checkIfTokenInvalid(){
        Response test = playlistController.all("");

        Assertions.assertEquals(401, test.getStatus());
    }

    @Test
    public void checkIfTokenIsValid(){
        Response test = playlistController.all(TOKEN);

        Assertions.assertEquals(200, test.getStatus());
    }

    @Test
    public void checkIfAllPlaylistReturns200() {
        Response test = playlistController.all(TOKEN);

        Assertions.assertEquals(200, test.getStatus());
    }

    @Test
    public void checkIfEditPlaylistReturns200() {
        Response test = playlistController.editPlaylist(PLAYLISTID, TOKEN, playlistRequestDTO);

        Assertions.assertEquals(200, test.getStatus());
    }

    @Test
    public void checkIfEditPlaylistReturns401(){
        Response test = playlistController.editPlaylist(2, TOKEN, playlistRequestDTO);

        Assertions.assertEquals(401, test.getStatus());
    }

    @Test
    public void checkIfPlaylistDoesntExistsAndTokenIsInvalid(){
        Response test = playlistController.editPlaylist(2, "", playlistRequestDTO);

        Assertions.assertEquals(401, test.getStatus());
    }

    @Test
    public void checkIfAddPlaylistReturns200(){
        Response test = playlistController.addPlaylist(TOKEN, playlistRequestDTO);

        Assertions.assertEquals(200, test.getStatus());
    }

    @Test
    public void checkIfDeletePlaylistReturns200(){
        Response test = playlistController.deletePlaylist(PLAYLISTID, TOKEN);

        Assertions.assertEquals(200, test.getStatus());
    }

    @Test
    public void checkIfPlaylistDoesntExistsInDeletePlaylistReturns401(){
        Response test = playlistController.deletePlaylist(2, TOKEN);

        Assertions.assertEquals(401, test.getStatus());
    }

    @Test
    public void checkIfSetTracksReturns200(){
        Response test = playlistController.setTracks(PLAYLISTID, TOKEN);

        Assertions.assertEquals(200, test.getStatus());
    }

    @Test
    public void checkIfPlaylistDoesntExistsInSetTracksReturns401(){
        Response test = playlistController.setTracks(2, TOKEN);

        Assertions.assertEquals(401, test.getStatus());
    }

    @Test
    public void checkIfAddTracksToPlaylistReturns200(){
        //TrackDTO
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setId(TRACKID);
        trackDTO.setOfflineAvailable(true);

        Response test = playlistController.addTrackToPlaylist(PLAYLISTID, TOKEN, trackDTO);

        Assertions.assertEquals(200, test.getStatus());
    }

    @Test
    public void checkDeleteTrackFromPlaylistReturns200(){
        Response test = playlistController.deleteTrackFromPlaylist(PLAYLISTID, TRACKID, TOKEN);

        Assertions.assertEquals(200, test.getStatus());
    }

}
