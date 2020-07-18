package ru.otus.valeev.config.integration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

@Configuration
@AllArgsConstructor
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
    public IntegrationFlow vangaFlow() {
        return IntegrationFlows.from("inVangaPredicate")
                .handle("predictionServiceImpl", "randomPrediction")
                .log()
                .channel("outVangaPredicate")
                .get();
    }
}
