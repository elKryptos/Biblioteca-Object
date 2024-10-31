package it.objectmethod.biblioteca.controllers;

import it.objectmethod.biblioteca.models.dtos.PersonaleDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.models.requests.PersonaleRequest;
import it.objectmethod.biblioteca.services.PersonaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/personale")
public class PersonaleController {

    private final PersonaleService personaleService;

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper<List<PersonaleDto>>> getAll() {
        ResponseWrapper<List<PersonaleDto>> response = personaleService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PersonaleDto> create (@RequestBody PersonaleRequest personaleRequest) {
        PersonaleDto personaleDto = personaleService.create(personaleRequest);
        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(personaleDto);
    }

}