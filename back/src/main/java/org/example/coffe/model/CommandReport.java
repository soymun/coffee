package org.example.coffe.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommandReport {

    private Long id;

    private String message;

    private String machine;

    private String command;

    private String time;

    private String coffeeLog;
}
