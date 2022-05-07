package com.ftninformatika.jwd.modul3.test.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ftninformatika.jwd.modul3.test.model.Tip;
import com.ftninformatika.jwd.modul3.test.web.dto.TipDTO;

@Component
public class TipToTipDto implements Converter<Tip, TipDTO> {

	@Override
	public TipDTO convert(Tip source) {
		TipDTO dto = new TipDTO();
		
		dto.setId(source.getId());
		dto.setName(source.getName());
		
		return dto;
	}
	
	public List<TipDTO> convert(List<Tip> types) {
		List<TipDTO> dto = new ArrayList<>();
		
		for(Tip t : types) {
			dto.add(convert(t));

		}
		
		return dto;
	}

}
