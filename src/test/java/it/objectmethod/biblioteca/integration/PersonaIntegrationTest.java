package it.objectmethod.biblioteca.integration;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import it.objectmethod.biblioteca.base.BaseIntegrationTest;
import it.objectmethod.biblioteca.enums.RuoloPersona;
import it.objectmethod.biblioteca.models.dtos.PersonaDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PersonaIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String LISTA_TROVATA = "Lista trovata!";
    private static final String PERSONA_TROVATA = "Persona trovata!";
    private static final String PERSONA_UPDATE = "Persona updated!";
    private static final String PERSONA_CREATA = "Persona creata!";

    static final List<PersonaDto> personaDto = List.of(
            PersonaDto.builder()
                    .nome("Mario Rossi")
                    .email("mario.rossis@example.com")
                    .telefono("1234567890")
                    .password("123456")
                    .ruoloPersona(RuoloPersona.GOLD)
                    .build(),

            PersonaDto.builder()
                    .nome("Luca Bianchi")
                    .email("luca.bianchis@example.com")
                    .telefono("2345678901")
                    .password("123456")
                    .ruoloPersona(RuoloPersona.SILVER)
                    .build()
    );

    @Test
    void shouldReturnStatoTest_whenFindAllPersonas() {

        //ARRANGE --> ARRANGIAMENTO
        ResponseWrapper<List<PersonaDto>> expected = new ResponseWrapper<>(LISTA_TROVATA, personaDto);

        // ACT --> AZIONE
        ResponseWrapper<List<PersonaDto>> actual = given()
                .port(this.port)
                .contentType(ContentType.JSON)
                .when()
                .get("/persona/all")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .prettyPeek()
                .body()
                .as(new TypeRef<>() {
                });

        // ASSERT --> ASSERZIONE
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("type.password")
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnPersonaById_whenFindPersonaById() {

        ResponseWrapper<PersonaDto> expected = new ResponseWrapper<>(PERSONA_TROVATA, personaDto.get(1));

        ResponseWrapper<PersonaDto> actual = given()
                .port(this.port)
                .pathParam("id", 2L)
                .contentType(ContentType.JSON)
                .when()
                .get("persona/{id}")
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
                .ignoringFields("type.password")
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnPersonaUpdate_whenUpdatePersonaById() {

        ResponseWrapper<PersonaDto> expected = new ResponseWrapper<>(PERSONA_UPDATE, personaDto.get(0));

        ResponseWrapper<PersonaDto> actual = given()
                .port(this.port)
                .pathParam("id", 1L)
                .contentType(ContentType.JSON)
                .body(personaDto.get(0))
                .when()
                .put("persona/update/{id}")
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
                .ignoringFields("type.password")
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnPersonaCreate_whenCreatePersona() {

        ResponseWrapper<PersonaDto> expected = new ResponseWrapper<>(PERSONA_CREATA);

        ResponseWrapper<PersonaDto> actual = given()
                .port(this.port)
                .contentType(ContentType.JSON)
                .body(personaDto.get(0))
                .when()
                .post("persona/create")
                .then()
                .statusCode(201)
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
