package it.objectmethod.biblioteca.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UtenteDto {
    private LocalDate inizioIscrizione;
    private LocalDate fineIscrizione;
    private String nome;
    private String email;
    private String telefono;
}
