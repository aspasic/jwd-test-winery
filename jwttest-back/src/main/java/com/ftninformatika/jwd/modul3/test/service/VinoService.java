package com.ftninformatika.jwd.modul3.test.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ftninformatika.jwd.modul3.test.model.Vino;


public interface VinoService {

	List<Vino> findAll();
	
	Vino findOneById(Long id);
	
	Vino save(Vino e);
	
	Vino update(Vino e);
	
	Vino delete(Long id);

	Page<Vino> find(Long wineryId, String name, Integer pageNo);
}
