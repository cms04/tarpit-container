package de.cms04.importer.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImporterConfiguration {
    
    @Bean
    public Job importOwidCovidData(JobRepository jobRepository, Step downloadCsvStep, Step importTarpitDataStep) {
        return new JobBuilder("importTarpitData", jobRepository)
            .incrementer(new RunIdIncrementer())
            .flow(importTarpitDataStep)
            .end()
            .build();
    }

}
