package org.example.coffe.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.coffe.exceptions.ResourcesExceptions;
import org.example.coffe.model.CoffeeType;
import org.example.coffe.service.LogService;
import org.example.coffe.service.MachineService;
import org.example.coffe.sockets.SocketContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Primary
@Slf4j
public class MachineServiceImpl implements MachineService {


    private final SocketContext socketContext;

    private final LogService logService;

    @Override
    public void make(CoffeeType coffeeType) {
        try {
            if (socketContext.makeCoffee(coffeeType.toString())) {
                logService.logWithCoffee(1, "OK", coffeeType);
            } else {
                throw new ResourcesExceptions("Невозможно создать кофе");
            }
        } catch (ResourcesExceptions e) {
            logService.logWithCoffee(1, e.getMessage(), coffeeType);
            throw e;
        } catch (Exception e) {
            logService.logWithCoffee(1, "ERROR", coffeeType);
            throw e;
        }

    }

    @Override
    public void clean(String machine) {
        try {
            if (socketContext.clean(machine)) {
                logService.log(2, "OK", machine);
            } else {
                throw new ResourcesExceptions("Кофе машина отключена");
            }
        } catch (ResourcesExceptions e) {
            logService.log(2, e.getMessage(), machine);
            throw e;
        } catch (Exception e) {
            logService.log(2, "ERROR", machine);
        }
    }

    @Override
    public void restart(String machine) {
        try {
            socketContext.restart(machine);
            logService.log(3, "OK", machine);
        } catch (Exception e) {
            logService.log(3, "ERROR", machine);
        }
    }

    @Override
    public void stop(String machine) {
        try {
            socketContext.stop(machine);
            logService.log(4, "OK", machine);
        } catch (Exception e) {
            logService.log(4, "ERROR", machine);
        }
    }

}
