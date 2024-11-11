package it.objectmethod.biblioteca.services;

import it.objectmethod.biblioteca.exceptions.NotFoundException;
import it.objectmethod.biblioteca.models.dtos.PersonaDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.models.entities.Persona;
import it.objectmethod.biblioteca.repositories.PersonaRepository;
import it.objectmethod.biblioteca.utils.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PersonaRepository personaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtToken jwtToken;

    public ResponseWrapper<String> login(PersonaDto personaDto) {
        if(personaDto.getEmail().isEmpty()) {
            throw new NotFoundException("Email is empty");
        }
        if(personaDto.getPassword().isEmpty()) {
            throw new NotFoundException("Password is empty");
        }
        Persona persona = personaRepository.findByEmail(personaDto.getEmail());
        if(persona == null || personaDto.getEmail().isEmpty() || personaDto.getPassword().isEmpty()) {
            throw new NotFoundException("Email or password is empty");
        }
        if (!bCryptPasswordEncoder.matches(personaDto.getPassword(), persona.getPassword())){
            throw new NotFoundException("Password doesn't match");
        }
        String token = jwtToken.tokenGenerator(
                persona.getNome(),
                persona.getEmail(),
                persona.getTelefono(),
                persona.getRuoloPersona()
        );
        return new ResponseWrapper<>("Loggato correttamente", token);
    }
}
