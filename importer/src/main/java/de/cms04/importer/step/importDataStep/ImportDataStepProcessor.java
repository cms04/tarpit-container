package de.cms04.importer.step.importDataStep;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import de.cms04.importer.model.EintragTyp;
import de.cms04.importer.model.Land;
import de.cms04.importer.model.Server;
import de.cms04.importer.model.VerbindungsEintrag;
import de.cms04.importer.repository.LandRepository;
import de.cms04.importer.repository.ServerRepository;
import de.cms04.importer.repository.VerbindungsEintragRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ImportDataStepProcessor implements ItemProcessor<RawData, VerbindungsEintrag> {

    private final LandRepository landRepository;
    private final ServerRepository serverRepository;
    private final VerbindungsEintragRepository verbindungsEintragRepository;

    @Override
    @Nullable
    public VerbindungsEintrag process(@NonNull RawData item) throws Exception {
        Land land = landRepository.findById(item.getIsoCode()).orElseGet(() -> {
            Land l = new Land();
            l.setIsoCode(item.getIsoCode());
            l.setLandName(item.getCountry());
            return landRepository.save(l);
        });

        Server server = serverRepository.findById(item.getIp()).orElseGet(() -> {
            Server s = new Server();
            s.setIp(item.getIp());
            s.setLand(land);
            s.setEintraege(new HashSet<>());
            return serverRepository.save(s);
        });

        LocalDateTime timestamp = mapTimestamp(item.getDate(), item.getTime());
        EintragTyp eintragTyp = mapEintragTyp(item.getType());
        VerbindungsEintrag eintrag = verbindungsEintragRepository.findByZeitstempelAndServerAndEintragTypAndPort(timestamp, server, eintragTyp, item.getPort()).orElseGet(() -> {
            VerbindungsEintrag e = new VerbindungsEintrag();
            e.setZeitstempel(timestamp);
            e.setServer(server);
            e.setEintragTyp(eintragTyp);
            e.setPort(item.getPort());
            return e;
        });

        return eintrag;
    }

    private LocalDateTime mapTimestamp(String dateString, String timeString) {
        LocalDate date = LocalDate.parse(dateString);
        LocalTime time = LocalTime.parse(timeString);
        return LocalDateTime.of(date, time);
    }

    private EintragTyp mapEintragTyp(String eintrag) {
        return EnumUtils.getEnum(EintragTyp.class, eintrag.toUpperCase());
    }
    
}
