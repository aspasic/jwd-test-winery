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

import com.ftninformatika.jwd.modul3.test.model.Vino;
import com.ftninformatika.jwd.modul3.test.service.VinoService;
import com.ftninformatika.jwd.modul3.test.support.VinoDtoToVino;
import com.ftninformatika.jwd.modul3.test.support.VinoToVinoDto;
import com.ftninformatika.jwd.modul3.test.web.dto.VinoDTO;


@RestController
@RequestMapping(value = "/api/wines")
@Validated
public class VinoController {
	
	@Autowired
	private VinoService service;
	
	@Autowired
	private VinoToVinoDto toDto;
	
	@Autowired
	private VinoDtoToVino toEntity;
	
//	@PreAuthorize("hasAnyRole('ROLE_KORISNIK', 'ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<List<VinoDTO>> getAll(
					@RequestParam(required = false) Long wineryId,
					@RequestParam(required = false) String name,
					@RequestParam(value = "pageNo", defaultValue ="0") Integer pageNo) {
	
	Page<Vino> page = service.find(wineryId,name, pageNo);
	
	HttpHeaders headers = new HttpHeaders();
	headers.add("Total-Pages", Integer.toString(page.getTotalPages()));
	
	return new ResponseEntity<>(toDto.convert(page.getContent()), headers, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_KORISNIK', 'ROLE_ADMIN')")
	@GetMapping("/{id}")
    public ResponseEntity<VinoDTO> getOne(@PathVariable Long id){
        Vino e = service.findOneById(id);

        if(e != null) {
            return new ResponseEntity<>(toDto.convert(e), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
		Vino e = service.delete(id);

        if(e != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VinoDTO> update(@PathVariable Long id, @Valid @RequestBody VinoDTO dto){

        if(!id.equals(dto.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Vino e = toEntity.convert(dto);
        Vino saved = service.update(e);

        return new ResponseEntity<>(toDto.convert(saved),HttpStatus.OK);
    }
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VinoDTO> create(@Valid @RequestBody VinoDTO dto) {
		Vino e = toEntity.convert(dto);
		Vino saved = service.save(e);
		
		return new ResponseEntity<>(toDto.convert(saved), HttpStatus.CREATED);
	}
	
}
