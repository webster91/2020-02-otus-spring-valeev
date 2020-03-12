package ru.otus.valeev.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${defaultEncoding}")
    private String defaultEncoding;

    @Value("${bundle.url}")
    private String baseNames;

    @Value("${locale}")
    private String locale;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Locale locale() {
        return Locale.forLanguageTag(locale);
    }

    @Bean
    public MessageSourceAccessor getMessageSourceAccessor(final MessageSource messageSource, Locale locale) {
        return new MessageSourceAccessor(messageSource, locale);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename(baseNames);
        ms.setDefaultEncoding(defaultEncoding);
        return ms;
    }
}
