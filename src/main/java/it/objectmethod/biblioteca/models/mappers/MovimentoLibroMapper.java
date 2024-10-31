package it.objectmethod.biblioteca.models.mappers;

import it.objectmethod.biblioteca.models.dtos.MovimentoLibroDto;
import it.objectmethod.biblioteca.models.entities.MovimentoLibro;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovimentoLibroMapper {
    MovimentoLibroDto toDto(MovimentoLibro movimentoLibro);
    MovimentoLibro toEntity(MovimentoLibroDto movimentoLibroDto);
    List<MovimentoLibroDto> toDtoList(List<MovimentoLibro> movimentoLibroList);
    List<MovimentoLibro> toEntityList(List<MovimentoLibroDto> movimentoLibroDtoList);
}
