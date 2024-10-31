package it.objectmethod.biblioteca.models.dtos;

import it.objectmethod.biblioteca.enums.Stato;
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
public class MovimentoLibroDto {
    @NotNull
    private Date dataPrestito;
    @NotNull
    private Date dataScandenza;
    @NotNull
    private Date dataRestituzione;

    private Stato stato;
}
