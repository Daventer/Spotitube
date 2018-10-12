package nl.han.dea.dave;

import nl.han.dea.dave.controllers.LoginController;
import nl.han.dea.dave.controllers.PlaylistController;
import nl.han.dea.dave.controllers.dto.PlaylistDTO;
import nl.han.dea.dave.controllers.dto.PlaylistRequestDTO;
import nl.han.dea.dave.controllers.dto.PlaylistsDTO;
import nl.han.dea.dave.controllers.dto.UserResponseDTO;
import nl.han.dea.dave.services.PlaylistService;
import nl.han.dea.dave.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class PlaylistControllerTest {

    private final String TOKEN = "1234-1234-1234";
    private final int USERID = 1;
    private final int PLAYLISTID = 1;

    private PlaylistService playlistService;
    private PlaylistController playlistController;
    private PlaylistsDTO playlistsDTO;
    private PlaylistRequestDTO playlistRequestDTO;
    private UserService userService;

    @BeforeEach
    public void setup() {
        // playlist
        playlistController = new PlaylistController();
        playlistService = Mockito.mock(PlaylistService.class);
        playlistController.setPlaylistService(playlistService);
        playlistRequestDTO = new PlaylistRequestDTO();

        // userService
        userService = Mockito.mock(UserService.class);
        playlistController.setUserService(userService);

        // All playlists
        ArrayList<PlaylistDTO> playLists = new ArrayList<>();
        playLists.add(new PlaylistDTO());
        playlistsDTO = new PlaylistsDTO(playLists);

        // MOCKITO
        // control token
        Mockito.when(userService.tokenIsValid(TOKEN)).thenReturn(true);
        // get userId
        Mockito.when(userService.getUserIdFromToken(TOKEN)).thenReturn(USERID);
        // get all plalists from user
        Mockito.when(playlistService.allPlaylistsFromUser(USERID)).thenReturn(playlistsDTO);
        // PlaylistExists
        Mockito.when(playlistService.playlistExist(PLAYLISTID)).thenReturn(true);
        // get all plalists from user
        Mockito.when(playlistService.allPlaylistsFromUser(USERID)).thenReturn(playlistsDTO);

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
    public void checkIfPlaylistDoesntExistsInEditPlaylistReturns401(){
        Response test = playlistController.editPlaylist(2, TOKEN, playlistRequestDTO);

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

}
