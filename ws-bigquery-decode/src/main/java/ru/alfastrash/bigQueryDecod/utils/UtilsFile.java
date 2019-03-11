package ru.alfastrash.bigQueryDecod.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

import java.util.stream.Stream;
import java.util.stream.Collectors;


/**
 * @author VorobevAV
 * @version 1.0
 * Вспомогательный класс для работы с файлами
 */
@Slf4j
@Component
public class UtilsFile {

    /**
     * Сохранение файла на диск
     * Файл размещается в темповой директории
     * @param
     * @return
     * @exception IllegalStateException,IOException
     */
    public static File saveAttachmentAndGetLink(MultipartFile multipart) throws IllegalStateException, IOException {
        log.info("Call saveAttachmentAndGetLink");
        File resultFile = File.createTempFile(multipart.getOriginalFilename(), "encode");
        log.info("Template file for endecode {}",resultFile.getAbsolutePath());
        multipart.transferTo(resultFile);
        return resultFile;
    }

    /**
     * Чтение файла с диска в stream
     * @param inputFile ссылка на файл
     * @return strean<String>
     * @exception IOException
     */
    public static Stream<String> readFileToStream(File inputFile) throws IOException {
        log.info("Call readFileToStream");
        return Files.lines(inputFile.toPath(), StandardCharsets.UTF_8);
    }

    /**
     * Сохранение stream в файл
     * @param
     * @return
     * @exception IllegalStateException,IOException
     */
    public static File writeStreamToFile(Stream<String> inputStream) throws IOException {
        log.info("Call writeStreamToFile");

        File outputFile = File.createTempFile("decode", ".txt");
        log.info("Create template file for decode {}",outputFile.getAbsolutePath());

        Files.write(outputFile.toPath(), inputStream.collect(Collectors.toList()), StandardCharsets.UTF_8);
        return outputFile;
    }


}


