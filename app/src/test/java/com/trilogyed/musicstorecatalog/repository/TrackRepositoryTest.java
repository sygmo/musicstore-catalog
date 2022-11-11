package com.trilogyed.musicstorecatalog.repository;

import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.model.Track;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TrackRepositoryTest {

    @Autowired
    TrackRepository trackRepository;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    LabelRepository labelRepository;

    @Before
    public void setUp() throws Exception {
        trackRepository.deleteAll();
        albumRepository.deleteAll();
        artistRepository.deleteAll();
        labelRepository.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        trackRepository.deleteAll();
        albumRepository.deleteAll();
        labelRepository.deleteAll();
        artistRepository.deleteAll();
    }

    @Test
    public void shouldCreateGetDeleteTrack() {
        // Create
        Artist artist = new Artist();
        artist.setName("Sydney Mercier");
        artist.setInstagram("sydneyInsta");
        artist.setTwitter("theRealSydney");
        artist = artistRepository.save(artist);

        Label label = new Label();
        label.setName("Whatever Label");
        label.setWebsite("www.whatever.com");
        label = labelRepository.save(label);

        Album album = new Album();
        album.setTitle("Debut Album");
        album.setArtistId(artist.getId());
        album.setReleaseDate(LocalDate.parse("2022-04-23"));
        album.setLabelId(label.getId());
        album.setListPrice(BigDecimal.valueOf(10.99));
        album = albumRepository.save(album);

        Track track = new Track();
        track.setTitle("Cool Melody");
        track.setAlbumId(album.getId());
        track.setRunTime(360);

        track = trackRepository.save(track);
        Optional<Track> foundTrack = trackRepository.findById(track.getId());

        assertTrue(foundTrack.isPresent());
        assertEquals(track, foundTrack.get());

        // Get and Update
        track.setRunTime(366);

        trackRepository.save(track);
        foundTrack = trackRepository.findById(track.getId());

        assertTrue(foundTrack.isPresent());
        assertEquals(track, foundTrack.get());

        // Delete
        trackRepository.deleteById(track.getId());
        foundTrack = trackRepository.findById(track.getId());

        assertFalse(foundTrack.isPresent());
    }

    @Test
    public void shouldFindAllTracks() {
        Artist artist = new Artist();
        artist.setName("Sydney Mercier");
        artist.setInstagram("sydneyInsta");
        artist.setTwitter("theRealSydney");
        artist = artistRepository.save(artist);

        Label label = new Label();
        label.setName("Whatever Label");
        label.setWebsite("www.whatever.com");
        label = labelRepository.save(label);

        Album album = new Album();
        album.setTitle("Debut Album");
        album.setArtistId(artist.getId());
        album.setReleaseDate(LocalDate.parse("2022-04-23"));
        album.setLabelId(label.getId());
        album.setListPrice(BigDecimal.valueOf(10.99));
        album = albumRepository.save(album);

        Track track = new Track();
        track.setTitle("Cool Melody");
        track.setAlbumId(album.getId());
        track.setRunTime(360);

        Track track1 = new Track();
        track1.setTitle("Awesome Guitar Solo");
        track1.setAlbumId(album.getId());
        track1.setRunTime(255);

        track = trackRepository.save(track);
        track1 = trackRepository.save(track1);
        List<Track> allTracks = new ArrayList<>();
        allTracks.add(track);
        allTracks.add(track1);

        List<Track> foundAllTracks = trackRepository.findAll();

        assertEquals(allTracks.size(), foundAllTracks.size());
    }

}