package it.objectmethod.biblioteca.repositories;

import it.objectmethod.biblioteca.models.entities.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
}
