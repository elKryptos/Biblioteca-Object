package it.objectmethod.biblioteca.services;

import it.objectmethod.biblioteca.exceptions.NotFoundException;
import it.objectmethod.biblioteca.models.dtos.LibroDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.models.entities.Libro;
import it.objectmethod.biblioteca.models.mappers.LibroMapper;
import it.objectmethod.biblioteca.repositories.LibroRepository;
import it.objectmethod.biblioteca.utils.Constants;
import it.objectmethod.biblioteca.utils.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibroService {

    private final LibroRepository libroRepository;
    private final LibroMapper libroMapper;

    public ResponseWrapper<List<LibroDto>> getAll() {
        List<LibroDto> libroList = libroMapper.toDtoList(libroRepository.findAll());
        if (libroList.isEmpty()) {
            return new ResponseWrapper<>(Constants.LIBRO_NON_TROVATO);
        }
        return new ResponseWrapper<>(Constants.LIBRO_TROVATO, libroList);
    }

    public ResponseWrapper<LibroDto> create(LibroDto libroDto) {
        Libro entity = libroMapper.toEntity(libroDto);
        libroRepository.save(entity);
        return new ResponseWrapper<>(Constants.LIBRO_CREATO, libroDto);
    }

    public ResponseWrapper<LibroDto> update(Long libroId, LibroDto libroDto) {
        Libro libro = libroRepository.findById(libroId).orElseThrow(
                () -> new NotFoundException(Constants.LIBRO_NON_TROVATO));
        libroMapper.updateEntity(libro, libroDto);
        libroRepository.save(libro);
        return new ResponseWrapper<>(Constants.LIBRO_UPDATED, libroMapper.toDto(libro));
    }

    //@Scheduled(cron = "0/5 * * * * *")
    public void generateXLS() {
        FileStorageUtil.createStorageDirectory();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String filePath = "FileStorage/libro_report" + timestamp + ".xlsx";
        List<Libro> libri = libroRepository.findAll();
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();
        XSSFRow header = sheet.createRow(0);
        List<String> headerNames = Arrays.asList("id", "titolo", "autore",
                "isbn", "genere", "editor", "annoPubblicazione", "copie");;
        for (int i = 0; i < headerNames.size(); i++) {
            header.createCell(i).setCellValue(headerNames.get(i));
        }
        int limit = Math.min(headerNames.size(), libri.size());

        for (int i = 0; i < limit; i++) {  // Cambio a 0-based index
            XSSFRow row = sheet.createRow(i + 1); // Righe partono da 1 per i dati
            List<String> properties = libri.get(i).allProperties();

            // Se properties è null o vuota, possiamo anche gestire questo caso
            if (properties == null || properties.isEmpty()) {
                System.out.println("Nessuna proprietà disponibile per il libro all'indice " + i);
                continue; // Salta alla prossima iterazione
            }

            // Popolamento delle celle
            for (int j = 0; j < properties.size(); j++) {
                row.createCell(j).setCellValue(properties.get(j));
            }
        }
        try {
            wb.write(new FileOutputStream(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Errore durante la creazione del file");
        }
    }

    //@Scheduled(cron = "*/5 * * * * *")
    public void control() {
        List<Libro> libri = libroRepository.findAll();
        if (libri.isEmpty()) {
            throw new NotFoundException(Constants.LIBRO_NON_TROVATO);
        } else {
            this.generateXLS();
            int deletedCount = 0;
            for (Libro libro : libri) {
                String anno = new SimpleDateFormat("yyyy").format(libro.getAnnoPubblicazione());
                if (!libro.getIsbn().contains(anno)) {
                    libroRepository.delete(libro);
                    deletedCount++;
                }
            }
            System.out.println("Libri cancellati: " + deletedCount);
        }
    }
}
