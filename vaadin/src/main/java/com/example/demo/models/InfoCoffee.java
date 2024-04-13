package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoCoffee {

    @JsonProperty(value = "cups")
    private int cups;
    @JsonProperty(value = "water")
    private int water;
    @JsonProperty(value = "milk")
    private int milk;
    @JsonProperty(value = "bean")
    private int bean;

}
