package it.objectmethod.biblioteca.models.mappers;

import it.objectmethod.biblioteca.models.dtos.LibroDto;
import it.objectmethod.biblioteca.models.entities.Libro;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibroMapper {
    LibroDto toDto(Libro libro);
    Libro toEntity(LibroDto libroDto);
    List<LibroDto> toDtoList(List<Libro> libroList);
    List<Libro> toEntityList(List<LibroDto> libroDtoList);
}
