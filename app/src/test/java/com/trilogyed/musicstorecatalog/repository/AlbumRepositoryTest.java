package com.trilogyed.musicstorecatalog.repository;

import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.model.Label;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AlbumRepositoryTest {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    LabelRepository labelRepository;

    @Before
    public void setUp() throws Exception {
        albumRepository.deleteAll();
        artistRepository.deleteAll();
        labelRepository.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        albumRepository.deleteAll();
        labelRepository.deleteAll();
        artistRepository.deleteAll();
    }

    @Test
    public void shouldCreateGetDeleteAlbum() {
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
        Optional<Album> foundAlbum = albumRepository.findById(album.getId());

        assertTrue(foundAlbum.isPresent());
        assertEquals(album, foundAlbum.get());

        // Get and Update
        album.setListPrice(BigDecimal.valueOf(11.99));

        albumRepository.save(album);
        foundAlbum = albumRepository.findById(album.getId());

        assertTrue(foundAlbum.isPresent());
        assertEquals(album, foundAlbum.get());

        // Delete
        albumRepository.deleteById(album.getId());
        foundAlbum = albumRepository.findById(album.getId());

        assertFalse(foundAlbum.isPresent());
    }

    @Test
    public void shouldFindAllAlbums() {
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

        Album album1 = new Album();
        album1.setTitle("Self-Titled");
        album1.setArtistId(artist.getId());
        album1.setReleaseDate(LocalDate.parse("2022-10-31"));
        album1.setLabelId(label.getId());
        album1.setListPrice(BigDecimal.valueOf(12.99));

        album = albumRepository.save(album);
        album1 = albumRepository.save(album1);
        List<Album> allAlbums = new ArrayList<>();
        allAlbums.add(album);
        allAlbums.add(album1);

        List<Album> foundAllAlbums = albumRepository.findAll();

        assertEquals(allAlbums.size(), foundAllAlbums.size());
    }

}