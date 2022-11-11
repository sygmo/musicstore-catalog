package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.service.MusicstoreServiceLayer;
import com.trilogyed.musicstorecatalog.viewModel.ArtistViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/artist")
public class ArtistController {

    @Autowired
    MusicstoreServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistViewModel createArtist(@RequestBody @Valid ArtistViewModel artistViewModel) {
        artistViewModel = service.createArtist(artistViewModel);
        return artistViewModel;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArtistViewModel getArtist(@PathVariable("id") long artistId) {
        ArtistViewModel artistViewModel = service.getArtist(artistId);
        if (artistViewModel == null) {
            throw new IllegalArgumentException("Artist could not be retrieved for id: " + artistId);
        } else {
            return artistViewModel;
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtist(@RequestBody @Valid ArtistViewModel artistViewModel) {
        if (artistViewModel == null || artistViewModel.getId() < 1) {
            throw new IllegalArgumentException("Id in path must match id in view model");
        } else if (artistViewModel.getId() > 0) {
            service.updateArtist(artistViewModel);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable("id") long artistId) {
        service.deleteArtist(artistId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistViewModel> getAllArtists() {
        List<ArtistViewModel> artistViewModels = service.getAllArtists();
        if (artistViewModels == null || artistViewModels.isEmpty()) {
            throw new IllegalArgumentException("No artists were found");
        } else {
            return artistViewModels;
        }
    }
}
