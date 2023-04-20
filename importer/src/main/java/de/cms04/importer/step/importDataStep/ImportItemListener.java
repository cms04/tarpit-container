package de.cms04.importer.step.importDataStep;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

import de.cms04.importer.model.VerbindungsEintrag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ImportItemListener implements ItemReadListener<RawData>, ItemProcessListener<VerbindungsEintrag, RawData>, ItemWriteListener<VerbindungsEintrag> {
    
    @Override
    public void onReadError(Exception ex) {
        log.error("Error while reading an object.", ex);
    }

    @Override
    public void onProcessError(VerbindungsEintrag item, Exception e) {
        log.error("Error processing VerbindungsEintrag item '{}'", item, e);
	}

    @Override
    public void onWriteError(Exception exception, Chunk<? extends VerbindungsEintrag> items) {
        log.error("Error writing VerbindungsEintrag items:", exception);
        items.forEach(item -> log.info("Affected item: '{}'", item));
	}

}