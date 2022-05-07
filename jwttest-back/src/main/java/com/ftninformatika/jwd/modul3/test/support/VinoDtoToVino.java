package com.ftninformatika.jwd.modul3.test.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ftninformatika.jwd.modul3.test.model.Vino;
import com.ftninformatika.jwd.modul3.test.service.TipService;
import com.ftninformatika.jwd.modul3.test.service.VinarijaService;
import com.ftninformatika.jwd.modul3.test.service.VinoService;
import com.ftninformatika.jwd.modul3.test.web.dto.VinoDTO;

@Component
public class VinoDtoToVino implements Converter<VinoDTO, Vino>{

	@Autowired
	private VinoService vinoServ;
	
	@Autowired
	private TipService tipService;
	
	@Autowired
	private VinarijaService vinarijaServ;
	
	@Override
	public Vino convert(VinoDTO source) {
		
		Vino v = null;
		
		if(source.getId() == null) {
			v = new Vino();
		} else {
			v = vinoServ.findOneById(source.getId());
		}
		
		
		if( v != null) {
			v.setId(source.getId());
			v.setName(source.getName());
			v.setPrice(source.getPrice());
			
			if(source.getStock() == 0) {
				v.setStock(0);
			} else {
				v.setStock(source.getStock());
			}
			
			v.setYear(source.getYear());
			v.setDescription(source.getDescription());
			
			v.setType(tipService.findOneById(source.getTypeId()));
			
			v.setWinery(vinarijaServ.findOneById(source.getWineryId()));
		}
		
		return v;
	}

}
