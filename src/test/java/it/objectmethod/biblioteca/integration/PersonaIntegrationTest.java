package it.objectmethod.biblioteca.integration;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import it.objectmethod.biblioteca.base.BaseIntegrationTest;
import it.objectmethod.biblioteca.enums.RuoloPersona;
import it.objectmethod.biblioteca.models.dtos.PersonaDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.models.mappers.PersonaMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PersonaIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final String LISTA_TROVATA = "Lista trovata!";

    static final List<PersonaDto> personaDto = List.of(
            PersonaDto.builder()
            .nome("Mario Rossi")
            .email("mario.rossi@example.com")
            .telefono("1234567890")
            .password("password123")
            .ruoloPersona(RuoloPersona.GOLD)
            .build(),

            PersonaDto.builder()
            .nome("Luca Bianchi")
            .email("luca.bianchi@example.com")
            .telefono("2345678901")
            .password("password123")
            .ruoloPersona(RuoloPersona.SILVER)
            .build()
    );

    //ResponseWrapper<List<PersonaDto>> expected = new ResponseWrapper<>(LISTA_TROVATA, personaDto);

    @Test
    void shouldReturnStatoTest_whenFindAllPersonas() {


        //ARRANGE --> ARRANGIAMENTO
        ResponseWrapper<List<PersonaDto>> expected = new ResponseWrapper<>(LISTA_TROVATA, personaDto);


        // ACT --> AZIONE
        ResponseWrapper<List<PersonaDto>> actual = given()
                .port(this.port)
//                .pathParam("id", 1L)
                .contentType(ContentType.JSON)
                .when()
                .get("/persona/all")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .prettyPeek()
                .body()
                .as(new TypeRef<ResponseWrapper<List<PersonaDto>>>() {});

        // ASSERT --> ASSERZIONE
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("type.password")
                .isEqualTo(expected);
    }
}
