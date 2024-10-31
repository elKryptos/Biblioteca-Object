package it.objectmethod.biblioteca.repositories;

import it.objectmethod.biblioteca.models.entities.MovimentoLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentoLibroRepository extends JpaRepository<MovimentoLibro, Long> {
}
