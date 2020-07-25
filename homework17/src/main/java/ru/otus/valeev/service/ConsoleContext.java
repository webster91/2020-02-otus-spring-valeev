package ru.otus.valeev.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.PrintStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ConsoleContext {
    private PrintStream out = System.out;
}
