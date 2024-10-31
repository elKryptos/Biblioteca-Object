package it.objectmethod.biblioteca.models.mappers;

import it.objectmethod.biblioteca.models.dtos.PersonaleDto;
import it.objectmethod.biblioteca.models.entities.Personale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RuoloMapper.class, PersonaMapper.class})
public interface PersonaleMapper {
    @Mapping(target = "nome", source = "persona.nome")
    @Mapping(target = "email", source = "persona.email")
    @Mapping(target = "telefono", source = "persona.telefono")
    @Mapping(target = "ruolo", source = "ruolo.nomeRuolo")
    PersonaleDto toDto(Personale personale);
    Personale toEntity(PersonaleDto personaleDto);
    List<PersonaleDto> toDtoList(List<Personale> personaleList);
    List<Personale> toEntityList(List<PersonaleDto> personaleDtoList);
}
