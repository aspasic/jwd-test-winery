package com.ftninformatika.jwd.modul3.test.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftninformatika.jwd.modul3.test.model.Tip;
import com.ftninformatika.jwd.modul3.test.service.TipService;
import com.ftninformatika.jwd.modul3.test.support.TipToTipDto;
import com.ftninformatika.jwd.modul3.test.web.dto.TipDTO;


@RestController
@RequestMapping(value = "/api/types")
@Validated
public class TipController {

	@Autowired
	private TipService service;
	
	@Autowired
	private TipToTipDto toDto;
	
	
	@PreAuthorize("hasAnyRole('ROLE_KORISNIK', 'ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<List<TipDTO>> getAll() {
	
	List<Tip> types = service.findAll();
	
	
	return new ResponseEntity<>(toDto.convert(types), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<TipDTO> getOne(@PathVariable Long id){
		Tip e = service.findOneById(id);

        if(e != null) {
            return new ResponseEntity<>(toDto.convert(e), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
}
