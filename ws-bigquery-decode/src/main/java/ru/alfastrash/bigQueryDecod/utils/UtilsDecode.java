package ru.alfastrash.bigQueryDecod.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author VorobevAV
 * @version 1.0
 * Вспомогательный класс для декодирования данных
 */
@Slf4j
@Component
public class UtilsDecode {

    /**
     * Расшифровка единичной строки
     * @param encode закодированная строка
     * @return декодированная строка
     */
    public static String decodeString(String encode) {
        log.info("Encode string {}",encode);
        return "Decode string ->  " + encode;
    }

    /**
     * Расшифровка списка строка
     * @param encodeList закодированная строки
     * @return декодируемые строки
     */
    public static Stream<String> decodeStrings(List<String> encodeList) {
        return decodeStrings(encodeList.stream());
    }

    /**
     * Расшифровка строк переданных как stream
     * @param encodeList стрим строк
     * @return декодированный стрим строк
     */
    public static Stream<String> decodeStrings(Stream<String> encodeList) {
        return encodeList.map(UtilsDecode::decodeString);
    }

    /**
     * Расшифровка строк содержащих несколько столбцов
     * @param encodeList стрим строк
     * @param separator разделитель используемый в строке
     * @param position позиция зашифрованнй строки, отсчёт начинается с позиции 0
     * @return декодированный стрим строк
     */
    public static Stream<String> decodeComplexStrings(Stream<String> encodeList, String separator, int position) {
        return encodeList.map((str) ->
                {
                    String[] elements = str.split(separator);
                    elements[position] = decodeString(elements[position]);
                    return String.join(separator, elements);
                }
        );
    }


}
