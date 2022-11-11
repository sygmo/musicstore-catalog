package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.service.MusicstoreServiceLayer;
import com.trilogyed.musicstorecatalog.viewModel.ArtistViewModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicstoreServiceLayer musicstoreServiceLayer;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnNewArtistOnPost() throws Exception {
        ArtistViewModel inArtist = new ArtistViewModel();
        inArtist.setName("Sydney Mercier");
        inArtist.setInstagram("sydneyInsta");
        inArtist.setTwitter("theRealSydney");

        ArtistViewModel outArtist = new ArtistViewModel();
        outArtist.setId(10);
        outArtist.setName("Sydney Mercier");
        outArtist.setInstagram("sydneyInsta");
        outArtist.setTwitter("theRealSydney");

        doReturn(outArtist).when(this.musicstoreServiceLayer).createArtist(inArtist);

        mockMvc.perform(post("/artist")
                            .content(mapper.writeValueAsString(inArtist))
                            .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(outArtist)));
    }

    @Test
    public void shouldReturnArtistById() throws Exception {
        ArtistViewModel outArtist = new ArtistViewModel();
        outArtist.setId(10);
        outArtist.setName("Sydney Mercier");
        outArtist.setInstagram("sydneyInsta");
        outArtist.setTwitter("theRealSydney");

        doReturn(outArtist).when(this.musicstoreServiceLayer).getArtist(10);

        mockMvc.perform(get("/artist/10")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(outArtist)));
    }

    @Test
    public void shouldReturn204OnGoodUpdate() throws Exception {
        ArtistViewModel inArtist = new ArtistViewModel();
        inArtist.setId(10);
        inArtist.setName("Sydney Mercier");
        inArtist.setInstagram("sydneyInsta");
        inArtist.setTwitter("theAmazingSydney");

        doNothing().when(this.musicstoreServiceLayer).updateArtist(inArtist);

        mockMvc.perform(put("/artist")
                        .content(mapper.writeValueAsString(inArtist))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404OnBadUpdate() throws Exception {
        ArtistViewModel inArtist = new ArtistViewModel();
        inArtist.setId(11);
        inArtist.setName("Sydney Mercier");
        inArtist.setInstagram("sydneyInsta");
        inArtist.setTwitter("theAmazingSydney");

        doThrow(new IllegalArgumentException("Artist not found at given id.")).when(musicstoreServiceLayer).updateArtist(inArtist);

        mockMvc.perform(put("/artist")
                        .content(mapper.writeValueAsString(inArtist))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteArtistAndReturnNoContent() throws Exception {
        doNothing().when(musicstoreServiceLayer).deleteArtist(10);

        mockMvc.perform(delete("/artist/10")
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnAllArtists() throws Exception {
        List<ArtistViewModel> artistViewModelList = new ArrayList<>();

        ArtistViewModel outArtist = new ArtistViewModel();
        outArtist.setId(10);
        outArtist.setName("Sydney Mercier");
        outArtist.setInstagram("sydneyInsta");
        outArtist.setTwitter("theRealSydney");

        artistViewModelList.add(outArtist);

        ArtistViewModel outArtist1 = new ArtistViewModel();
        outArtist1.setId(11);
        outArtist1.setName("Rachel Gorman");
        outArtist1.setInstagram("rachelInsta");
        outArtist1.setTwitter("raygor");

        artistViewModelList.add(outArtist1);

        doReturn(artistViewModelList).when(musicstoreServiceLayer).getAllArtists();

        mockMvc.perform(get("/artist")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(artistViewModelList)));

        doReturn(null).when(musicstoreServiceLayer).getAllArtists();

        mockMvc.perform(get("/artist")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldFailCreateArtistWithInvalidTwitter() throws Exception {
        ArtistViewModel inArtist = new ArtistViewModel();
        inArtist.setName("Sydney Mercier");
        inArtist.setInstagram("sydneyInsta");

        doReturn(null).when(musicstoreServiceLayer).createArtist(inArtist);

        mockMvc.perform(post("/artist")
                        .content(mapper.writeValueAsString(inArtist))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldFailUpdateArtistWithInvalidInstagram() throws Exception {
        ArtistViewModel inArtist = new ArtistViewModel();
        inArtist.setId(10);
        inArtist.setName("Sydney Mercier");
        inArtist.setInstagram("");
        inArtist.setTwitter("theRealSydney");

        doNothing().when(musicstoreServiceLayer).updateArtist(inArtist);

        mockMvc.perform(put("/artist")
                        .content(mapper.writeValueAsString(inArtist))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldFailGetArtistWithBadId() throws Exception {
        ArtistViewModel outArtist = new ArtistViewModel();
        outArtist.setId(10);
        outArtist.setName("Sydney Mercier");
        outArtist.setInstagram("sydneyInsta");
        outArtist.setTwitter("theRealSydney");

        doReturn(null).when(musicstoreServiceLayer).getArtist(11);

        mockMvc.perform(get("/artist/11")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}