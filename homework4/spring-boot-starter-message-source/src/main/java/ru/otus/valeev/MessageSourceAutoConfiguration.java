package ru.otus.valeev;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
@EnableConfigurationProperties(Props.class)
@AllArgsConstructor
@Slf4j
public class MessageSourceAutoConfiguration {

    private final Props props;

    @Bean
    @ConditionalOnMissingBean
    public MessageSourceAccessor getMessageSourceAccessor(final MessageSource messageSource, final Locale locale) {
        return new MessageSourceAccessor(messageSource, locale);
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename(props.getBaseNames());
        ms.setDefaultEncoding(props.getDefaultEncoding());
        return ms;
    }
}
