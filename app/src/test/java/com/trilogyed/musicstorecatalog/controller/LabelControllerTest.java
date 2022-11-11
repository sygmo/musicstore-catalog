package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.service.MusicstoreServiceLayer;
import com.trilogyed.musicstorecatalog.viewModel.LabelViewModel;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LabelController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LabelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicstoreServiceLayer musicstoreServiceLayer;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnNewLabelOnPost() throws Exception {
        LabelViewModel inLabel = new LabelViewModel();
        inLabel.setName("Whatever Label");
        inLabel.setWebsite("www.whatever.com");

        LabelViewModel outLabel = new LabelViewModel();
        outLabel.setId(5);
        outLabel.setName("Whatever Label");
        outLabel.setWebsite("www.whatever.com");

        doReturn(outLabel).when(this.musicstoreServiceLayer).createLabel(inLabel);

        mockMvc.perform(post("/label")
                        .content(mapper.writeValueAsString(inLabel))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(outLabel)));
    }

    @Test
    public void shouldReturnLabelById() throws Exception {
        LabelViewModel outLabel = new LabelViewModel();
        outLabel.setId(5);
        outLabel.setName("Whatever Label");
        outLabel.setWebsite("www.whatever.com");

        doReturn(outLabel).when(musicstoreServiceLayer).getLabel(5);

        mockMvc.perform(get("/label/5")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(outLabel)));
    }

    @Test
    public void shouldReturn204OnGoodUpdate() throws Exception {
        LabelViewModel inLabel = new LabelViewModel();
        inLabel.setId(5);
        inLabel.setName("Whatever Label");
        inLabel.setWebsite("www.whatever.org");

        doNothing().when(musicstoreServiceLayer).updateLabel(inLabel);

        mockMvc.perform(put("/label")
                        .content(mapper.writeValueAsString(inLabel))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404OnBadUpdate() throws Exception {
        LabelViewModel inLabel = new LabelViewModel();
        inLabel.setId(6);
        inLabel.setName("Whatever Label");
        inLabel.setWebsite("www.whatever.org");

        doThrow(new IllegalArgumentException("Label not found at given id.")).when(musicstoreServiceLayer).updateLabel(inLabel);

        mockMvc.perform(put("/label")
                        .content(mapper.writeValueAsString(inLabel))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteLabelAndReturnNoContent() throws Exception {
        doNothing().when(musicstoreServiceLayer).deleteLabel(5);

        mockMvc.perform(delete("/label/5")
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnAllLabels() throws Exception {
        List<LabelViewModel> labelViewModelList = new ArrayList<>();

        LabelViewModel outLabel = new LabelViewModel();
        outLabel.setId(5);
        outLabel.setName("Whatever Label");
        outLabel.setWebsite("www.whatever.com");

        labelViewModelList.add(outLabel);

        LabelViewModel outLabel1 = new LabelViewModel();
        outLabel1.setId(6);
        outLabel1.setName("Rival Label");
        outLabel1.setWebsite("www.rival.com");

        labelViewModelList.add(outLabel1);

        doReturn(labelViewModelList).when(musicstoreServiceLayer).getAllLabels();

        mockMvc.perform(get("/label")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(labelViewModelList)));

        doReturn(null).when(musicstoreServiceLayer).getAllLabels();

        mockMvc.perform(get("/label")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldFailCreateLabelWithInvalidName() throws Exception {
        LabelViewModel inLabel = new LabelViewModel();
        inLabel.setName("");
        inLabel.setWebsite("www.whatever.com");

        doReturn(null).when(musicstoreServiceLayer).createLabel(inLabel);

        mockMvc.perform(post("/label")
                        .content(mapper.writeValueAsString(inLabel))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldFailUpdateLabelWithInvalidWebsite() throws Exception {
        LabelViewModel inLabel = new LabelViewModel();
        inLabel.setId(5);
        inLabel.setName("Whatever Label");
        inLabel.setWebsite(null);

        doNothing().when(musicstoreServiceLayer).updateLabel(inLabel);

        mockMvc.perform(put("/label")
                        .content(mapper.writeValueAsString(inLabel))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldFailGetLabelWithBadId() throws Exception {
        LabelViewModel outLabel = new LabelViewModel();
        outLabel.setId(5);
        outLabel.setName("Whatever Label");
        outLabel.setWebsite("www.whatever.com");

        doReturn(null).when(musicstoreServiceLayer).getLabel(6);

        mockMvc.perform(get("/label/6")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}