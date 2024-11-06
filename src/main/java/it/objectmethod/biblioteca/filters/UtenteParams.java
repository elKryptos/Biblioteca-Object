package it.objectmethod.biblioteca.filters;

import it.objectmethod.biblioteca.models.entities.Utente;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Data
@AllArgsConstructor
public class UtenteParams {
    private List<Integer> ids;
    private String nome;
    private String email;

    public Specification<Utente> getSpecification() {
        return Specification.<Utente>where(null)
                .and(equalsIdSpecification(ids))
                .and(equalsNameSpecification(nome))
                .and(equalsEmailSpecification(email));
    }

    private Specification<Utente> equalsIdSpecification(List<Integer> ids) {
        if (ids == null) return null;
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.in(root.get("persona").get("personaId")).value(ids));
    }

    private Specification<Utente> equalsNameSpecification(String nome) {
        if (nome == null) return null;
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("persona").get("nome"), nome.toLowerCase()));
    }

    private Specification<Utente> equalsEmailSpecification(String email) {
        if (email == null) return null;
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("persona").get("email"), email));
    }
}
