package org.example.coffe.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.coffe.coffeeMachine.coffee.CoffeeMachine;
import org.example.coffe.exceptions.ResourcesExceptions;
import org.example.coffe.model.typeCoffe.CoffeeType;
import org.example.coffe.service.LogService;
import org.example.coffe.service.MachineService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Primary
@Slf4j
public class MachineServiceImpl implements MachineService {


    private final CoffeeMachine coffeeMachine;

    private final LogService logService;

    @Override
    public void make(CoffeeType coffeeType) {
        try {
            coffeeMachine.make(coffeeType);
            logService.logWithCoffee(1, "OK", coffeeType);
        } catch (ResourcesExceptions e) {
            logService.logWithCoffee(1, e.getMessage(), coffeeType);
            throw e;
        } catch (Exception e) {
            logService.logWithCoffee(1, "ERROR", coffeeType);
            throw e;
        }

    }

    @Override
    public void clean() {
        try {
            coffeeMachine.clean();
            logService.log(2, "OK");
        } catch (ResourcesExceptions e) {
            logService.log(2, e.getMessage());
            throw e;
        } catch (Exception e) {
            logService.log(2, "ERROR");
            throw e;
        }

    }

    @Override
    public void restart() {
        try {
            coffeeMachine.restart();
            logService.log(3, "OK");
        } catch (Exception e) {
            logService.log(3, "ERROR");
            throw e;
        }
    }

    @Override
    public void stop() {
        try {
            if (coffeeMachine.stop()) {
                logService.log(4, "OK");
            }
        } catch (Exception e) {
            logService.log(4, "ERROR");
            throw e;
        }
    }

    @Override
    public void start() {
        try {
            if (coffeeMachine.start()) {
                logService.log(5, "OK");
            }
        } catch (Exception e) {
            logService.log(5, "ERROR");
            throw e;
        }
    }
}
