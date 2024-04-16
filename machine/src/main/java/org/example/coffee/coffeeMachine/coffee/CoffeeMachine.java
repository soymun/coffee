package org.example.coffee.coffeeMachine.coffee;


import org.example.coffee.model.typeCoffe.CoffeeType;

import java.util.concurrent.locks.Lock;

public interface CoffeeMachine {

    /**
     * Производство кофе
     * @param coffeeType тип кофе
     */
    void make(CoffeeType coffeeType, boolean milk, int count, int sugar);

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

    Lock getLock();
}
