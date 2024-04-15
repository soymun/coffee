package org.example.coffe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "logging_command")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Column(name = "machine")
    private String machine;

    @Column(name = "command_id")
    private Integer commandId;

    private LocalDateTime time;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "command")
    private CoffeeLog coffeeLog;

    public Command(String message, Integer commandId, LocalDateTime time, String machine) {
        this.message = message;
        this.commandId = commandId;
        this.time = time;
        this.machine = machine;
    }

    public Command(Integer commandId, LocalDateTime time) {
        this.commandId = commandId;
        this.time = time;
    }
}
