package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.service.MusicstoreServiceLayer;
import com.trilogyed.musicstorecatalog.viewModel.LabelViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/label")
public class LabelController {

    @Autowired
    MusicstoreServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelViewModel createLabel(@RequestBody @Valid LabelViewModel labelViewModel) {
        labelViewModel = service.createLabel(labelViewModel);
        return labelViewModel;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelViewModel getLabel(@PathVariable("id") long labelId) {
        LabelViewModel labelViewModel = service.getLabel(labelId);
        if (labelViewModel == null) {
            throw new IllegalArgumentException("Label could not be retrieved for id: " + labelId);
        } else {
            return labelViewModel;
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@RequestBody @Valid LabelViewModel labelViewModel) {
        if (labelViewModel == null || labelViewModel.getId() < 1) {
            throw new IllegalArgumentException("Id must match id in the view model");
        } else if (labelViewModel.getId() > 0) {
            service.updateLabel(labelViewModel);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable("id") long labelId) {
        service.deleteLabel(labelId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LabelViewModel> getAllLabels() {
        List<LabelViewModel> labelViewModels = service.getAllLabels();
        if (labelViewModels == null || labelViewModels.isEmpty()) {
            throw new IllegalArgumentException("No labels were found");
        } else {
            return labelViewModels;
        }
    }
}
