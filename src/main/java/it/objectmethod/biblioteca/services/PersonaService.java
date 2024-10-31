package it.objectmethod.biblioteca.services;

import it.objectmethod.biblioteca.exceptions.NotFoundException;
import it.objectmethod.biblioteca.models.dtos.PersonaDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.models.entities.Persona;
import it.objectmethod.biblioteca.models.mappers.PersonaMapper;
import it.objectmethod.biblioteca.repositories.PersonaRepository;
import it.objectmethod.biblioteca.utils.Constants;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class PersonaService {
    private final PersonaRepository personaRepository;
    private final PersonaMapper personaMapper;

    public ResponseWrapper<List<PersonaDto>> getAll() {
        List<Persona> personas = personaRepository.findAll();
        List<PersonaDto> personaDtos = personaMapper.toDtoList(personas);
        if (personaDtos.isEmpty()) {
            throw new NotFoundException(Constants.LISTA_NON_TROVATA);
        }
        return new ResponseWrapper<>(Constants.LISTA_TROVATA, personaDtos);
    }

    public ResponseWrapper<PersonaDto> getById(long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.PERSONA_NON_TROVATA));
        return new ResponseWrapper<>(Constants.PERSONA_TROVATA, personaMapper.toDto(persona));
    }

    public ResponseWrapper<PersonaDto> create(PersonaDto personaDto) {
        Persona persona = personaMapper.toEntity(personaDto);
        personaRepository.save(persona);
        return new ResponseWrapper<>(Constants.PERSONA_CREATA, personaMapper.toDto(persona));
    }

    public ResponseWrapper<PersonaDto> update(Long id, PersonaDto personaDto) {
        Persona persona = personaRepository.getById(id);
        if (persona == null) {
            throw new RuntimeException(Constants.PERSONA_NON_TROVATA);
        }
        personaMapper.updateEntity(persona, personaDto);
        personaRepository.save(persona);
        return new ResponseWrapper<>(Constants.PERSONA_UPDATE, personaMapper.toDto(persona));
    }
}
