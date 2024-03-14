package org.example.coffe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coffee_type_log")
public class CoffeeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coffee_type")
    private String coffeeType;

    @Column(name = "command_id")
    private Long commandId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "command_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Command command;

    public CoffeeLog(String coffeeType, Long commandId) {
        this.coffeeType = coffeeType;
        this.commandId = commandId;
    }
}
