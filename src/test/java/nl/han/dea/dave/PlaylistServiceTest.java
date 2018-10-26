package nl.han.dea.dave;

import nl.han.dea.dave.dto.PlaylistDTO;
import nl.han.dea.dave.dto.PlaylistsDTO;
import nl.han.dea.dave.dto.TrackDTO;
import nl.han.dea.dave.dto.TracksDTO;
import nl.han.dea.dave.datasource.daos.PlaylistDao;
import nl.han.dea.dave.services.PlaylistService;
import nl.han.dea.dave.services.TrackService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class PlaylistServiceTest {

    private PlaylistService playlistService;
    private PlaylistsDTO playlistsDTO;
    private PlaylistDTO playlistOne;
    private List<PlaylistDTO> playlistDTOS;

    private TrackService trackService;
    private TracksDTO tracksOne;

    @BeforeEach
    public void setup(){
        //PlaylistService
        playlistService =  new PlaylistService();
        PlaylistDao playlistDao = Mockito.mock(PlaylistDao.class);
        playlistService.setPlaylistDao(playlistDao);

        //TrackService
        trackService = Mockito.mock(TrackService.class);
        playlistService.setTrackService(trackService);

        // Make Track
        List<TrackDTO> allTracks = new ArrayList<>();
        TrackDTO trackOne = new TrackDTO();
        trackOne.setId(1);
        trackOne.setTitle("TrackOne");
        allTracks.add(trackOne);
        tracksOne = new TracksDTO(allTracks);

        // PlaylistDTO
        playlistOne = new PlaylistDTO();
        playlistDTOS =  new ArrayList<>();
        playlistOne.setId(1);
        playlistOne.setName("Dave");
        playlistOne.setTracks(tracksOne.getTracks());
        playlistDTOS.add(playlistOne);
        playlistsDTO = new PlaylistsDTO(playlistDTOS);

        // GetAllPlaylistsFromUser
        Mockito.when(playlistDao.getAllPlaylistsFromUser(1)).thenReturn(playlistsDTO);
        // GetAllTracksFromUser
        Mockito.when(trackService.tracksFromPlaylistId(1)).thenReturn(tracksOne);
        // GetPlaylistFromPlaylistId
        Mockito.when(playlistDao.getPlaylist(1)).thenReturn(playlistOne);
    }

    @Test
    public void checkIfPlaylistsFromUserReturnsSizeOfOne(){
        PlaylistsDTO getAllPlaylistsFromUser = playlistService.allPlaylistsFromUser(1);

        Assertions.assertEquals(playlistDTOS.size(), getAllPlaylistsFromUser.getPlaylists().size());
    }

    @Test
    public void checkIfIdOfPlaylistIsOne(){
        PlaylistDTO playlistDTO = playlistService.playlistById(1);

        Assertions.assertEquals(playlistOne.getId(), playlistDTO.getId());
    }

    @Test
    public void checkIfTracksByPlaylistIdReturnsSizeOfOne(){
        TracksDTO tracksDTO = trackService.tracksFromPlaylistId(1);

        Assertions.assertEquals(tracksDTO.getTracks().size(), tracksOne.getTracks().size());
    }

    @Test
    public void checkIfPlaylistExists(){
        Boolean playlistExists = playlistService.playlistExist(1);

        Assertions.assertEquals(true, playlistExists);
    }

    @Test
    public void checkIfPlaylistDoesntExists(){
        Boolean playlistDoesntExists = playlistService.playlistExist(2);

        Assertions.assertEquals(false, playlistDoesntExists);
    }
}