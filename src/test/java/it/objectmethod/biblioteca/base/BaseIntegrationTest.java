package it.objectmethod.biblioteca.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import it.objectmethod.biblioteca.BibliotecaApplication;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cache.CacheManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;


/*
 * Classe base astratta per i test di integrazione. Fornisce metodi di utilità per la gestione del caching,
 * creazione di stubs, e verifica delle risposte HTTP, utilizzando WireMock e altre librerie per il testing.
 */
@SpringBootTest(classes = BibliotecaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@Sql(scripts = {
        "classpath:/dbH2/01-truncate.sql",
        "classpath:/dbH2/02-insertPersona.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public abstract class BaseIntegrationTest {

    @LocalServerPort
    protected int port;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CacheManager cacheManager;

    /**
     * Svuota tutte le cache disponibili nel sistema.
     * Utilizzato nei test per garantire che i dati in cache non influenzino i risultati.
     */
    public void clearCache() {
        if (cacheManager != null) {
            this.cacheManager.getCacheNames().forEach(cache -> Objects.requireNonNull(this.cacheManager.getCache(cache)).clear());
        }
    }

    /**
     * Crea un'istanza di WireMockExtension con configurazioni di default per il testing.
     *
     * @return un'istanza configurata di {@link WireMockExtension}.
     */
    public static WireMockExtension createStub() {
        return WireMockExtension.newInstance().failOnUnmatchedRequests(true).options(wireMockConfig().dynamicPort().notifier(new Slf4jNotifier(true))).build();
    }

    /**
     * Verifica che non ci siano stub definiti ma non utilizzati durante il test.
     * In caso di stub inutilizzati, il test fallirà con un messaggio di errore.
     *
     * @param stubs lista di estensioni WireMock da verificare.
     */
    public static void verifyNoUnusedStubs(WireMockExtension... stubs) {
        for (WireMockExtension stub : stubs) {
            Set<StubMapping> usedMappings = stub.getAllServeEvents().stream().map(ServeEvent::getStubMapping).collect(Collectors.toSet());
            List<StubMapping> defineMappings = stub.getStubMappings();
            for (StubMapping mapping : defineMappings) {
                if (!usedMappings.contains(mapping)) {
                    Assertions.fail("A stub mapping was defined but never used: \r\n" + mapping);
                }
            }
        }
    }

    /**
     * Crea un matcher per confrontare una data e ora (OffsetDateTime) nel formato accettato da WireMock.
     *
     * @param odt la data e ora da confrontare.
     * @return un'istanza di {@link StringValuePattern} per confrontare una data e ora.
     */
    public StringValuePattern equalToDateTime(OffsetDateTime odt) {
        return WireMock.equalToDateTime(odt.toLocalDateTime());
    }

    /**
     * Crea un matcher per confrontare una data (LocalDate) con l'inizio della giornata, nel fuso orario di Roma.
     *
     * @param ld la data da confrontare.
     * @return un'istanza di {@link StringValuePattern} per confrontare una data.
     */
    public StringValuePattern equalToDateAtStartOfDay(LocalDate ld) {
        return WireMock.equalToDateTime(ld.atStartOfDay(ZoneId.of("Europe/Rome")));
    }

    /**
     * Crea un matcher per confrontare un oggetto BigDecimal nel formato JSON utilizzato da WireMock.
     *
     * @param bd il valore BigDecimal da confrontare.
     * @return un'istanza di {@link StringValuePattern} per confrontare un BigDecimal in formato JSON.
     */
    public StringValuePattern equalToBigDecimal(BigDecimal bd) {
        return equalToJsonObj(bd);
    }

    /**
     * Crea un matcher per confrontare un oggetto serializzato in formato JSON utilizzando WireMock.
     *
     * @param obj l'oggetto da confrontare.
     * @return un'istanza di {@link StringValuePattern} per confrontare l'oggetto in formato JSON.
     */
    public StringValuePattern equalToJsonObj(Object obj) {
        try {
            return equalToJson(objectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Crea una risposta HTTP di successo (200 OK) con il contenuto serializzato in formato JSON.
     *
     * @param o l'oggetto da restituire come JSON.
     * @return una definizione di risposta HTTP con status 200 e contenuto JSON.
     */
    public ResponseDefinitionBuilder okJsonObj(Object o) {
        final String s;
        try {
            s = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
        return WireMock.okJson(s);
    }

    /**
     * Crea una risposta HTTP di errore con un codice di stato personalizzato e un contenuto serializzato in formato JSON.
     *
     * @param httpStatusCode il codice di stato HTTP.
     * @param o              l'oggetto da restituire come JSON.
     * @return una definizione di risposta HTTP con il codice di stato specificato e contenuto JSON.
     */
    public ResponseDefinitionBuilder koJsonObj(int httpStatusCode, Object o) {
        final String s;
        try {
            s = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
        return WireMock.jsonResponse(s, httpStatusCode);
    }

    /**
     * Legge il contenuto di una risorsa di test dalla classpath e la restituisce come stringa.
     *
     * @param path il percorso della risorsa all'interno della classpath.
     * @return il contenuto della risorsa come stringa.
     * @throws UncheckedIOException se si verifica un errore durante la lettura della risorsa.
     */
    String readTestResource(String path) {
        final ClassPathResource resource = new ClassPathResource(path);
        try {
            return resource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
