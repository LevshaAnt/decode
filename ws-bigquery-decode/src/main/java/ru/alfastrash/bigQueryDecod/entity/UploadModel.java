package ru.alfastrash.bigQueryDecod.entity;

import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Data;

/**
 * @author VorobevAV
 * @version 1.0
 * Модель класс которая содержит необходимую информацию для обработки многоколоночного файла
 */
@Data
@ToString
@AllArgsConstructor
public class UploadModel {

    //разделитель в переданном файле
    @NotNull
    private String separator;

    //номер столбца в котором храняться данные
    @NotNull
    private int position;

    //переданный файл
    @ToString.Exclude
    @NotNull
    private MultipartFile file;
}
