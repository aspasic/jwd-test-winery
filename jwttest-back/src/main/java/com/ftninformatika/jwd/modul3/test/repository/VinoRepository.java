package com.ftninformatika.jwd.modul3.test.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ftninformatika.jwd.modul3.test.model.Vino;

public interface VinoRepository extends JpaRepository<Vino, Long> {

	Vino findOneById(Long id);

	Page<Vino> findByWineryIdAndNameContainsIgnoreCase(Long wineryId, String name, Pageable p);

	Page<Vino> findByNameContainsIgnoreCase(String name, Pageable p);
}
