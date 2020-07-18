package ru.otus.valeev.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MoodIndicator implements HealthIndicator {

    private final Random random = new Random();

    @Override
    public Health health() {
        boolean serverIsDown = random.nextBoolean();
        if (serverIsDown) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Мне очень плохо!")
                    .build();
        } else {
            return Health.up().withDetail("message", "Я очень счастлив!").build();
        }
    }
}
