package it.objectmethod.biblioteca.models.dtos;

import it.objectmethod.biblioteca.validations.BiblioRegexpValidation;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibroDto {
    @NotBlank(message = "Il nome non può essere vuoto")
    //@BiblioRegexpValidation(regexp = "^[0-9A-Za-zÀ-ÖØ-öø-ÿ &_\\-]+$", message = "Formato numero non valido", annoPubblicazione = 0)
    private String titolo;
    @NotBlank(message = "L'autore non può essere vuoto")
    private String autore;
    @NotBlank(message = "L'isbn non può essere vuoto")
    @BiblioRegexpValidation(regexp = "^[0-9A-Za-zÀ-ÖØ-öø-ÿ &_\\-]+$", message = "Formato numero non valido")
    private String isbn;
    @NotBlank(message = "Il genere non può essere vuoto")
    private String genere;
    @NotBlank(message = "L'editoriale non può essere vuoto")
    private String editor;
    @NotNull(message = "L'anno di pubbicazione non può essere vuoto")
    private LocalDate annoPubblicazione;
    @NotNull
    @Min(value = 3, message = "Il numero minimo dev'essere almeno 3")
    private int copie;
}
