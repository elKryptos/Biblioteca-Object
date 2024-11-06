package it.objectmethod.biblioteca.models.mappers;

import it.objectmethod.biblioteca.models.dtos.PersonaDto;
import it.objectmethod.biblioteca.models.entities.Persona;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonaMapper {
    PersonaDto toDto(Persona persona);
    Persona toEntity(PersonaDto personaDto);
    List<PersonaDto> toDtoList(List<Persona> personaList);
    List<Persona> toEntityList(List<PersonaDto> personaDtoList);

    /* mapping che server per aggiornare una entity da un dto */
    @Mapping(target = "personaId", ignore = true)
    void updateEntity(@MappingTarget Persona persona, PersonaDto personaDto);
}
