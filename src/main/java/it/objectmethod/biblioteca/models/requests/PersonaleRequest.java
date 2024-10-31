package it.objectmethod.biblioteca.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaleRequest {
    private String nome;
    private String email;
    private String telefono;
    private String ruolo;
}
