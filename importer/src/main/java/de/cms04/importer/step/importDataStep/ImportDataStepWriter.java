package de.cms04.importer.step.importDataStep;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ImportDataStepWriter implements ItemWriter<RawData> {

    @Override
    public void write(@NonNull Chunk<? extends RawData> chunk) {
        chunk.forEach(r -> {
            log.info("Object: {}", r);
        });
    }
    
}
