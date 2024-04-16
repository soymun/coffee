package org.example.coffee.coffeeMachine.info;

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

    public byte[] serialize(){
        byte[] byteArray = new byte[16];

        byteArray[0] = (byte) (cups >> 24);
        byteArray[1] = (byte) ((cups >> 16) & 0xFF);
        byteArray[2] = (byte) ((cups >> 8) & 0xFF);
        byteArray[3] = (byte) (cups & 0xFF);

        byteArray[4] = (byte) (water >> 24);
        byteArray[5] = (byte) ((water >> 16) & 0xFF);
        byteArray[6] = (byte) ((water >> 8) & 0xFF);
        byteArray[7] = (byte) (water & 0xFF);

        byteArray[8] = (byte) (milk >> 24);
        byteArray[9] = (byte) ((milk >> 16) & 0xFF);
        byteArray[10] = (byte) ((milk >> 8) & 0xFF);
        byteArray[11] = (byte) (milk & 0xFF);

        byteArray[12] = (byte) (bean >> 24);
        byteArray[13] = (byte) ((bean >> 16) & 0xFF);
        byteArray[14] = (byte) ((bean >> 8) & 0xFF);
        byteArray[15] = (byte) (bean & 0xFF);

        return byteArray;
    }

}
