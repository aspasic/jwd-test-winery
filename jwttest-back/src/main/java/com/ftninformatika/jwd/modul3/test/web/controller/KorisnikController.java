package com.ftninformatika.jwd.modul3.test.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftninformatika.jwd.modul3.test.model.Korisnik;
import com.ftninformatika.jwd.modul3.test.security.TokenUtils;
import com.ftninformatika.jwd.modul3.test.service.KorisnikService;
import com.ftninformatika.jwd.modul3.test.support.KorisnikDtoToKorisnik;
import com.ftninformatika.jwd.modul3.test.support.KorisnikToKorisnikDto;
import com.ftninformatika.jwd.modul3.test.web.dto.AuthKorisnikDto;
import com.ftninformatika.jwd.modul3.test.web.dto.KorisnikDTO;
import com.ftninformatika.jwd.modul3.test.web.dto.KorisnikPromenaLozinkeDto;
import com.ftninformatika.jwd.modul3.test.web.dto.KorisnikRegistracijaDTO;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/korisnici", produces = MediaType.APPLICATION_JSON_VALUE)
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private KorisnikDtoToKorisnik toKorisnik;

    @Autowired
    private KorisnikToKorisnikDto toKorisnikDto;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<KorisnikDTO> create(@RequestBody @Validated KorisnikRegistracijaDTO dto){

        if(dto.getId() != null || !dto.getLozinka().equals(dto.getPonovljenaLozinka())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Korisnik korisnik = toKorisnik.convert(dto);

        String encodedPassword = passwordEncoder.encode(dto.getLozinka());
        korisnik.setLozinka(encodedPassword);

        return new ResponseEntity<>(toKorisnikDto.convert(korisnikService.save(korisnik)), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('KORISNIK', 'ADMIN')")
    @PutMapping(value= "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KorisnikDTO> update(@PathVariable Long id, @Valid @RequestBody KorisnikDTO korisnikDTO){

        if(!id.equals(korisnikDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Korisnik korisnik = toKorisnik.convert(korisnikDTO);

        return new ResponseEntity<>(toKorisnikDto.convert(korisnikService.save(korisnik)),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('KORISNIK', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<KorisnikDTO> get(@PathVariable Long id){
        Optional<Korisnik> korisnik = korisnikService.findOne(id);

        if(korisnik.isPresent()) {
            return new ResponseEntity<>(toKorisnikDto.convert(korisnik.get()), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<KorisnikDTO>> get(@RequestParam(defaultValue="0") int page){
        Page<Korisnik> korisnici = korisnikService.findAll(page);
        return new ResponseEntity<>(toKorisnikDto.convert(korisnici.getContent()), HttpStatus.OK);
    }


    @PreAuthorize("permitAll()")
    @RequestMapping(path = "/auth", method = RequestMethod.POST)
    public ResponseEntity authenticateUser(@RequestBody AuthKorisnikDto dto) {
        // Perform the authentication
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            // Reload user details so we can generate token
            UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
            return ResponseEntity.ok(tokenUtils.generateToken(userDetails));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
