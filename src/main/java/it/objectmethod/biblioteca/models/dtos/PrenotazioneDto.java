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
public class PrenotazioneDto {
    @NotNull
    private Date dataPrenotazione;
    @NotNull
    private String stato;
}
