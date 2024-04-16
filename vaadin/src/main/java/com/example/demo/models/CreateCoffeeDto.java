package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCoffeeDto {

    private String type;

    private boolean milk;

    private Integer portion;

    private Integer sugar;
}
