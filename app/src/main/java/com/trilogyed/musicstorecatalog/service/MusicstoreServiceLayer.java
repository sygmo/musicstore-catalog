package com.trilogyed.musicstorecatalog.service;

import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.model.Track;
import com.trilogyed.musicstorecatalog.repository.AlbumRepository;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
import com.trilogyed.musicstorecatalog.repository.LabelRepository;
import com.trilogyed.musicstorecatalog.repository.TrackRepository;
import com.trilogyed.musicstorecatalog.viewModel.AlbumViewModel;
import com.trilogyed.musicstorecatalog.viewModel.ArtistViewModel;
import com.trilogyed.musicstorecatalog.viewModel.LabelViewModel;
import com.trilogyed.musicstorecatalog.viewModel.TrackViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MusicstoreServiceLayer {

    AlbumRepository albumRepo;
    ArtistRepository artistRepo;
    LabelRepository labelRepo;
    TrackRepository trackRepo;

    @Autowired
    public MusicstoreServiceLayer(AlbumRepository albumRepo, ArtistRepository artistRepo,
                                  LabelRepository labelRepo, TrackRepository trackRepo) {
        this.albumRepo = albumRepo;
        this.artistRepo = artistRepo;
        this.labelRepo = labelRepo;
        this.trackRepo = trackRepo;
    }

    // Album service layer
    public AlbumViewModel createAlbum(AlbumViewModel albumViewModel) {
        if (albumViewModel == null) {
            throw new IllegalArgumentException("Album object is null!");
        }

        Album album = new Album();
        album.setTitle(albumViewModel.getTitle());
        album.setArtistId(albumViewModel.getArtistId());
        album.setReleaseDate(albumViewModel.getReleaseDate());
        album.setLabelId(albumViewModel.getLabelId());
        album.setListPrice(albumViewModel.getListPrice());

        albumViewModel.setId(albumRepo.save(album).getId());
        return albumViewModel;
    }

    public AlbumViewModel getAlbum(long id) {
        Album album = albumRepo.findById(id).orElse(null);
        if (album == null) {
            return null;
        } else {
            return buildAlbumViewModel(album);
        }
    }

    public void updateAlbum(AlbumViewModel albumViewModel) {
        if (albumViewModel == null) {
            throw new IllegalArgumentException("Album object is null!");
        }

        if (this.getAlbum(albumViewModel.getId()) == null) {
            throw new IllegalArgumentException("Album does not exist to update.");
        }

        Album album = new Album();
        album.setId(albumViewModel.getId());
        album.setTitle(albumViewModel.getTitle());
        album.setArtistId(albumViewModel.getArtistId());
        album.setReleaseDate(albumViewModel.getReleaseDate());
        album.setLabelId(albumViewModel.getLabelId());
        album.setListPrice(albumViewModel.getListPrice());

        albumRepo.save(album);
    }

    public void deleteAlbum(long id) {
        albumRepo.deleteById(id);
    }

    public List<AlbumViewModel> getAllAlbums() {
        List<Album> albumList = albumRepo.findAll();
        List<AlbumViewModel> avmList = new ArrayList<>();

        if (albumList == null) {
            return null;
        } else {
            albumList.stream().forEach(a -> avmList.add(buildAlbumViewModel(a)));
        }
        return avmList;
    }

    // Artist service layer
    public ArtistViewModel createArtist(ArtistViewModel artistViewModel) {
        if (artistViewModel == null) {
            throw new IllegalArgumentException("Artist object is null!");
        }

        Artist artist = new Artist();
        artist.setName(artistViewModel.getName());
        artist.setInstagram(artistViewModel.getInstagram());
        artist.setTwitter(artistViewModel.getTwitter());

        artistViewModel.setId(artistRepo.save(artist).getId());
        return artistViewModel;
    }

    public ArtistViewModel getArtist(long id) {
        Artist artist = artistRepo.findById(id).orElse(null);
        if (artist == null) {
            return null;
        } else {
            return buildArtistViewModel(artist);
        }
    }

    public void updateArtist(ArtistViewModel artistViewModel) {
        if (artistViewModel == null) {
            throw new IllegalArgumentException("Artist object is null!");
        }

        if (this.getArtist(artistViewModel.getId()) == null) {
            throw new IllegalArgumentException("Artist does not exist to update.");
        }

        Artist artist = new Artist();
        artist.setId(artistViewModel.getId());
        artist.setName(artistViewModel.getName());
        artist.setInstagram(artistViewModel.getInstagram());
        artist.setTwitter(artistViewModel.getTwitter());

        artistRepo.save(artist);
    }

    public void deleteArtist(long id) {
        artistRepo.deleteById(id);
    }

    public List<ArtistViewModel> getAllArtists() {
        List<Artist> artistList = artistRepo.findAll();
        List<ArtistViewModel> avmList = new ArrayList<>();

        if (artistList == null) {
            return null;
        } else {
            artistList.stream().forEach(a -> avmList.add(buildArtistViewModel(a)));
        }
        return avmList;
    }

    // Label service layer
    public LabelViewModel createLabel(LabelViewModel labelViewModel) {
        if (labelViewModel == null) {
            throw new IllegalArgumentException("Label object is null!");
        }

        Label label = new Label();
        label.setName(labelViewModel.getName());
        label.setWebsite(labelViewModel.getWebsite());

        labelViewModel.setId(labelRepo.save(label).getId());
        return labelViewModel;
    }

    public LabelViewModel getLabel(long id) {
        Label label = labelRepo.findById(id).orElse(null);
        if (label == null) {
            return null;
        } else {
            return buildLabelViewModel(label);
        }
    }

    public void updateLabel(LabelViewModel labelViewModel) {
        if (labelViewModel == null) {
            throw new IllegalArgumentException("Label object is null!");
        }

        if (this.getLabel(labelViewModel.getId()) == null) {
            throw new IllegalArgumentException("Label does not exist to update.");
        }

        Label label = new Label();
        label.setId(labelViewModel.getId());
        label.setName(labelViewModel.getName());
        label.setWebsite(labelViewModel.getWebsite());

        labelRepo.save(label);
    }

    public void deleteLabel(long id) {
        labelRepo.deleteById(id);
    }

    public List<LabelViewModel> getAllLabels() {
        List<Label> labelList = labelRepo.findAll();
        List<LabelViewModel> lvmList = new ArrayList<>();

        if (labelList == null) {
            return null;
        } else {
            labelList.stream().forEach(l -> lvmList.add(buildLabelViewModel(l)));
        }

        return lvmList;
    }

    // Track service layer
    public TrackViewModel createTrack(TrackViewModel trackViewModel) {
        if (trackViewModel == null) {
            throw new IllegalArgumentException("Track object is null!");
        }

        Track track = new Track();
        track.setAlbumId(trackViewModel.getAlbumId());
        track.setTitle(trackViewModel.getTitle());
        track.setRunTime(trackViewModel.getRunTime());

        trackViewModel.setId(trackRepo.save(track).getId());
        return trackViewModel;
    }

    public TrackViewModel getTrack(long id) {
        Track track = trackRepo.findById(id).orElse(null);
        if (track == null) {
            return null;
        } else {
            return buildTrackViewModel(track);
        }
    }

    public void updateTrack(TrackViewModel trackViewModel) {
        if (trackViewModel == null) {
            throw new IllegalArgumentException("Track object is null!");
        }

        if (this.getTrack(trackViewModel.getId()) == null) {
            throw new IllegalArgumentException("Track does not exist to update.");
        }

        Track track = new Track();
        track.setId(trackViewModel.getId());
        track.setAlbumId(trackViewModel.getAlbumId());
        track.setTitle(trackViewModel.getTitle());
        track.setRunTime(trackViewModel.getRunTime());

        trackRepo.save(track);
    }

    public void deleteTrack(long id) {
        trackRepo.deleteById(id);
    }

    public List<TrackViewModel> getAllTracks() {
        List<Track> trackList = trackRepo.findAll();
        List<TrackViewModel> tvmList = new ArrayList<>();

        if (trackList == null) {
            return null;
        } else {
            trackList.stream().forEach(t -> tvmList.add(buildTrackViewModel(t)));
        }

        return tvmList;
    }

    // Helper methods
    public AlbumViewModel buildAlbumViewModel(Album album) {
        AlbumViewModel albumViewModel = new AlbumViewModel();
        albumViewModel.setId(album.getId());
        albumViewModel.setTitle(album.getTitle());
        albumViewModel.setArtistId(album.getArtistId());
        albumViewModel.setReleaseDate(album.getReleaseDate());
        albumViewModel.setLabelId(album.getLabelId());
        albumViewModel.setListPrice(album.getListPrice());

        return albumViewModel;
    }

    public ArtistViewModel buildArtistViewModel(Artist artist) {
        ArtistViewModel artistViewModel = new ArtistViewModel();
        artistViewModel.setId(artist.getId());
        artistViewModel.setName(artist.getName());
        artistViewModel.setInstagram(artist.getInstagram());
        artistViewModel.setTwitter(artist.getTwitter());

        return artistViewModel;
    }

    public LabelViewModel buildLabelViewModel(Label label) {
        LabelViewModel labelViewModel = new LabelViewModel();
        labelViewModel.setId(label.getId());
        labelViewModel.setName(label.getName());
        labelViewModel.setWebsite(label.getWebsite());

        return labelViewModel;
    }

    public TrackViewModel buildTrackViewModel(Track track) {
        TrackViewModel trackViewModel = new TrackViewModel();
        trackViewModel.setId(track.getId());
        trackViewModel.setAlbumId(track.getAlbumId());
        trackViewModel.setTitle(track.getTitle());
        trackViewModel.setRunTime(track.getRunTime());

        return trackViewModel;
    }
}
