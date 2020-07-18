package ru.otus.valeev.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;
import ru.otus.valeev.service.MigrationService;

@Service
@AllArgsConstructor
@Slf4j
public class MigrationServiceImpl implements MigrationService {
    private final JobLauncher jobLauncher;
    private final Job importUserJob;

    @Override
    public void executeMigration() {
        try {
            jobLauncher.run(importUserJob, new JobParameters());
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.error("Произошла ошибка при запуске миграции");
        }
    }
}
