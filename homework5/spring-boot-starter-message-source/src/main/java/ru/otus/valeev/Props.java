package ru.otus.valeev;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "message")
@Getter
@Setter
public class Props {

    private String defaultEncoding;
    private String baseNames;
}
