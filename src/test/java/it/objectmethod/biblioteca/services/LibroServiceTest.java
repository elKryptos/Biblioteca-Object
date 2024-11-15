package it.objectmethod.biblioteca.services;

import it.objectmethod.biblioteca.models.dtos.LibroDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.models.entities.Libro;
import it.objectmethod.biblioteca.models.mappers.LibroMapper;
import it.objectmethod.biblioteca.repositories.LibroRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@MockitoSettings
class LibroServiceTest {

    @Mock
    private LibroRepository libroRepository;

    @Mock
    private LibroMapper libroMapper;

    @InjectMocks
    private LibroService libroService;

    private final static String MSG = "Libro trovato!";
    private final static String LIBRO_CREATO = "Libro creato correttamente!";
    private static final String LIBRO_UPDATED = "Libro aggiornato correttamente";

    static final LibroDto libroDto = LibroDto.builder()
            .copie(1)
            .isbn("isbn")
            .annoPubblicazione(new Date())
            .titolo("titolo")
            .autore("autore")
            .editor("editor")
            .genere("genere")
            .build();

    static final Libro entity = Libro.builder()
            .copie(1)
            .isbn("isbn")
            .annoPubblicazione(new Date())
            .titolo("titolo")
            .autore("autore")
            .editor("editor")
            .genere("genere")
            .build();

    @Test
    void getAll() {

        final ResponseWrapper<List<LibroDto>> expected = new ResponseWrapper<>(MSG);
        final List<LibroDto> dto = List.of(libroDto);
        expected.setType(dto);

        final List<Libro> entities = List.of(entity);
        when(libroRepository.findAll()).thenReturn(entities);
        when(libroMapper.toDtoList(entities)).thenReturn(dto);

        final ResponseWrapper<List<LibroDto>> actual = libroService.getAll();

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldCreateLibro_whenIsValid() {

        final ResponseWrapper<LibroDto> expected = new ResponseWrapper<>(LIBRO_CREATO);
        final LibroDto inputDto = libroDto;
        final Libro entityToSave = entity;
        final LibroDto savedDto = libroDto;
        expected.setType(savedDto);

        when(libroMapper.toEntity(inputDto)).thenReturn(entityToSave);
        when(libroRepository.save(entityToSave)).thenReturn(entityToSave);

        final ResponseWrapper<LibroDto> actual = libroService.create(inputDto);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldUpdateLibro_whenIsValid() {

        final Long libroId = 1L;
        final LibroDto inputDto = libroDto;
        final Libro updatedLibro = entity;
        final LibroDto updatedLibroDto = libroDto;
        final ResponseWrapper<LibroDto> expected = new ResponseWrapper<>(LIBRO_UPDATED);
        expected.setType(updatedLibroDto);

        when(libroRepository.findById(libroId)).thenReturn(Optional.of(updatedLibro));
        when(libroMapper.updateEntity(updatedLibro, inputDto)).thenReturn(updatedLibro);
        when(libroRepository.save(updatedLibro)).thenReturn(updatedLibro);
        when(libroMapper.toDto(updatedLibro)).thenReturn(updatedLibroDto);

        final ResponseWrapper<LibroDto> actual = libroService.update(libroId, updatedLibroDto);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);

    }
}