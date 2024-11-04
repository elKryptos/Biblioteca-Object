package it.objectmethod.biblioteca.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personaId;
    private String nome;
    private String email;
    private String telefono;

    public List<String> allProperties() {
        String idString = String.valueOf(personaId);
        return Arrays.asList(idString, nome, email, telefono);
    }

    @OneToMany(mappedBy = "persona")
    private List<Utente> utenti;

    @OneToMany(mappedBy = "persona")
    private List<Personale> personaleList;
}
