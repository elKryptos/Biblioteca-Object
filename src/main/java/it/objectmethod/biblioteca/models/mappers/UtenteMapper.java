package it.objectmethod.biblioteca.models.mappers;

import it.objectmethod.biblioteca.models.dtos.UtenteDto;
import it.objectmethod.biblioteca.models.entities.Utente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UtenteMapper {
    @Mapping(target = "nome", source = "persona.nome")
    @Mapping(target = "email", source = "persona.email")
    @Mapping(target = "telefono", source = "persona.telefono")
    UtenteDto toDto(Utente utente);
    Utente toEntity(UtenteDto utenteDto);
    List<UtenteDto> toDto(List<Utente> utente);
    List<Utente> toEntity(List<UtenteDto> utenteDto);
}
