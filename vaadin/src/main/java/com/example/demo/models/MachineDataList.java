package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MachineDataList {

    @JsonProperty("data")
    private List<String> data;
}
