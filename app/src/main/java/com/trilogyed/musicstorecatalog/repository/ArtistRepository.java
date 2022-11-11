package com.trilogyed.musicstorecatalog.repository;

import com.trilogyed.musicstorecatalog.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

}
