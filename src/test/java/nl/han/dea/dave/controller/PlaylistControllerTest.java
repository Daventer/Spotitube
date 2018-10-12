package nl.han.dea.dave.controller;

import nl.han.dea.dave.controllers.LoginController;
import nl.han.dea.dave.controllers.PlaylistController;
import nl.han.dea.dave.controllers.dto.PlaylistDTO;
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

    private PlaylistService playlistService;
    private PlaylistController playlistController;
    private PlaylistsDTO playlistsDTO;
    private UserService userService;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    public void setup() {
        // playlist
        playlistController = new PlaylistController();
        playlistService = Mockito.mock(PlaylistService.class);
        playlistController.setPlaylistService(playlistService);

        // userService
        userService = Mockito.mock(UserService.class);
        playlistController.setUserService(userService);
        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUser("Dave");
        userResponseDTO.setToken("1234-1234-1234");

        // All playlists
        ArrayList<PlaylistDTO> playLists = new ArrayList<>();
        playLists.add(new PlaylistDTO());
        playlistsDTO = new PlaylistsDTO(playLists);
    }

    @Test
    public void getAllPlaylists() {
        Mockito.when(userService.tokenIsValid("1234-1234-1234")).thenReturn(true);
        Mockito.when(userService.getUserIdFromToken("1234-1234-1234")).thenReturn(1);
        Mockito.when(playlistService.allPlaylistsFromUser(1)).thenReturn(playlistsDTO);

        Response test = playlistController.all(userResponseDTO.getToken());

        Assertions.assertEquals(200, test.getStatus());
    }


}
