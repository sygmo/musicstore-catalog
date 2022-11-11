package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.service.MusicstoreServiceLayer;
import com.trilogyed.musicstorecatalog.viewModel.TrackViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/track")
public class TrackController {

    @Autowired
    MusicstoreServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrackViewModel createTrack(@RequestBody @Valid TrackViewModel trackViewModel) {
        trackViewModel = service.createTrack(trackViewModel);
        return trackViewModel;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackViewModel getTrack(@PathVariable("id") long trackId) {
        TrackViewModel trackViewModel = service.getTrack(trackId);
        if (trackViewModel == null) {
            throw new IllegalArgumentException("Track could not be retrieved for id: " + trackId);
        } else {
            return trackViewModel;
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrack(@RequestBody @Valid TrackViewModel trackViewModel) {
        if (trackViewModel == null || trackViewModel.getId() < 1) {
            throw new IllegalArgumentException("Id must match id in the view model");
        } else if (trackViewModel.getId() > 0) {
            service.updateTrack(trackViewModel);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrack(@PathVariable("id") long trackId) {
        service.deleteTrack(trackId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TrackViewModel> getAllTracks() {
        List<TrackViewModel> trackViewModels = service.getAllTracks();
        if (trackViewModels == null || trackViewModels.isEmpty()) {
            throw new IllegalArgumentException("No tracks were found");
        } else {
            return trackViewModels;
        }
    }
}
