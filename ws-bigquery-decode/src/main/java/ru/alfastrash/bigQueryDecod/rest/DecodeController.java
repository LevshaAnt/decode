package ru.alfastrash.bigQueryDecod.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ru.alfastrash.bigQueryDecod.entity.Info;
import ru.alfastrash.bigQueryDecod.entity.UploadModel;

import ru.alfastrash.bigQueryDecod.utils.UtilsDecode;
import ru.alfastrash.bigQueryDecod.utils.UtilsAttachment;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.stream.Collectors;

/**
 * @author VorobevAV
 * @version 1.0
 * REST контроллер
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "/api")
public class DecodeController {

    @Autowired
    Info infoService;

    @Autowired
    UtilsDecode utilsDecode;

    @Autowired
    UtilsAttachment utilsAttachment;

    /**
     * Информация о сервисе
     */
    @GetMapping(path = "/infoService")
    public Info getInfoService() {
        return infoService;
    }

    /**
     * Декодирует одну переданную строку
     * @param oneString строка для декодирования
     * @return декодированные строки
     */
    @GetMapping("/decodeOneString")
    public String decodeOneString(@RequestParam(value = "oneString") String oneString) {
        log.info("String for decode {}", oneString);
        return UtilsDecode.decodeString(oneString);
    }

    /**
     * Декодирует список строк
     * @param manyString список строк для декодирования
     * @return декодированные строки
     */
    @GetMapping("/decodeManyStrings")
    public List<String> decodeManyString(@RequestParam(value = "manyStrings") List<String> manyString) {
        log.info("Strings for decode {}, count {}", manyString, manyString.size());

        return UtilsDecode.decodeStrings(manyString).collect(Collectors.toList());
    }

    /**
     * Декодирует файл
     * @param uploadEncodeFile файл содержащий строки для декодирования, каждая строка на новой строке
     * @return декодированный файл
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadEncodeFile) {
        return utilsAttachment.processingAttachment(new UploadModel("", 0, uploadEncodeFile));
    }

    /**
     * Декодирует файл
     * @param model файл содержащиит несколько столбоц, один из столбцов должен быть декодирована
     * @return декодированный файл с исходной структурой
     */
    @PostMapping("/upload/modelFile")
    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute UploadModel model) {
        return utilsAttachment.processingAttachment(model);
    }

}

