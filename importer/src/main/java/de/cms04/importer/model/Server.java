package de.cms04.importer.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(of = "ip", callSuper = false)
@ToString(exclude = "eintraege")
@Table(name = "server")
public class Server extends AuditedEntity {
    
    @Id
    @NotNull
    @Column(name = "ip", nullable = false, unique = true)
    private String ip;

    @ManyToOne
    @JoinColumn(name = "land")
    public Land land;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "server")
    private Set<VerbindungsEintrag> eintraege;

}
