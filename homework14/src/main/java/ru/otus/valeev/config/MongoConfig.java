package ru.otus.valeev.config;


import com.github.cloudyrock.mongock.SpringBootMongock;
import com.github.cloudyrock.mongock.SpringBootMongockBuilder;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.valeev.config.migration.DatabaseChangelog;
import ru.otus.valeev.config.migration.SpringMongockOrderedRunnerWrapper;

@Configuration
@AllArgsConstructor
public class MongoConfig {

    @Bean
    public SpringMongockOrderedRunnerWrapper mongockOrdered(MongoTemplate template, ApplicationContext springContext) {
        return new SpringMongockOrderedRunnerWrapper(getMongockRunner(template, springContext));
    }

    public SpringBootMongock getMongockRunner(MongoTemplate mongoTemplate, ApplicationContext springContext) {
        return new SpringBootMongockBuilder(mongoTemplate, DatabaseChangelog.class.getPackage().getName())
                .setApplicationContext(springContext)
                .setLockQuickConfig()
                .build();
    }

}
