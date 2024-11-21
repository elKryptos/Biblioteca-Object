package it.objectmethod.biblioteca.integration;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import it.objectmethod.biblioteca.base.BaseIntegrationTest;
import it.objectmethod.biblioteca.models.dtos.LibroDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class LibroIntegrationTest extends BaseIntegrationTest {

    private static final String LIBRO_TROVATO = "Libro trovato!";
    private static final String LIBRO_UPDATED = "Libro aggiornato correttamente";

    static final List<LibroDto> libroDto = List.of(
            LibroDto.builder()
                    .titolo("Il Codice Da Vinci")
                    .autore("Dan Brown")
                    .isbn("9780307474278")
                    .genere("Thriller")
                    .editor("Mondadori")
                    .annoPubblicazione(LocalDate.of(2003, 3, 18))
                    .copie(10)
                    .build(),
            LibroDto.builder()
                    .titolo("La Solitudine dei Numeri Primi")
                    .autore("Paolo Giordano")
                    .isbn("9788834605047")
                    .genere("Romanzo")
                    .editor("Mondadori")
                    .annoPubblicazione(LocalDate.of(2008, 9, 17))
                    .copie(5)
                    .build()
    );

    @Test
    void shouldReturnAll_whenGetAll() {

        ResponseWrapper<List<LibroDto>> expected = new ResponseWrapper<>(LIBRO_TROVATO ,libroDto);

        ResponseWrapper<List<LibroDto>> actual = given()
                .port(this.port)
                .contentType(ContentType.JSON)
                .when()
                .get("libro/all")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .prettyPeek()
                .body()
                .as(new TypeRef<>() {
                });

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnUpdate_whenUpdate() {

        ResponseWrapper<LibroDto> expected = new ResponseWrapper<>(LIBRO_UPDATED, libroDto.get(0));

        ResponseWrapper<LibroDto> actual = given()
                .port(this.port)
                .pathParam("id", 1L)
                .contentType(ContentType.JSON)
                .body(libroDto.get(0))
                .when()
                .put("libro/update/{id}")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .prettyPeek()
                .body()
                .as(new TypeRef<>() {});

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

}
