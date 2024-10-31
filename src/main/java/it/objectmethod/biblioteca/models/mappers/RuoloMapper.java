package it.objectmethod.biblioteca.models.mappers;

import it.objectmethod.biblioteca.models.dtos.RuoloDto;
import it.objectmethod.biblioteca.models.entities.Ruolo;
import it.objectmethod.biblioteca.enums.NomeRuolo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RuoloMapper {
    RuoloDto toDto(Ruolo ruolo);
    Ruolo toEntity(RuoloDto ruoloDto);
    List<RuoloDto> toDtoList(List<Ruolo> ruoloList);
    List<Ruolo> toEntityList(List<RuoloDto> ruoloDtoList);

    default Ruolo map(String ruoloStr) {
        if (ruoloStr == null) {
            return null;
        }
        Ruolo ruolo = new Ruolo();
        ruolo.setNomeRuolo(NomeRuolo.valueOf(ruoloStr)); // Converte la stringa in enum
        return ruolo;
    }
}
