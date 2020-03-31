package ru.otus.valeev.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
@EnableConfigurationProperties(AppProps.class)
public class AppConfig {

    @Bean
    public Locale locale(final AppProps props) {
        return Locale.forLanguageTag(props.getLocale());
    }
}
