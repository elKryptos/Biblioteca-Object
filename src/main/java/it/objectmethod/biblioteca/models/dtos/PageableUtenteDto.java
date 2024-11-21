package it.objectmethod.biblioteca.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
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

