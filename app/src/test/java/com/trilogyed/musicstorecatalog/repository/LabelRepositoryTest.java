package com.trilogyed.musicstorecatalog.repository;

import com.trilogyed.musicstorecatalog.model.Label;
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
public class LabelRepositoryTest {

    @Autowired
    LabelRepository labelRepository;

    @Before
    public void setUp() throws Exception {
        labelRepository.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        labelRepository.deleteAll();
    }

    @Test
    public void shouldCreateGetDeleteLabel() {
        // Create
        Label label = new Label();
        label.setName("Whatever Label");
        label.setWebsite("www.whatever.com");

        label = labelRepository.save(label);
        Optional<Label> foundLabel = labelRepository.findById(label.getId());

        assertTrue(foundLabel.isPresent());
        assertEquals(label, foundLabel.get());

        // Get and Update
        label.setWebsite("www.whatever.org");

        labelRepository.save(label);
        foundLabel = labelRepository.findById(label.getId());

        assertTrue(foundLabel.isPresent());
        assertEquals(label, foundLabel.get());

        // Delete
        labelRepository.deleteById(label.getId());
        foundLabel = labelRepository.findById(label.getId());

        assertFalse(foundLabel.isPresent());
    }

    @Test
    public void shouldFindAllLabels() {
        Label label = new Label();
        label.setName("Whatever Label");
        label.setWebsite("www.whatever.com");

        Label label1 = new Label();
        label1.setName("Rival Label");
        label1.setWebsite("www.rival.com");

        label = labelRepository.save(label);
        label1 = labelRepository.save(label1);
        List<Label> allLabels = new ArrayList<>();
        allLabels.add(label);
        allLabels.add(label1);

        List<Label> foundAllLabels = labelRepository.findAll();

        assertEquals(allLabels.size(), foundAllLabels.size());
    }

}