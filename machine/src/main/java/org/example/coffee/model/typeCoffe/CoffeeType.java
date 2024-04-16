package org.example.coffee.model.typeCoffe;

public enum CoffeeType {
    ESPRESSO,
    LATTE,
    CAPPUCCINO;

    public static CoffeeType getType(int value){
        return CoffeeType.values()[value];
    }
}
