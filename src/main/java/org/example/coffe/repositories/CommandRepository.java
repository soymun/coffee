package org.example.coffe.repositories;

import org.example.coffe.entities.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, Long> {

}
