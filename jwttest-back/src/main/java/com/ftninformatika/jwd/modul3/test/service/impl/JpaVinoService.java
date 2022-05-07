package com.ftninformatika.jwd.modul3.test.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ftninformatika.jwd.modul3.test.service.VinoService;
import com.ftninformatika.jwd.modul3.test.model.Vino;

import com.ftninformatika.jwd.modul3.test.repository.VinoRepository;


@Service
public class JpaVinoService implements VinoService {

	@Autowired
	private VinoRepository repository;
	
	@Override
	public List<Vino> findAll() {
		return repository.findAll();
	}

	@Override
	public Vino findOneById(Long id) {
		return repository.findOneById(id);
	}

	@Override
	public Vino save(Vino e) {
		return repository.save(e);
	}

	@Override
	public Vino update(Vino e) {
		return repository.save(e);
	}

	@Override
	public Vino delete(Long id) {
		Vino v = repository.findOneById(id);
		
		if (v != null) {
			
			v.getWinery().getWines().remove(v);
			v.getType().getWines().remove(v);
			
			repository.deleteById(id);
			return v;
		}
		
		return null;
	}

	@Override
	public Page<Vino> find(Long wineryId, String name, Integer pageNo) {
		if(name == null) {
			name = "";
		}
		
		if(wineryId == null) {
			return repository.findByNameContainsIgnoreCase(name, PageRequest.of(pageNo, 3));
		}
		
		return repository.findByWineryIdAndNameContainsIgnoreCase(wineryId, name, PageRequest.of(pageNo, 3));
	}

//	private LocalDateTime getDateConverted(String dateTime) throws DateTimeParseException {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        return LocalDateTime.parse(dateTime, formatter);
//    }
	
}
