package de.cms04.importer.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.cms04.importer.model.EintragTyp;
import de.cms04.importer.model.Server;
import de.cms04.importer.model.VerbindungsEintrag;

@Repository
public interface VerbindungsEintragRepository extends JpaRepository<VerbindungsEintrag, Long> {
    
    @Query("SELECT v FROM VerbindungsEintrag v WHERE v.zeitstempel = :zeitstempel AND v.server = :server AND v.eintragTyp = :eintragTyp AND v.port = :port")
    Optional<VerbindungsEintrag> findByZeitstempelAndServerAndEintragTypAndPort(@Param("zeitstempel") LocalDateTime zeitstempel, @Param("server") Server server, @Param("eintragTyp") EintragTyp eintragTyp, @Param("port") Integer port);

}
