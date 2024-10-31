package it.objectmethod.biblioteca.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long libroId;
    private String titolo;
    private String autore;
    private String isbn;
    private String genere;
    private String editor;
    private Date annoPubblicazione;
    private int copie;

    @OneToMany(mappedBy = "libro", fetch = FetchType.LAZY)
    private List<MovimentoLibro> movimentoLibri;

    @OneToMany(mappedBy = "libro", fetch = FetchType.LAZY)
    private List<Prenotazione> prenotazioni;
}
