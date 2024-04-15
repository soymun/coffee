package org.example.coffe.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name="Info dictionary", description="Command dictionary")
public class DcCommandDto {

    private Long id;

    private String name;
}
