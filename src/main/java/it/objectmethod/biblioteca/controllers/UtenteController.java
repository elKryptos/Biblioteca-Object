package it.objectmethod.biblioteca.controllers;

import it.objectmethod.biblioteca.filters.UtenteParams;
import it.objectmethod.biblioteca.models.dtos.PageableUtenteDto;
import it.objectmethod.biblioteca.models.dtos.PersonaDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.models.dtos.UtenteDto;
import it.objectmethod.biblioteca.models.entities.Persona;
import it.objectmethod.biblioteca.models.entities.Utente;
import it.objectmethod.biblioteca.services.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/utente")
public class UtenteController {

    private final UtenteService utenteService;

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper<List<UtenteDto>>> getAllUtentes() {
        ResponseWrapper<List<UtenteDto>> response = utenteService.getAll();
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<UtenteDto>> getUtenteById(@PathVariable Long id, HttpServletRequest request) {
        ResponseWrapper<UtenteDto> response = utenteService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper<UtenteDto>> createUtente(@RequestBody UtenteDto utenteDto) {
        ResponseWrapper<UtenteDto> response = utenteService.createUtente(utenteDto);
        return new ResponseEntity(utenteDto, HttpStatus.CREATED);
    }

    @PostMapping("/create/{utenteId}")
    public ResponseEntity<UtenteDto> createUtenteWithPerson(
            @PathVariable long utenteId,
            @RequestBody PersonaDto personaDto) {
            UtenteDto utenteDto = utenteService.createUtenteWithPerson(utenteId, personaDto);
            return new ResponseEntity(utenteDto, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<ResponseWrapper<Page<PageableUtenteDto>>> getAllUtenti(
            @PageableDefault(sort = "utenteId", size = 5) Pageable pageable, UtenteParams utenteParams) {
        Specification<Utente> spec = utenteParams.getSpecification();
        ResponseWrapper<Page<PageableUtenteDto>> response = utenteService.paginate(pageable, utenteParams);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
