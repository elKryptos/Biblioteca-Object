package it.objectmethod.biblioteca.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UtenteDto {
    private Long utenteId;
    private Long personaId;
    private Date inizioIscrizione;
    private Date fineIscrizione;
}
