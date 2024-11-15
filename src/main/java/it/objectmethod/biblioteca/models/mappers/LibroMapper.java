package it.objectmethod.biblioteca.models.mappers;

import it.objectmethod.biblioteca.models.dtos.LibroDto;
import it.objectmethod.biblioteca.models.entities.Libro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibroMapper {
    LibroDto toDto(Libro libro);
    Libro toEntity(LibroDto libroDto);
    List<LibroDto> toDtoList(List<Libro> libroList);
    List<Libro> toEntityList(List<LibroDto> libroDtoList);

    /* mapping che server per aggiornare una entity da un dto */
    @Mapping(target = "libroId", ignore = true)
    Libro updateEntity(@MappingTarget Libro libro, LibroDto libroDto);
}

