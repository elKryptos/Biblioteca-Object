package it.objectmethod.biblioteca.models.mappers;

import it.objectmethod.biblioteca.models.dtos.PageableUtenteDto;
import it.objectmethod.biblioteca.models.dtos.UtenteDto;
import it.objectmethod.biblioteca.models.entities.Utente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PersonaMapper.class})
public interface UtenteMapper {
    @Mapping(target = "personaId", source = "persona.personaId")
    UtenteDto toDto(Utente utente);

    @Mapping(target = "persona.personaId", source = "personaId")
    Utente toEntity(UtenteDto utenteDto);

    List<UtenteDto> toDto(List<Utente> utente);

    List<Utente> toEntity(List<UtenteDto> utenteDto);

    @Mapping(target = "personaId", source = "persona.personaId")
    @Mapping(target = "nome", source = "persona.nome")
    @Mapping(target = "email", source = "persona.email")
    @Mapping(target = "telefono", source = "persona.telefono")
    PageableUtenteDto toPageableDto(Utente utente);

    @Mapping(target = "persona.personaId", source = "personaId")
    @Mapping(target = "persona.nome", source = "nome")
    @Mapping(target = "persona.email", source = "email")
    @Mapping(target = "persona.telefono", source = "telefono")
    Utente toUtenteEntity(PageableUtenteDto pageableUtenteDto);
}
