package it.objectmethod.biblioteca.controllers;

import it.objectmethod.biblioteca.models.dtos.PersonaDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper<String>> login(@RequestBody PersonaDto personaDto) {
        ResponseWrapper<String> response = authService.login(personaDto);
        return ResponseEntity.ok(response);
    }
}
