package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CommandListDto {

    @JsonProperty("data")
    private List<CommandDto> data;
}
