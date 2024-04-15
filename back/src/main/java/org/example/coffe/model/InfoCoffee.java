package org.example.coffe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoCoffee {

    private int cups;
    private int water;
    private int milk;
    private int bean;

}
