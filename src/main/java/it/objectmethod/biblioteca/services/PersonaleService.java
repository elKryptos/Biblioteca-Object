package it.objectmethod.biblioteca.services;

import it.objectmethod.biblioteca.enums.NomeRuolo;
import it.objectmethod.biblioteca.exceptions.NotFoundException;
import it.objectmethod.biblioteca.models.dtos.PersonaleDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.models.entities.Persona;
import it.objectmethod.biblioteca.models.entities.Personale;
import it.objectmethod.biblioteca.models.entities.Ruolo;
import it.objectmethod.biblioteca.models.mappers.PersonaleMapper;
import it.objectmethod.biblioteca.models.requests.PersonaleRequest;
import it.objectmethod.biblioteca.repositories.PersonaRepository;
import it.objectmethod.biblioteca.repositories.PersonaleRepository;
import it.objectmethod.biblioteca.repositories.RuoloRepository;
import it.objectmethod.biblioteca.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonaleService {

    private final PersonaleRepository personaleRepository;
    private final PersonaRepository personaRepository;
    private final RuoloRepository ruoloRepository;
    private final PersonaleMapper personaleMapper;

    public ResponseWrapper<List<PersonaleDto>> getAll() {
        List<Personale> personaleList = personaleRepository.findAll();
        if (personaleList.isEmpty()) {
            throw new NotFoundException(Constants.LISTA_PERSONALE_NON_TROVATA);
        }
        List<PersonaleDto> personaleDtoList = personaleMapper.toDtoList(personaleList);
        return new ResponseWrapper<>(Constants.LISTA_PERSONALE_TROVATA, personaleDtoList);
    }

    public PersonaleDto create (PersonaleRequest personaleRequest) {
        Persona persona = new Persona();
        persona.setNome(personaleRequest.getNome());
        persona.setEmail(personaleRequest.getEmail());
        persona.setTelefono(personaleRequest.getTelefono());
        personaRepository.save(persona);

        String ruoloString = personaleRequest.getRuolo();
        Ruolo ruolo = new Ruolo();
        ruolo.setNomeRuolo(NomeRuolo.valueOf(ruoloString));
        ruoloRepository.save(ruolo);

        Personale personale = new Personale();
        personale.setDataAssunzione(LocalDate.now());
        personale.setRuolo(ruolo);
        personale.setPersona(persona);
        personaleRepository.save(personale);

        return personaleMapper.toDto(personale);
    }
}
