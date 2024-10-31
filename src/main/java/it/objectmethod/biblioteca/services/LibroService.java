package it.objectmethod.biblioteca.services;

import it.objectmethod.biblioteca.exceptions.NotFoundException;
import it.objectmethod.biblioteca.models.dtos.LibroDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.models.entities.Libro;
import it.objectmethod.biblioteca.models.mappers.LibroMapper;
import it.objectmethod.biblioteca.repositories.LibroRepository;
import it.objectmethod.biblioteca.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibroService {

    private final LibroRepository libroRepository;
    private final LibroMapper libroMapper;

    public ResponseWrapper<LibroDto> create(LibroDto libroDto) {
        libroMapper.toEntity(libroDto);
        libroRepository.save(libroMapper.toEntity(libroDto));
        return new ResponseWrapper<>(Constants.LIBRO_CREATO, libroDto);
    }

    public ResponseWrapper<LibroDto> update(Long libroId, LibroDto libroDto) {
        Libro libro = libroRepository.findById(libroId).orElseThrow(
                () -> new NotFoundException(Constants.LIBRO_NON_TROVATO));
        libroMapper.toEntity(libroDto);
        libroRepository.save(libro);
        return new ResponseWrapper<>(Constants.LIBRO_UPDATE, libroDto);
    }
}
