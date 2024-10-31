package it.objectmethod.biblioteca.services;

import it.objectmethod.biblioteca.models.dtos.PersonaDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.models.dtos.UtenteDto;
import it.objectmethod.biblioteca.models.entities.Persona;
import it.objectmethod.biblioteca.models.entities.Utente;
import it.objectmethod.biblioteca.exceptions.NotFoundException;
import it.objectmethod.biblioteca.models.mappers.PersonaMapper;
import it.objectmethod.biblioteca.models.mappers.UtenteMapper;
import it.objectmethod.biblioteca.repositories.PersonaRepository;
import it.objectmethod.biblioteca.repositories.UtenteRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PersonaRepository personaRepository;
    private final UtenteMapper utenteMapper;
    private final PersonaMapper personaMapper;


    public ResponseWrapper<List<UtenteDto>> getAll() {
        List<Utente> utenteList = utenteRepository.findAll();
        List<UtenteDto> utenteDtoList = utenteMapper.toDto(utenteList);
        if(utenteDtoList.isEmpty()){
            return new ResponseWrapper<>("Nessun utente trovato");
        }
        return  new ResponseWrapper<>("Utenti trovati", utenteDtoList);
    }

    public ResponseWrapper<UtenteDto> getById(Long id) {
        Optional<Utente> utente = utenteRepository.findById(id);
        if (utente.isPresent()) {
            return new ResponseWrapper<>("Utente trovato", utenteMapper.toDto(utente.get()));
        }
        throw new NotFoundException("Utente non trovato con ID: " + id);
    }

    public UtenteDto createUtenteWithPerson(long utenteId, PersonaDto personaDto) {
        if (utenteRepository.existsById(utenteId)) {
            throw new RuntimeException("Utente already exists");
        }
        Persona persona = personaMapper.toEntity(personaDto);
        personaRepository.save(persona);

        Utente utente = new Utente();
        utente.setUtenteId(utenteId);
        utente.setPersona(persona);
        LocalDate inizioIscrizione = LocalDate.now();
        utente.setInizioIscrizione(inizioIscrizione);
        LocalDate fineIscrizione = inizioIscrizione.plusYears(1);
        utente.setFineIscrizione(fineIscrizione);
        utenteRepository.save(utente);

        UtenteDto utenteDto = utenteMapper.toDto(utente);
        return utenteDto;


    }


}
