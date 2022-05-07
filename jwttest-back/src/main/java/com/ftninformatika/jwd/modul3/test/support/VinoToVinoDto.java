package com.ftninformatika.jwd.modul3.test.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ftninformatika.jwd.modul3.test.model.Vino;
import com.ftninformatika.jwd.modul3.test.web.dto.VinoDTO;

@Component
public class VinoToVinoDto implements Converter<Vino, VinoDTO> {

	@Override
	public VinoDTO convert(Vino source) {
		VinoDTO dto = new VinoDTO();
		
		dto.setId(source.getId());
		dto.setDescription(source.getDescription());
		dto.setName(source.getName());
		dto.setPrice(source.getPrice());
		dto.setYear(source.getYear());
		dto.setStock(source.getStock());
		
		dto.setTypeId(source.getType().getId());
		dto.setTypeName(source.getType().getName());
		
		dto.setWineryId(source.getWinery().getId());
		dto.setWineryName(source.getWinery().getName());
		
		return dto;
	}
	
	public List<VinoDTO> convert(List<Vino> wines) {
		List<VinoDTO> dto = new ArrayList<>();
		
		for(Vino w : wines) {
			dto.add(convert(w));

		}
		
		return dto;
	}

}
