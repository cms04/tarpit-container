package de.cms04.importer.step.importDataStep;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
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
import de.cms04.importer.model.VerbindungsEintrag;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class ImportDataStepConfiguration {
    
    @Bean
    public Step importTarpitDataStep(ApplicationProperties applicationProperties, JobRepository jobRepository, PlatformTransactionManager pta, ItemReader<RawData> reader, ImportDataStepProcessor processor, ItemWriter<VerbindungsEintrag> writer, ImportItemListener listener) {
        return new StepBuilder("importTarpitDataStep", jobRepository)
            .<RawData, VerbindungsEintrag>chunk(applicationProperties.getChunkSize(), pta)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .faultTolerant()
            .skipLimit(Integer.MAX_VALUE)
            .skip(Exception.class)
            .listener((ItemReadListener<RawData>) listener)
            .listener((ItemProcessListener<VerbindungsEintrag, RawData>) listener)
            .listener((ItemWriteListener<VerbindungsEintrag>) listener)
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

    @Bean
    ItemWriter<VerbindungsEintrag> itemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<VerbindungsEintrag> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
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
