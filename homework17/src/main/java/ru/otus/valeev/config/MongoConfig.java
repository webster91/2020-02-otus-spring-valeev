package ru.otus.valeev.config;


import com.github.cloudyrock.mongock.SpringBootMongock;
import com.github.cloudyrock.mongock.SpringBootMongockBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.valeev.config.migration.DatabaseChangelog;

@Configuration
public class MongoConfig {

    @Bean
    public SpringBootMongock mongock(MongoTemplate mongoTemplate, ApplicationContext springContext) {
        return new SpringBootMongockBuilder(mongoTemplate, DatabaseChangelog.class.getPackage().getName())
                .setApplicationContext(springContext)
                .setLockQuickConfig()
                .build();
    }
}
