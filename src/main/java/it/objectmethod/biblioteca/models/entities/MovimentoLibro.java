package it.objectmethod.biblioteca.models.entities;

import it.objectmethod.biblioteca.enums.Stato;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentoLibro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movimentoLibroId;
    private Date dataPrestito;
    private Date dataScandenza;
    private Date dataRestituzione;

    @Enumerated(EnumType.STRING)
    private Stato stato;

    @ManyToOne
    @JoinColumn(name = "libro_id", referencedColumnName = "libroId")
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "utente_id", referencedColumnName = "utenteId")
    private Utente utente;
}
