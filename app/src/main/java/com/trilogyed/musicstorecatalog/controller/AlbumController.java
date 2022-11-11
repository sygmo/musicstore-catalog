package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.service.MusicstoreServiceLayer;
import com.trilogyed.musicstorecatalog.viewModel.AlbumViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/album")
public class AlbumController {

    @Autowired
    MusicstoreServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumViewModel createAlbum(@RequestBody @Valid AlbumViewModel albumViewModel) {
        albumViewModel = service.createAlbum(albumViewModel);
        return albumViewModel;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumViewModel getAlbum(@PathVariable("id") long albumId) {
        AlbumViewModel albumViewModel = service.getAlbum(albumId);
        if (albumViewModel == null) {
            throw new IllegalArgumentException("Album could not be found for id: " + albumId);
        } else {
            return albumViewModel;
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbum(@RequestBody @Valid AlbumViewModel albumViewModel) {
        if (albumViewModel == null || albumViewModel.getId() < 1) {
            throw new IllegalArgumentException("Id must match id in the view model");
        } else if (albumViewModel.getId() > 0) {
            service.updateAlbum(albumViewModel);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable("id") long albumId) {
        service.deleteAlbum(albumId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumViewModel> getAllAlbums() {
        List<AlbumViewModel> albumViewModels = service.getAllAlbums();
        if (albumViewModels == null || albumViewModels.isEmpty()) {
            throw new IllegalArgumentException("No albums were found");
        } else {
            return albumViewModels;
        }
    }
}
