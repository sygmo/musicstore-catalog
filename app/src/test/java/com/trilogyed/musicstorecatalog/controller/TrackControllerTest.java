package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.service.MusicstoreServiceLayer;
import com.trilogyed.musicstorecatalog.viewModel.TrackViewModel;
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
@WebMvcTest(TrackController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicstoreServiceLayer musicstoreServiceLayer;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnNewTrackOnPost() throws Exception {
        TrackViewModel inTrack = new TrackViewModel();
        inTrack.setAlbumId(5);
        inTrack.setTitle("Cool Melody");
        inTrack.setRunTime(360);

        TrackViewModel outTrack = new TrackViewModel();
        outTrack.setId(10);
        outTrack.setAlbumId(5);
        outTrack.setTitle("Cool Melody");
        outTrack.setRunTime(360);

        doReturn(outTrack).when(this.musicstoreServiceLayer).createTrack(inTrack);

        mockMvc.perform(post("/track")
                        .content(mapper.writeValueAsString(inTrack))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(outTrack)));
    }

    @Test
    public void shouldReturnTrackById() throws Exception {
        TrackViewModel outTrack = new TrackViewModel();
        outTrack.setId(10);
        outTrack.setAlbumId(5);
        outTrack.setTitle("Cool Melody");
        outTrack.setRunTime(360);

        doReturn(outTrack).when(this.musicstoreServiceLayer).getTrack(10);

        mockMvc.perform(get("/track/10")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(outTrack)));
    }

    @Test
    public void shouldReturn204OnGoodUpdate() throws Exception {
        TrackViewModel inTrack = new TrackViewModel();
        inTrack.setId(10);
        inTrack.setAlbumId(5);
        inTrack.setTitle("Cool Melody");
        inTrack.setRunTime(366);

        doNothing().when(this.musicstoreServiceLayer).updateTrack(inTrack);

        mockMvc.perform(put("/track")
                        .content(mapper.writeValueAsString(inTrack))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404OnBadUpdate() throws Exception {
        TrackViewModel inTrack = new TrackViewModel();
        inTrack.setId(11);
        inTrack.setAlbumId(5);
        inTrack.setTitle("Cool Melody");
        inTrack.setRunTime(366);

        doThrow(new IllegalArgumentException("Track not found at given id.")).when(musicstoreServiceLayer).updateTrack(inTrack);

        mockMvc.perform(put("/track")
                        .content(mapper.writeValueAsString(inTrack))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteTrackAndReturnNoContent() throws Exception {
        doNothing().when(musicstoreServiceLayer).deleteTrack(10);

        mockMvc.perform(delete("/track/10")
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnAllTracks() throws Exception {
        List<TrackViewModel> trackViewModelList = new ArrayList<>();

        TrackViewModel outTrack = new TrackViewModel();
        outTrack.setId(10);
        outTrack.setAlbumId(5);
        outTrack.setTitle("Cool Melody");
        outTrack.setRunTime(360);

        trackViewModelList.add(outTrack);

        TrackViewModel outTrack1 = new TrackViewModel();
        outTrack1.setId(11);
        outTrack1.setAlbumId(5);
        outTrack1.setTitle("Awesome Guitar Solo");
        outTrack1.setRunTime(255);

        trackViewModelList.add(outTrack1);

        doReturn(trackViewModelList).when(musicstoreServiceLayer).getAllTracks();

        mockMvc.perform(get("/track")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(trackViewModelList)));

        doReturn(null).when(musicstoreServiceLayer).getAllTracks();

        mockMvc.perform(get("/track")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldFailCreateTrackWithInvalidTitle() throws Exception {
        TrackViewModel inTrack = new TrackViewModel();
        inTrack.setAlbumId(5);
        inTrack.setTitle("");
        inTrack.setRunTime(360);

        doReturn(null).when(musicstoreServiceLayer).createTrack(inTrack);

        mockMvc.perform(post("/track")
                        .content(mapper.writeValueAsString(inTrack))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldFailGetTrackWithBadId() throws Exception {
        TrackViewModel outTrack = new TrackViewModel();
        outTrack.setId(10);
        outTrack.setAlbumId(5);
        outTrack.setTitle("Cool Melody");
        outTrack.setRunTime(360);

        doReturn(null).when(musicstoreServiceLayer).getTrack(11);

        mockMvc.perform(get("/track/11")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}