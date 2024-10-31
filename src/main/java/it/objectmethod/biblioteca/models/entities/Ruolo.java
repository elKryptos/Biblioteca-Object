package it.objectmethod.biblioteca.models.entities;

import it.objectmethod.biblioteca.enums.NomeRuolo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ruolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruoloId;

    @Enumerated(EnumType.STRING)
    private NomeRuolo nomeRuolo;

    @OneToMany(mappedBy = "ruolo")
    List<Personale> personaleList;
}
