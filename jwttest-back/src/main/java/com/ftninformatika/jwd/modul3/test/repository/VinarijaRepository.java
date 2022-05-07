package com.ftninformatika.jwd.modul3.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftninformatika.jwd.modul3.test.model.Vinarija;

@Repository
public interface VinarijaRepository extends JpaRepository<Vinarija, Long>{

	Vinarija findOneById(Long id);
}
