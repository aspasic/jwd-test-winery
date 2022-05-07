package com.ftninformatika.jwd.modul3.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftninformatika.jwd.modul3.test.model.Tip;
import com.ftninformatika.jwd.modul3.test.repository.TipRepository;
import com.ftninformatika.jwd.modul3.test.service.TipService;

@Service
public class JpaTipService implements TipService {

	@Autowired
	private TipRepository repository;
	
	@Override
	public List<Tip> findAll() {
		return repository.findAll();
	}

	@Override
	public Tip findOneById(Long id) {
		return repository.findOneById(id);
	}


}
