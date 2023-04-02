package de.cms04.importer.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "verbindungs_eintrag")
public class VerbindungsEintrag extends AuditedEntity {
    
    @Id
    @NotNull
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "zeitstempel", nullable = false)
    private LocalDateTime zeitstempel;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "ip", nullable = false, updatable = false)
    private Server server;

    @NotNull
    @Column(name = "eintrag_typ", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private EintragTyp eintragTyp;

    @NotNull
    @Column(name = "port", nullable = false, updatable = false)
    private Integer port;
    
}
