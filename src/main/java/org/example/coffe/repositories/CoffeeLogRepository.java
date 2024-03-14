package org.example.coffe.repositories;

import org.example.coffe.entities.CoffeeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeLogRepository extends JpaRepository<CoffeeLog, Long> {
}
