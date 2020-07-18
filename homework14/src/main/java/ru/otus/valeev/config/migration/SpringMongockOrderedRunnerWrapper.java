package ru.otus.valeev.config.migration;

import com.github.cloudyrock.mongock.SpringBootMongock;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

@Order(-110)
@AllArgsConstructor
public class SpringMongockOrderedRunnerWrapper implements ApplicationRunner {

    private final SpringBootMongock mongockInstance;

    @Override
    public void run(ApplicationArguments args) {
        mongockInstance.run(args);
    }
}
