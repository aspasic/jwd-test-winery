package com.ftninformatika.jwd.modul3.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftninformatika.jwd.modul3.test.model.Vinarija;
import com.ftninformatika.jwd.modul3.test.repository.VinarijaRepository;
import com.ftninformatika.jwd.modul3.test.service.VinarijaService;

@Service
public class JpaVinarijaService implements VinarijaService {

	@Autowired
	private VinarijaRepository repository;
	
	@Override
	public List<Vinarija> findAll() {
		return repository.findAll();
	}

	@Override
	public Vinarija findOneById(Long id) {
		return repository.findOneById(id);
	}

}
