package de.cms04.importer.step.importDataStep;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import de.cms04.importer.config.ApplicationProperties;

@Configuration
public class ImportDataStepConfiguration {
    
    @Bean
    public Step importDataStep(ApplicationProperties applicationProperties, JobRepository jobRepository, PlatformTransactionManager pta, ItemReader<RawData> reader, ImportDataStepWriter writer) {
        return new StepBuilder("importDataStep", jobRepository)
            .<RawData, RawData>chunk(applicationProperties.getChunkSize(), pta)
            .reader(reader)
            .writer(writer)
            .faultTolerant()
            .skipLimit(Integer.MAX_VALUE)
            .skip(Exception.class)
            .build();
    }

    @Bean
    ItemReader<RawData> itemReader(ApplicationProperties applicationProperties) {
        FlatFileItemReader<RawData> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(applicationProperties.getFileLocation()));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<RawData> lineMapper() {
        DefaultLineMapper<RawData> lineMapper = new DefaultLineMapper<>();
        
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(";");
        lineTokenizer.setStrict(true);
        lineTokenizer.setNames("date", "time", "ip", "port", "type", "iso_code", "country");

        BeanWrapperFieldSetMapper<RawData> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(RawData.class);
        fieldSetMapper.setDistanceLimit(5);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }
}
