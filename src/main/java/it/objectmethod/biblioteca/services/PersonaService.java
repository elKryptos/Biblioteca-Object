package it.objectmethod.biblioteca.services;

import it.objectmethod.biblioteca.enums.RuoloPersona;
import it.objectmethod.biblioteca.exceptions.NotFoundException;
import it.objectmethod.biblioteca.models.dtos.PersonaDto;
import it.objectmethod.biblioteca.models.dtos.ResponseWrapper;
import it.objectmethod.biblioteca.models.entities.Persona;
import it.objectmethod.biblioteca.models.mappers.PersonaMapper;
import it.objectmethod.biblioteca.repositories.PersonaRepository;
import it.objectmethod.biblioteca.utils.Constants;
import it.objectmethod.biblioteca.utils.FileStorageUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Data
@RequiredArgsConstructor
public class PersonaService {

    private final PersonaRepository personaRepository;
    private final PersonaMapper personaMapper;
    private final FileStorageUtil fileStorageUtil;
    private final BCryptPasswordEncoder bCryptEncoder;
    private final Lock lock = new ReentrantLock();

    public ResponseWrapper<List<PersonaDto>> getAll() {
        List<Persona> personas = personaRepository.findAll();
        List<PersonaDto> personaDtos = personaMapper.toDtoList(personas);
        if (personaDtos.isEmpty()) {
            throw new NotFoundException(Constants.LISTA_NON_TROVATA);
        }
        return new ResponseWrapper<>(Constants.LISTA_TROVATA, personaDtos);
    }

    public ResponseWrapper<PersonaDto> getById(long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.PERSONA_NON_TROVATA));
        return new ResponseWrapper<>(Constants.PERSONA_TROVATA, personaMapper.toDto(persona));
    }

    public ResponseWrapper<PersonaDto> create(PersonaDto personaDto) {
        Persona persona = personaMapper.toEntity(personaDto);
        persona.setPassword(bCryptEncoder.encode(personaDto.getPassword()));
        persona.setRuoloPersona(RuoloPersona.BRONCE);
        personaRepository.save(persona);
        return new ResponseWrapper<>(Constants.PERSONA_CREATA);
    }

    public ResponseWrapper<List<PersonaDto>> createFast(List<PersonaDto> personaDto) {
        List<Persona> personaList = personaMapper.toEntityList(personaDto);
        personaRepository.saveAll(personaList);
        return new ResponseWrapper<>("Persone create", personaMapper.toDtoList(personaList));
    }

    public ResponseWrapper<PersonaDto> update(Long id, PersonaDto personaDto) {
        Persona persona = personaRepository.getById(id);
        personaMapper.updateEntity(persona, personaDto);
        personaRepository.save(persona);
        return new ResponseWrapper<>(Constants.PERSONA_UPDATE, personaMapper.toDto(persona));
    }

    public String generaXLS() {
        FileStorageUtil.createStorageDirectory();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String filePath = "FileStorage/persona_report_" + timestamp + ".xlsx";
        FileStorageUtil.setFilePath(filePath);

        List<Persona> persone = personaRepository.findAll();
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();
        XSSFRow header = sheet.createRow(0);
        List<String> headerNames = Arrays.asList("id", "nome", "email", "telefono");
        for (int i = 0; i < headerNames.size(); i++) {
            header.createCell(i).setCellValue(headerNames.get(i));
        }
        for (int i = 1; i <= persone.size(); i++) {
            XSSFRow newRow = sheet.createRow(i);
            List<String> properties = persone.get(i - 1).allProperties();
            for (int j = 0; j < properties.size(); j++) {
                newRow.createCell(j).setCellValue(properties.get(j));
            }
        }
        try {
            wb.write(new FileOutputStream(filePath));
            return "File creato";
        } catch (IOException e) {
            throw new RuntimeException("Errore durante la creazione del file");
        }
    }

    //@Scheduled ( cron = "0/10 * * * * *")
    public String formatControlXLS() {
        lock.lock();
        FileStorageUtil.createStorageDirectory();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String filePath = "FileStorage/persona_report_" + timestamp + ".xlsx";
        FileStorageUtil.setFilePath(filePath);

        List<Persona> personaList = personaRepository.findAll();
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Tutti i dati");
        XSSFSheet sheetErrors = wb.createSheet("Nomi non formattati");
        XSSFRow header = sheet.createRow(0);
        List<String> headerNames = Arrays.asList("id", "nome", "email", "telefono");
        for (int i = 0; i < headerNames.size(); i++) {
            header.createCell(i).setCellValue(headerNames.get(i));
        }
        for (int i = 1; i <= personaList.size(); i++) {
            Persona persona = personaList.get(i - 1);
            List<String> properties = persona.allProperties();
            XSSFRow newRow = sheet.createRow(i);
            for (int j = 0; j < properties.size(); j++) {
                newRow.createCell(j).setCellValue(properties.get(j));
            }
            if (!fileStorageUtil.controlFormatName(persona.getNome())) {
                XSSFRow errorRow = sheetErrors.createRow(sheetErrors.getLastRowNum() + 1);
                for (int j = 0; j < properties.size(); j++) {
                    errorRow.createCell(j).setCellValue(properties.get(j));
                }
            }
        }
        try {
            wb.write(new FileOutputStream(filePath));
            return "File creato";
        } catch (IOException e) {
            throw new RuntimeException("Errore durante la creazione del file");
        } finally {
          lock.unlock();
        }
    }

    //@Scheduled(cron = "0/20 * * * * *")
    public void corregiNomi() {
        lock.lock();
        String filepath = FileStorageUtil.getFilePath();
        if (filepath != null) {
            correggiNomi(filepath);
        }
        lock.unlock();
    }

    public void correggiNomi(String filepath) {
        try (FileInputStream fis = new FileInputStream(filepath);
             XSSFWorkbook wb = new XSSFWorkbook(fis)) {

            XSSFSheet sheetErrors = wb.getSheet("Nomi non formattati");
            for (int i = 0; i <= sheetErrors.getLastRowNum(); i++) {
                XSSFRow row = sheetErrors.getRow(i);
                if (row == null) continue;

                Long id = Long.parseLong(row.getCell(0).getStringCellValue());
                String nomeNonFormattato = row.getCell(1).getStringCellValue();
                String nomeFormattato = fileStorageUtil.correctFormatName(nomeNonFormattato);

                Optional<Persona> personaOptional = personaRepository.findById(id);
                if (personaOptional.isPresent()) {
                    Persona persona = personaOptional.get();
                    persona.setNome(nomeFormattato);
                    personaRepository.save(persona);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore durante la lettura del file", e);
        }
    }

}
