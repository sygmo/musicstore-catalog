package com.trilogyed.musicstorecatalog.repository;

import com.trilogyed.musicstorecatalog.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {

}
