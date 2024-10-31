package it.objectmethod.biblioteca.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prenotazioneId;
    private Date dataPrenotazione;
    private String stato;

    @ManyToOne
    @JoinColumn(name = "libro_id", referencedColumnName = "libroId")
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "utente_id", referencedColumnName = "utenteId")
    private Utente utente;
}
