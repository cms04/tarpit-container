package de.cms04.importer.config;

import java.time.LocalDateTime;

import org.slf4j.MDC;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StepExecutionListener implements JobExecutionListener {

    private static final String START_TIME = "start.time";

    public static final String SUCCEEDED_ELEMENTS = "succeeded.elements";
    public static final String FAILED_ELEMENTS = "failed.elements";
    
    @Override
    public void beforeJob(JobExecution jobExecution) {
        MDC.put(SUCCEEDED_ELEMENTS, "0");
        MDC.put(FAILED_ELEMENTS, "0");

        LocalDateTime startTime = LocalDateTime.now();
        MDC.put(START_TIME, startTime.toString());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.parse(MDC.get(START_TIME));

        String succeeded = MDC.get(SUCCEEDED_ELEMENTS);
        String failed = MDC.get(FAILED_ELEMENTS);

        log.info("Job {} started at {} and finished at {}", jobExecution.getJobInstance().getJobName(), startTime, endTime);
        log.info("Successfully wrote {} elements. Skipped {} elements.", succeeded, failed);
    }

}
