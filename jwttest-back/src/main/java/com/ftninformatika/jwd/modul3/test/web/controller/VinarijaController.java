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

import com.ftninformatika.jwd.modul3.test.model.Vinarija;
import com.ftninformatika.jwd.modul3.test.service.VinarijaService;
import com.ftninformatika.jwd.modul3.test.support.VinarijaToVinarijaDto;
import com.ftninformatika.jwd.modul3.test.web.dto.VinarijaDTO;


@RestController
@RequestMapping(value = "/api/wineries")
@Validated
public class VinarijaController {

	@Autowired
	private VinarijaService service;
	
	@Autowired
	private VinarijaToVinarijaDto toDto;
	
	
	@PreAuthorize("hasAnyRole('ROLE_KORISNIK', 'ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<List<VinarijaDTO>> getAll() {
	
	List<Vinarija> wineries = service.findAll();
	
	return new ResponseEntity<>(toDto.convert(wineries), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_KORISNIK', 'ROLE_ADMIN')")
	@GetMapping("/{id}")
    public ResponseEntity<VinarijaDTO> getOne(@PathVariable Long id){
        Vinarija e = service.findOneById(id);

        if(e != null) {
            return new ResponseEntity<>(toDto.convert(e), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	
	
}
