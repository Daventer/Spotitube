package nl.han.dea.dave.services;

import nl.han.dea.dave.controllers.dto.*;
import nl.han.dea.dave.datasource.daos.PlaylistDao;

import javax.inject.Inject;

public class PlaylistService {

    private TrackService trackService;
    private PlaylistDao playlistDao;


    public PlaylistsDTO allPlaylistsFromUser(int userId) {

        PlaylistsDTO playlists = playlistDao.getAllPlaylistsFromUser(userId);

        int totalDuration = 0;

        for (PlaylistDTO playlist : playlists.getPlaylists()) {
            TracksDTO tracks = trackService.tracksFromPlaylistId(playlist.getId());
            for (TrackDTO track : tracks.getTracks()) {
                totalDuration += track.getDuration();
            }
            playlist.setTracks(tracks.getTracks());
        }

        playlists.setLength(totalDuration);

        return playlists;
    }

    public PlaylistDTO playlistById(int id) {
        return playlistDao.getPlaylist(id);
    }

    public TracksDTO tracksByPlaylistId(int id) {
        PlaylistDTO playlistDTO = playlistById(id);

        TracksDTO tracks = trackService.tracksFromPlaylistId(id);

        playlistDTO.setTracks(tracks.getTracks());

        return new TracksDTO(playlistDTO.getTracks());
    }

//    public TracksDTO getTracksFromPlaylistId(int id) {
//        PlaylistDTO playlistDTO = playlistById(id);
//
//        TracksDTO tracks = trackService.tracksFromPlaylistId(id);
//
//        playlistDTO.setTracks(tracks.getTracks());
//
//        return new TracksDTO(playlistDTO.getTracks());
//    }

    public Boolean playlistExist(int playlistId) {
        return playlistDao.getPlaylist(playlistId) != null;
    }

    public void updatePlaylistName(int playlistId, String name) {
        playlistDao.updatePlaylistName(playlistId, name);
    }

    public void addPlaylist(PlaylistRequestDTO playlistDTO) {
        playlistDao.addPlaylist(playlistDTO);
    }

    public void deletePlaylist(int playlistId) {
        deleteTracksFromPlaylist(playlistId);
        playlistDao.deletePlaylist(playlistId);
    }

    public void deleteTracksFromPlaylist(int playlistId) {
        playlistDao.deleteTracksFromPlaylist(playlistId);
    }

    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

    @Inject
    public void setPlaylistDao(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }
}
