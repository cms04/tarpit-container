package de.cms04.importer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = "isoCode", callSuper = false)
@Table(name = "land")
public class Land extends AuditedEntity {
    
    @Id
    @NotNull
    @Column(name = "iso_code", nullable = false, unique = true)
    private String isoCode;

    @NotNull
    @Column(name = "land_name", nullable = false)
    private String landName;

}
