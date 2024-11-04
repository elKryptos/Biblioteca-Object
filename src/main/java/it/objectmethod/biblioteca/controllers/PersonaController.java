package it.objectmethod.biblioteca.controllers;

import it.objectmethod.biblioteca.models.dtos.PersonaDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.services.PersonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/persona")
public class PersonaController {

    private final PersonaService personaService;

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper<List<PersonaDto>>> getAll() {
        ResponseWrapper<List<PersonaDto>> response = personaService.getAll();
        if (response == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<PersonaDto>> getById(@PathVariable int id) {
        ResponseWrapper<PersonaDto> response = personaService.getById(id);
        if (response == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    @Validated
    public ResponseEntity<ResponseWrapper<PersonaDto>> create(@Valid @RequestBody PersonaDto personaDto) {
        ResponseWrapper<PersonaDto> response = personaService.create(personaDto);
        if (response == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseWrapper<PersonaDto>> update(
            @PathVariable Long id,
            @RequestBody PersonaDto personaDto) {
        ResponseWrapper<PersonaDto> response = personaService.update(id, personaDto);
        if (response == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/xls")
    public ResponseEntity<String> generaXLS() {
        String response = personaService.generaXLS();
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
