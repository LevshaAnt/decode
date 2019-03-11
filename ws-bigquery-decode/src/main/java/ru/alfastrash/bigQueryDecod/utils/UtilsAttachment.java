package ru.alfastrash.bigQueryDecod.utils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.core.io.InputStreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import ru.alfastrash.bigQueryDecod.entity.UploadModel;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.stream.Stream;

/**
 * @author VorobevAV
 * @version 1.0
 * Вспомогательный класс для обработки принимаемого файла
 */
@Slf4j
@Component
public class UtilsAttachment {

    @Autowired
    UtilsFile utilsFile;

    @Autowired
    UtilsDecode utilsDecode;

    /**
     * Сохранение и обработка переданного файла
     * @param model данные для обработки файла
     * @return обработанный файл
     * @exception IOException
     */
    public ResponseEntity<?> processingAttachment(UploadModel model) {
        log.info("Processing attachment file");

        if (model.getFile().isEmpty()) {
            log.error("File cannot be empty!");
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {

            return produceResponseEntityWithFile(workWithAttachment(model));

        } catch (IOException e) {
            log.error("Cannot write file on disk");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Создание ответа на запрос при работе с файлами
     * @param responseFile файл который будет передан клиенту
     * @return обработанный файл
     * @exception FileNotFoundException
     */
    private ResponseEntity<?> produceResponseEntityWithFile(File responseFile) throws FileNotFoundException {
        log.info("Call produceResponseEntityWithFile");

        InputStreamResource resource = new InputStreamResource(new FileInputStream(responseFile));

        //формирование заголовков для передачи файла клиенту
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream; charset=UTF-8");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(responseFile.length())
                .body(resource);
    }

    /**
     * Сохранение файла на диск с декодирование содержимого
     * @param model данные для обработки файла
     * @return stream файла
     * @exception IOException
     */
    private File workWithAttachment(UploadModel model) throws IOException {
        log.info("Call workWithAttachment");
        File requestFile = UtilsFile.saveAttachmentAndGetLink(model.getFile());

        Stream<String> fileDecodeStream = UtilsDecode.decodeComplexStrings(UtilsFile.readFileToStream(requestFile),model.getSeparator(),model.getPosition());

        return UtilsFile.writeStreamToFile(fileDecodeStream);
    }
}
