package nl.han.dea.dave;

import nl.han.dea.dave.dto.TrackDTO;
import nl.han.dea.dave.dto.TracksDTO;
import nl.han.dea.dave.datasource.daos.TrackDao;
import nl.han.dea.dave.services.TrackService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class TrackServiceTest {

    private TrackService trackService;
    private TracksDTO tracksDTO;
    private TrackDTO trackOne;
    private TrackDTO trackTwo;
    private final int PLAYLISTIDONE = 1;
    private final int TRACKIDONE = 1;
    private final int PLAYLISTIDTWO = 2;
    private final int TRACKIDTWO = 2;

    @BeforeEach
    public void setup(){
        trackService = new TrackService();
        TrackDao trackDao = Mockito.mock(TrackDao.class);
        trackService.setTrackDao(trackDao);

        // TrackDTO
        List<Integer> tracksIdsOne = new ArrayList<>();
        List<Integer> tracksIdsTwo = new ArrayList<>();
        tracksIdsOne.add(1);
        tracksIdsTwo.add(2);
        List<TrackDTO> allTracks = new ArrayList<>();
        trackOne = new TrackDTO();
        trackTwo = new TrackDTO();
        trackOne.setId(TRACKIDONE);
        trackTwo.setId(TRACKIDTWO);
        allTracks.add(trackOne);
        allTracks.add(trackTwo);
        tracksDTO = new TracksDTO(allTracks);

        // GetAllTracks
        Mockito.when(trackDao.getAllTracks()).thenReturn(tracksDTO);

        // GetAllTracksId's from playlist one
        Mockito.when(trackDao.getTracksIdsFromPlaylistid(PLAYLISTIDONE)).thenReturn(tracksIdsOne);

        // GetAllTracksId's from playlist two
        Mockito.when(trackDao.getTracksIdsFromPlaylistid(PLAYLISTIDTWO)).thenReturn(tracksIdsTwo);
    }

    @Test
    public void checkIfGetAllTracksReturnsTracksDTO(){
        TracksDTO getAllTracks = trackService.allTracks();

        Assertions.assertEquals(tracksDTO, getAllTracks);
    }

    @Test
    public void checkIfGetTracksFromPlaylistIdReturnSameLengthAsTracksDTO() {
        TracksDTO getTracksFromPlaylistId = trackService.tracksFromPlaylistId(PLAYLISTIDONE);
        List<TrackDTO> allTracksFromPlaylist = new ArrayList<>();
        allTracksFromPlaylist.add(trackOne);
        TracksDTO tracksFromPlaylist = new TracksDTO(allTracksFromPlaylist);

        Assertions.assertEquals(tracksFromPlaylist.getTracks().size(), getTracksFromPlaylistId.getTracks().size());
    }

    @Test
    public void checkIfGetTrackByIdReturnsSameIdAsTrackOne(){
        TrackDTO getTrackById = trackService.getTrackById(TRACKIDONE);

        Assertions.assertEquals(trackOne.getId(), getTrackById.getId());
    }

    @Test
    public void checkIfTrackExists(){
        Boolean trackExists = trackService.trackExists(TRACKIDONE);

        Assertions.assertEquals(true, trackExists);
    }

    @Test
    public void checkIfTrackDoesntExists(){
        Boolean trackDoesntExists = trackService.trackExists(3);

        Assertions.assertEquals(false, trackDoesntExists);
    }

    @Test
    public void checkIfTrackExixstsInPlaylist(){
        Boolean trackExists = trackService.trackExistsInPlaylist(PLAYLISTIDONE, TRACKIDONE);

        Assertions.assertEquals(true, trackExists);
    }

    @Test
    public void checkIfTrackDoesntExixstsInPlaylist(){
        Boolean trackDoesntExists = trackService.trackExistsInPlaylist(PLAYLISTIDTWO, TRACKIDONE);

        Assertions.assertEquals(false, trackDoesntExists);
    }
}