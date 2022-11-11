package com.trilogyed.musicstorecatalog.repository;

import com.trilogyed.musicstorecatalog.model.Artist;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ArtistRepositoryTest {

    @Autowired
    ArtistRepository artistRepository;

    @Before
    public void setUp() throws Exception {
        artistRepository.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        artistRepository.deleteAll();
    }

    @Test
    public void shouldCreateGetDeleteArtist() {
        // Create
        Artist artist = new Artist();
        artist.setName("Sydney Mercier");
        artist.setInstagram("sydneyInsta");
        artist.setTwitter("theRealSydney");

        artist = artistRepository.save(artist);
        Optional<Artist> foundArtist = artistRepository.findById(artist.getId());

        assertTrue(foundArtist.isPresent());
        assertEquals(artist, foundArtist.get());

        // Get and Update
        artist.setTwitter("theAmazingSydney");

        artistRepository.save(artist);
        foundArtist = artistRepository.findById(artist.getId());

        assertTrue(foundArtist.isPresent());
        assertEquals(artist, foundArtist.get());

        // Delete
        artistRepository.deleteById(artist.getId());
        foundArtist = artistRepository.findById(artist.getId());

        assertFalse(foundArtist.isPresent());
    }

    @Test
    public void shouldFindAllArtists() {
        Artist artist = new Artist();
        artist.setName("Sydney Mercier");
        artist.setInstagram("sydneyInsta");
        artist.setTwitter("theRealSydney");

        Artist artist1 = new Artist();
        artist1.setName("Rachel Gorman");
        artist1.setInstagram("rachelInsta");
        artist1.setTwitter("raygor");

        artist = artistRepository.save(artist);
        artist1 = artistRepository.save(artist1);
        List<Artist> allArtists = new ArrayList<>();
        allArtists.add(artist);
        allArtists.add(artist1);

        List<Artist> foundAllArtists = artistRepository.findAll();

        assertEquals(allArtists.size(), foundAllArtists.size());
    }

}