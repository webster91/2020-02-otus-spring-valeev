package ru.otus.valeev.service.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.otus.valeev.service.ConsoleService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConsoleServiceImpl implements ConsoleService {
    PrintStream printStream = System.out;
    InputStream inputStream = System.in;


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
