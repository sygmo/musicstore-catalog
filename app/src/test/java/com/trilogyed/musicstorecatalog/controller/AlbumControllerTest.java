package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.service.MusicstoreServiceLayer;
import com.trilogyed.musicstorecatalog.viewModel.AlbumViewModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AlbumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicstoreServiceLayer musicstoreServiceLayer;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnNewAlbumOnPost() throws Exception {
        AlbumViewModel inAlbum = new AlbumViewModel();
        inAlbum.setTitle("Debut Album");
        inAlbum.setArtistId(5);
        inAlbum.setReleaseDate(LocalDate.parse("2022-04-23"));
        inAlbum.setLabelId(2);
        inAlbum.setListPrice(BigDecimal.valueOf(10.99));

        AlbumViewModel outAlbum = new AlbumViewModel();
        outAlbum.setId(3);
        outAlbum.setTitle("Debut Album");
        outAlbum.setArtistId(5);
        outAlbum.setReleaseDate(LocalDate.parse("2022-04-23"));
        outAlbum.setLabelId(2);
        outAlbum.setListPrice(BigDecimal.valueOf(10.99));

        doReturn(outAlbum).when(musicstoreServiceLayer).createAlbum(inAlbum);

        mockMvc.perform(post("/album")
                        .content(mapper.writeValueAsString(inAlbum))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(outAlbum)));
    }

    @Test
    public void shouldReturnAlbumById() throws Exception {
        AlbumViewModel outAlbum = new AlbumViewModel();
        outAlbum.setId(3);
        outAlbum.setTitle("Debut Album");
        outAlbum.setArtistId(5);
        outAlbum.setReleaseDate(LocalDate.parse("2022-04-23"));
        outAlbum.setLabelId(2);
        outAlbum.setListPrice(BigDecimal.valueOf(10.99));

        doReturn(outAlbum).when(musicstoreServiceLayer).getAlbum(3);

        mockMvc.perform(get("/album/3")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(outAlbum)));
    }

    @Test
    public void shouldReturn204OnGoodUpdate() throws Exception {
        AlbumViewModel inAlbum = new AlbumViewModel();
        inAlbum.setId(3);
        inAlbum.setTitle("Debut Album");
        inAlbum.setArtistId(5);
        inAlbum.setReleaseDate(LocalDate.parse("2022-04-23"));
        inAlbum.setLabelId(2);
        inAlbum.setListPrice(BigDecimal.valueOf(12.99));

        doNothing().when(musicstoreServiceLayer).updateAlbum(inAlbum);

        mockMvc.perform(put("/album")
                        .content(mapper.writeValueAsString(inAlbum))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404OnBadUpdate() throws Exception {
        AlbumViewModel inAlbum = new AlbumViewModel();
        inAlbum.setId(5);
        inAlbum.setTitle("Debut Album");
        inAlbum.setArtistId(5);
        inAlbum.setReleaseDate(LocalDate.parse("2022-04-23"));
        inAlbum.setLabelId(2);
        inAlbum.setListPrice(BigDecimal.valueOf(12.99));

        doThrow(new IllegalArgumentException("Album not found at given id.")).when(musicstoreServiceLayer).updateAlbum(inAlbum);

        mockMvc.perform(put("/album")
                        .content(mapper.writeValueAsString(inAlbum))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteAlbumAndReturnNoContent() throws Exception {
        doNothing().when(musicstoreServiceLayer).deleteArtist(3);

        mockMvc.perform(delete("/album/3")
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnAllAlbums() throws Exception {
        List<AlbumViewModel> albumViewModelList = new ArrayList<>();

        AlbumViewModel outAlbum = new AlbumViewModel();
        outAlbum.setId(3);
        outAlbum.setTitle("Debut Album");
        outAlbum.setArtistId(5);
        outAlbum.setReleaseDate(LocalDate.parse("2022-04-23"));
        outAlbum.setLabelId(2);
        outAlbum.setListPrice(BigDecimal.valueOf(10.99));

        albumViewModelList.add(outAlbum);

        AlbumViewModel outAlbum1 = new AlbumViewModel();
        outAlbum1.setId(4);
        outAlbum1.setTitle("Self-Titled Album");
        outAlbum1.setArtistId(3);
        outAlbum1.setReleaseDate(LocalDate.parse("2022-10-23"));
        outAlbum1.setLabelId(2);
        outAlbum1.setListPrice(BigDecimal.valueOf(9.99));

        albumViewModelList.add(outAlbum1);

        doReturn(albumViewModelList).when(musicstoreServiceLayer).getAllAlbums();

        mockMvc.perform(get("/album")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(albumViewModelList)));

        doReturn(null).when(musicstoreServiceLayer).getAllAlbums();

        mockMvc.perform(get("/album")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldFailCreateAlbumWithInvalidPrice() throws Exception {
        AlbumViewModel inAlbum = new AlbumViewModel();
        inAlbum.setTitle("Debut Album");
        inAlbum.setArtistId(5);
        inAlbum.setReleaseDate(LocalDate.parse("2022-04-23"));
        inAlbum.setLabelId(2);
        inAlbum.setListPrice(BigDecimal.valueOf(1000.00));

        doReturn(null).when(musicstoreServiceLayer).createAlbum(inAlbum);

        mockMvc.perform(post("/album")
                        .content(mapper.writeValueAsString(inAlbum))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldFailUpdateAlbumWithInvalidTitle() throws Exception {
        AlbumViewModel inAlbum = new AlbumViewModel();
        inAlbum.setTitle("");
        inAlbum.setArtistId(5);
        inAlbum.setReleaseDate(LocalDate.parse("2022-04-23"));
        inAlbum.setLabelId(2);
        inAlbum.setListPrice(BigDecimal.valueOf(10.99));

        doNothing().when(musicstoreServiceLayer).updateAlbum(inAlbum);

        mockMvc.perform(put("/album")
                        .content(mapper.writeValueAsString(inAlbum))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldFailGetAlbumWithBadId() throws Exception {
        AlbumViewModel outAlbum = new AlbumViewModel();
        outAlbum.setId(3);
        outAlbum.setTitle("Debut Album");
        outAlbum.setArtistId(5);
        outAlbum.setReleaseDate(LocalDate.parse("2022-04-23"));
        outAlbum.setLabelId(2);
        outAlbum.setListPrice(BigDecimal.valueOf(10.99));

        doReturn(null).when(musicstoreServiceLayer).getAlbum(4);

        mockMvc.perform(get("/album/4")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}