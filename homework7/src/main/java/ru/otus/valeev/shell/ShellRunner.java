package ru.otus.valeev.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.stereotype.Component;
import ru.otus.valeev.service.ConsoleService;

@Component
@RequiredArgsConstructor
@Order(InteractiveShellApplicationRunner.PRECEDENCE - 1000)
@Log4j2
public class ShellRunner implements CommandLineRunner {
    private final ConsoleService consoleService;

    @Override
    public void run(String... args) {
        consoleService.sendMessage("Приложение \"Библиотека\" готово к работе");
    }
}
