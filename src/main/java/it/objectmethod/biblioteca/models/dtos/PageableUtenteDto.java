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
public class PageableUtenteDto {
    private Long utenteId;
    private Date inizioIscrizione;
    private Date fineIscrizione;
    private Long personaId;
    private String nome;
    private String email;
    private String telefono;
}

