package ru.otus.valeev.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.valeev.service.PredictionService;

import java.util.Random;

@Service
public class PredictionServiceImpl implements PredictionService {

    private final Random random = new Random();

    public String randomPrediction() {
        boolean rnd = random.nextBoolean();
        if (rnd) {
            return "Стоит прочитать";
        } else {
            return "Не стоит тратить время на эту книгу";
        }
    }
}
