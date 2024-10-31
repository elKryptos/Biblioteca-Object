package it.objectmethod.biblioteca.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utente {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long utenteId;
    private LocalDate inizioIscrizione;
    private LocalDate fineIscrizione;

    @OneToMany(mappedBy = "utente", fetch = FetchType.LAZY)
    private List<Prenotazione> prenotazioni;

    @OneToMany(mappedBy = "utente", fetch = FetchType.LAZY)
    private List<MovimentoLibro> movimentoLibri;

    @ManyToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "personaId")
    private Persona persona;
}
