package com.trilogyed.musicstorecatalog.repository;

import com.trilogyed.musicstorecatalog.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {

}
