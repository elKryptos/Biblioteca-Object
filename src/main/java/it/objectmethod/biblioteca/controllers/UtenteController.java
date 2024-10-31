package it.objectmethod.biblioteca.controllers;

import it.objectmethod.biblioteca.models.dtos.PersonaDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.models.dtos.UtenteDto;
import it.objectmethod.biblioteca.services.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/create/{utenteId}")
    public ResponseEntity<UtenteDto> createUtenteWithPerson(
            @PathVariable long utenteId,
            @RequestBody PersonaDto personaDto) {

//        try {
            // Call the service method to create a new Utente
            UtenteDto utenteDto = utenteService.createUtenteWithPerson(utenteId, personaDto);
            return new ResponseEntity(utenteDto, HttpStatus.CREATED); // Return 201 Created
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(null, HttpStatus.CONFLICT); // Conflict if the user already exists
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Handle other exceptions
//        }
    }
}
