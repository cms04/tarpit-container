package de.cms04.importer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.cms04.importer.model.Land;

@Repository
public interface LandRepository extends JpaRepository<Land, String> {
    
}
