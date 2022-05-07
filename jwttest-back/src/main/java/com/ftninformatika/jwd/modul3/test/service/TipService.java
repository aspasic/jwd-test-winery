package com.ftninformatika.jwd.modul3.test.service;

import java.util.List;

import com.ftninformatika.jwd.modul3.test.model.Tip;


public interface TipService {

	List<Tip> findAll();
	
	Tip findOneById(Long id);
	
//	Tip save(Tip e);
//	
//	Tip update(Tip e);
//	
//	Tip delete(Long id);

}
