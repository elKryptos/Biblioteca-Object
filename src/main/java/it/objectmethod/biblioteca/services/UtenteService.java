package it.objectmethod.biblioteca.services;

import it.objectmethod.biblioteca.exceptions.NotFoundException;
import it.objectmethod.biblioteca.filters.UtenteParams;
import it.objectmethod.biblioteca.models.dtos.PageableUtenteDto;
import it.objectmethod.biblioteca.models.dtos.PersonaDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.models.dtos.UtenteDto;
import it.objectmethod.biblioteca.models.entities.Persona;
import it.objectmethod.biblioteca.models.entities.Utente;
import it.objectmethod.biblioteca.models.mappers.PersonaMapper;
import it.objectmethod.biblioteca.models.mappers.UtenteMapper;
import it.objectmethod.biblioteca.repositories.PersonaRepository;
import it.objectmethod.biblioteca.repositories.UtenteRepository;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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
        Date inizioIscrizione = new Date();
        utente.setInizioIscrizione(inizioIscrizione);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inizioIscrizione);
        calendar.add(Calendar.MONTH, 1);
        Date fineIscrizione = calendar.getTime();
        utente.setFineIscrizione(fineIscrizione);
        utenteRepository.save(utente);
        UtenteDto utenteDto = utenteMapper.toDto(utente);
        return utenteDto;
    }

    public ResponseWrapper<UtenteDto> createUtente(UtenteDto utenteDto) {
        Utente utente = utenteMapper.toEntity(utenteDto);
        Date inizioIscrizione = new Date();
        utente.setInizioIscrizione(inizioIscrizione);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inizioIscrizione);
        calendar.add(Calendar.MONTH, 1);
        Date fineIscrizione = calendar.getTime();
        utente.setFineIscrizione(fineIscrizione);
        utenteRepository.save(utente);
        UtenteDto utenteResponseDto = utenteMapper.toDto(utente);
        System.out.println("Inizio Iscrizione: " + utenteResponseDto.getInizioIscrizione());
        System.out.println("Fine Iscrizione: " + utenteResponseDto.getFineIscrizione());
        return new ResponseWrapper<>("Utente creato", utenteMapper.toDto(utente));
    }

    public ResponseWrapper<Page<PageableUtenteDto>> paginate(Pageable pageable,@Valid UtenteParams utenteParams) {
        Specification<Utente> spec = utenteParams.getSpecification();
        Page<Utente> utentePage = utenteRepository.findAll(spec, pageable);
        Page<PageableUtenteDto> utenteDtoPage = utentePage.map(utenteMapper::toPageableDto);
        return new ResponseWrapper<>("Libri trovati", utenteDtoPage);
    }

}
