package org.example.coffe.repositories;

import org.example.coffe.entities.Command;
import org.example.coffe.model.CommandReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommandRepository extends JpaRepository<Command, Long> {

    @Query(value = "select new org.example.coffe.model.CommandReport(c.id, c.message, coalesce(c.machine, ''), dc.name, c.time, coalesce(cl.coffeeType, '')) from Command c left join DcCommand dc on c.commandId = dc.id left join CoffeeLog cl on cl.commandId = c.id")
    List<CommandReport> getCommandReport();
}
