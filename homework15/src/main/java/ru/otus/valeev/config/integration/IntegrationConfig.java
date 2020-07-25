package ru.otus.valeev.config.integration;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.PollableChannel;

@Configuration
@AllArgsConstructor
@Log4j2
public class IntegrationConfig {

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
    }


    @Bean
    public QueueChannel inVangaPredicate() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel outVangaPredicate() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PollableChannel exceptionVangaChanel() {
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow vangaFlow() {
        return IntegrationFlows.from(inVangaPredicate())
                .handle("predictionServiceImpl", "randomPrediction")
                .channel(outVangaPredicate())
                .log()
                .get();
    }

    @Bean
    IntegrationFlow exceptionVangaChannelFlow() {
        return IntegrationFlows.from(exceptionVangaChanel())
                .<MessagingException, String>transform(message -> {
                            log.error(message);
                            return "Произошла ошибка в сервисе вангования";
                        }
                )
                .get();
    }

}
