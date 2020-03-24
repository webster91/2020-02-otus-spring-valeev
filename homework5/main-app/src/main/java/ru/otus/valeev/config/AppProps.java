package ru.otus.valeev.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
@Getter
@Setter
public class AppProps {

    private String locale;
    private Integer correctAnswers;
    private BaseNames baseNames;

    @Getter
    @Setter
    public static class BaseNames {
        private String fileName;
    }

    @Getter
    @Setter
    public static class Bundle {
        private String url;
    }
}
