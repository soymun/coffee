package org.example.coffe.service;

import org.example.coffe.model.CoffeeType;

public interface MachineService {

    void make(CoffeeType coffeeType);

    void clean(String machine);

    void restart(String machine);

    void stop(String machine);
}
