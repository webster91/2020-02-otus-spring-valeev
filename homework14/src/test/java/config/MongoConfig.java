package config;

import com.github.cloudyrock.mongock.SpringBootMongock;
import com.github.cloudyrock.mongock.SpringBootMongockBuilder;
import config.migration.DatabaseChangelog;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@AllArgsConstructor
public class MongoConfig {

    @Bean
    public SpringBootMongock getMongockRunner(MongoTemplate mongoTemplate, ApplicationContext springContext) {
        return new SpringBootMongockBuilder(mongoTemplate, DatabaseChangelog.class.getPackage().getName())
                .setApplicationContext(springContext)
                .setLockQuickConfig()
                .build();
    }

}
