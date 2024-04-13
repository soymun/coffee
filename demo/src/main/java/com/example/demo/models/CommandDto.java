package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommandDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "commandId")
    private Integer commandId;

    @JsonProperty(value = "time")
    private LocalDateTime time;

    @JsonProperty(value = "coffeeLog")
    private CoffeeLogDto coffeeLog;
}
