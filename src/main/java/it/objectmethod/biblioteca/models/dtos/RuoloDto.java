package it.objectmethod.biblioteca.models.dtos;

import it.objectmethod.biblioteca.enums.NomeRuolo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class RuoloDto {
    private NomeRuolo nomeRuolo;
}
