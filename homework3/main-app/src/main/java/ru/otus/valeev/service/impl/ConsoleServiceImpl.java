package ru.otus.valeev.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.valeev.service.ConsoleService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class ConsoleServiceImpl implements ConsoleService {

    private final PrintStream printStream = System.out;
    private final InputStream inputStream = System.in;

    @Override
    public void sendMessage(String message) {
        printStream.println(message);
    }

    @Override
    public String getMessage() {
        Scanner scanner = new Scanner(inputStream);
        return scanner.nextLine();
    }
}
