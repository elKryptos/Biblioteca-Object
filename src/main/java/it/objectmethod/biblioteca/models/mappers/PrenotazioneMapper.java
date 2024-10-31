package it.objectmethod.biblioteca.models.mappers;

import it.objectmethod.biblioteca.models.dtos.PrenotazioneDto;
import it.objectmethod.biblioteca.models.entities.Prenotazione;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrenotazioneMapper {
    PrenotazioneDto toDto(Prenotazione prenotazione);
    Prenotazione toEntity(PrenotazioneDto prenotazioneDto);
    List<PrenotazioneDto> toDtoList(List<Prenotazione> prenotazioneList);
    List<Prenotazione> toEntityList(List<PrenotazioneDto> prenotazioneDtoList);
}
