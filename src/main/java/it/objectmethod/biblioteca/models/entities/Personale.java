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
public class Personale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personaleId;
    private Date dataAssunzione;

    @ManyToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "personaId")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "ruolo_id", referencedColumnName = "ruoloId")
    private Ruolo ruolo;
}
