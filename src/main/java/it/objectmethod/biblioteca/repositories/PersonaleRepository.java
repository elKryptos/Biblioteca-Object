package it.objectmethod.biblioteca.repositories;

import it.objectmethod.biblioteca.models.entities.Personale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaleRepository extends JpaRepository<Personale, Long> {
}
