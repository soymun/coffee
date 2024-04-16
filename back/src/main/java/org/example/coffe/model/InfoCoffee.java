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


    public InfoCoffee(byte[] value) {
        cups = ((value[0] & 0xFF) << 24) |
                ((value[1] & 0xFF) << 16) |
                ((value[2] & 0xFF) << 8) |
                (value[3] & 0xFF);

        water = ((value[4] & 0xFF) << 24) |
                ((value[5] & 0xFF) << 16) |
                ((value[6] & 0xFF) << 8) |
                (value[7] & 0xFF);

        milk = ((value[8] & 0xFF) << 24) |
                ((value[9] & 0xFF) << 16) |
                ((value[10] & 0xFF) << 8) |
                (value[11] & 0xFF);

        bean = ((value[12] & 0xFF) << 24) |
                ((value[13] & 0xFF) << 16) |
                ((value[14] & 0xFF) << 8) |
                (value[15] & 0xFF);

    }
}
