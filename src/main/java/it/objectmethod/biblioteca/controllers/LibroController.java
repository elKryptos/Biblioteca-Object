package it.objectmethod.biblioteca.controllers;

import it.objectmethod.biblioteca.exceptions.NotFoundException;
import it.objectmethod.biblioteca.models.dtos.LibroDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.services.LibroService;
import it.objectmethod.biblioteca.utils.Constants;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/libro")
public class LibroController {

    private final LibroService libroService;

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper<List<LibroDto>>> getAll() {
        ResponseWrapper<List<LibroDto>> response = libroService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//  @Validated
    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper<LibroDto>> create(@Valid @RequestBody LibroDto libroDto) {
        ResponseWrapper<LibroDto> response = libroService.create(libroDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Validated
    @PutMapping("/update/{libroId}")
    public ResponseEntity<ResponseWrapper<LibroDto>> update(@Valid @PathVariable Long libroId, @Valid @RequestBody LibroDto libroDto) {
        ResponseWrapper<LibroDto> response = libroService.update(libroId, libroDto);
        if (response == null) throw new NotFoundException(Constants.LIBRO_NON_TROVATO);
        return ResponseEntity.status(HttpStatus.OK).body(response);


     }
}
