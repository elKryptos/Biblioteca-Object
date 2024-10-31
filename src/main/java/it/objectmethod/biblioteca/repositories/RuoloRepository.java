package it.objectmethod.biblioteca.repositories;

import it.objectmethod.biblioteca.models.entities.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
}
