package ru.alfastrash.bigQueryDecod.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Getter;

/**
 * @author VorobevAV
 * @version 1.0
 * Информация о сервисе и разработчике
 */
@Getter
@Component("info")
@JacksonXmlRootElement
public class Info {

    //автор
    @Value("${info.author}")
    private String author;

    //контактный email
    @Value("${info.email}")
    private String email;

    //версия декодера
    @Value("${info.versionDecodeFile}")
    private int versionDecodeFile;
}
