package com.trilogyed.musicstorecatalog.repository;

import com.trilogyed.musicstorecatalog.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {

}
