package org.example.coffe.service;

import org.example.coffe.model.typeCoffe.CoffeeType;

public interface MachineService {

    void make(CoffeeType coffeeType);

    void clean();

    void restart();

    void stop();

    void start();
}
