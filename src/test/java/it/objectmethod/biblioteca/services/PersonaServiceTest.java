package it.objectmethod.biblioteca.services;

import it.objectmethod.biblioteca.enums.RuoloPersona;
import it.objectmethod.biblioteca.models.dtos.PersonaDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.models.entities.Persona;
import it.objectmethod.biblioteca.models.mappers.PersonaMapper;
import it.objectmethod.biblioteca.repositories.PersonaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings
public class PersonaServiceTest {

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private PersonaMapper personaMapper;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private PersonaService personaService;

    private static final String LISTA_TROVATA = "Lista trovata!";
    private static final String PERSONA_CREATA = "Persona creata!";

    static final PersonaDto personaDto = PersonaDto.builder()
            .nome("Joe Biden")
            .email("joe.biden@example.com")
            .telefono("+555 (343) 234")
            .password("123456")
            .ruoloPersona(RuoloPersona.BRONCE)
            .build();

    static final Persona entity = Persona.builder()
            .nome("Joe Biden")
            .email("joe.biden@example.com")
            .telefono("+555 (343) 234")
            .password("123456")
            .ruoloPersona(RuoloPersona.BRONCE)
            .build();

    @Test
    void getAll() {

        final ResponseWrapper<List<PersonaDto>> expected = new ResponseWrapper<>(LISTA_TROVATA);
        final List<PersonaDto> dtoList = List.of(personaDto);
        expected.setType(dtoList);

        final List<Persona> entities = List.of(entity);

        when(personaRepository.findAll()).thenReturn(entities);
        when(personaMapper.toDtoList(entities)).thenReturn(dtoList);

        final ResponseWrapper<List<PersonaDto>> actual = personaService.getAll();

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldCreatePersona_whenIsvalid() {

        final ResponseWrapper<PersonaDto> expected = new ResponseWrapper<>(PERSONA_CREATA);
        final PersonaDto inputDto = personaDto;
        final Persona entityToSave = entity;

        when(personaMapper.toEntity(inputDto)).thenReturn(entityToSave);
        when(personaRepository.save(entityToSave)).thenReturn(entityToSave);

        final ResponseWrapper<PersonaDto> actual = personaService.create(inputDto);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }





















}
