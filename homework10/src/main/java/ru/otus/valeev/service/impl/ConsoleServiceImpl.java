package ru.otus.valeev.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.valeev.service.ConsoleContext;
import ru.otus.valeev.service.ConsoleService;

import java.io.PrintStream;

@Service
public class ConsoleServiceImpl implements ConsoleService {
    private final PrintStream out;

    public ConsoleServiceImpl(ConsoleContext consoleContext) {
        this.out = consoleContext.getOut();
    }

    @Override
    public void sendMessage(String message) {
        out.println(message);
    }
}