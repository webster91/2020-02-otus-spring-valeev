package ru.otus.valeev.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(InteractiveShellApplicationRunner.PRECEDENCE - 1000)
@Log4j2
public class ShellRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info("Приложение \"Библиотека\" готово к работе");
    }
}
