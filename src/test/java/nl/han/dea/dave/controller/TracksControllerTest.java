package nl.han.dea.dave.controller;

import nl.han.dea.dave.controllers.TracksController;
import nl.han.dea.dave.services.TrackService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

public class TracksControllerTest {

    private TrackService trackService;
    private TracksController tracksController;

    @BeforeEach
    public void setup() {
        tracksController = new TracksController();
        trackService = Mockito.mock(TrackService.class);
    }

    @Test
    public void checkIfAllTracksReturns200() {
        tracksController.setTrackService(trackService);
        Response test = tracksController.allTracks();

        Assertions.assertEquals(200, test.getStatus());
    }
}
