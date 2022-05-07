package com.ftninformatika.jwd.modul3.test.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ftninformatika.jwd.modul3.test.model.Vinarija;
import com.ftninformatika.jwd.modul3.test.web.dto.VinarijaDTO;

@Component
public class VinarijaToVinarijaDto implements Converter<Vinarija, VinarijaDTO>{

	@Override
	public VinarijaDTO convert(Vinarija source) {
		VinarijaDTO dto = new VinarijaDTO();
		
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setYear(source.getYear());
		
		return dto;
	}
	
	public List<VinarijaDTO> convert(List<Vinarija> wineries) {
		List<VinarijaDTO> dto = new ArrayList<>();
		
		for(Vinarija v : wineries) {
			dto.add(convert(v));

		}
		
		return dto;
	}

}
