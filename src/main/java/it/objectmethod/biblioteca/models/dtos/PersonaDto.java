package it.objectmethod.biblioteca.models.dtos;

import it.objectmethod.biblioteca.enums.RuoloPersona;
import it.objectmethod.biblioteca.validations.BiblioRegexpValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonaDto {
    //@NotBlank(message = "Il nome non può essere vuoto")
    @BiblioRegexpValidation(regexp = "^[0-9A-Za-zÀ-ÖØ-öø-ÿ&_ \\-]+$", message = "Formato numero non valido")
    private String nome;
    @NotBlank(message = "L'email non può essere vuota")
    @Email(message = "Formato email non valido")
    private String email;
    @NotBlank(message = "Il numero di telfono non può essere vuoto")
    private String telefono;
    @NotBlank
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private RuoloPersona ruoloPersona;
}
