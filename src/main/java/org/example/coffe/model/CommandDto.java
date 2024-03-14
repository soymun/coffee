package org.example.coffe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.example.coffe.entities.CoffeeLog;

import java.time.LocalDateTime;

@Data
@Schema(name="Info command", description="Command info")
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
