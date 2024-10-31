package it.objectmethod.biblioteca.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LibroDto {
    @NotNull
    private String titolo;
    @NotNull
    private String autore;
    @NotNull
    private String isbn;
    @NotNull
    private String genero;
    @NotNull
    private String editoriale;
    @NotNull
    private Date annoPubblicazione;
    @NotNull
    private int copie;
}
