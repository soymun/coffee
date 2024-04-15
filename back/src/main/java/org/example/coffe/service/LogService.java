package org.example.coffe.service;

import org.example.coffe.model.CoffeeType;

public interface LogService {

    /**
     * Логи для обычных команд
     * @param type тип команды
     * @param message сообщение
     */
    void log(Integer type, String message, String machine);

    /**
     * Логи для команд создания кофе
     * @param type тип команды
     * @param message сообщение
     * @param coffeeType тип кофе
     */
    void logWithCoffee(Integer type, String message, CoffeeType coffeeType);
}
