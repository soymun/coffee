package org.example.coffe.coffeeMachine.coffee;

import org.example.coffe.model.typeCoffe.CoffeeType;

public interface CoffeeMachine {

    /**
     * Производство кофе
     * @param coffeeType тип кофе
     */
    void make(CoffeeType coffeeType);

    /**
     * Очиста кофе машины
     */
    void clean();

    /**
     * Перезагрузка кофе машины
     */
    void restart();

    /**
     * Отключение кофе машины
     */
    boolean stop();

    /**
     * Включение кофе машины
     */
    boolean start();
}
