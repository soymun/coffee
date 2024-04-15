package org.example.coffe.repositories;

import org.example.coffe.entities.DcCommand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DcCommandDcRepository extends JpaRepository<DcCommand, Long> {
}
