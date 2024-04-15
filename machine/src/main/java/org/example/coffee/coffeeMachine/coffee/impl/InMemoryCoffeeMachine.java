package org.example.coffee.coffeeMachine.coffee.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.coffee.coffeeMachine.coffee.CoffeeMachine;
import org.example.coffee.coffeeMachine.info.Info;
import org.example.coffee.coffeeMachine.status.Status;
import org.example.coffee.coffeeMachine.status.StatusMachine;
import org.example.coffee.exceptions.ResourcesExceptions;
import org.example.coffee.model.typeCoffe.CoffeeRecipe;
import org.example.coffee.model.typeCoffe.CoffeeType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Primary
@RequiredArgsConstructor
@Slf4j
public class InMemoryCoffeeMachine implements CoffeeMachine {

    @Value("${machine.clean}")
    private Integer clean;

    @Value("${machine.restart}")
    private Integer restart;

    private boolean stop = false;

    private final Lock lock = new ReentrantLock();

    private final Map<CoffeeType, CoffeeRecipe> coffeeTypes;

    private final StatusMachine statusMachine;

    private final Info info;

    @Override
    public void make(CoffeeType coffeeType) {
        try {
            lock.lock();
            if (!stop) {

                log.debug("Make {}", coffeeType);

                if(info.isEnoughFor(coffeeTypes.get(coffeeType))) {
                    info.allocate(coffeeTypes.get(coffeeType));
                    statusMachine.setStatus(Status.MAKE);
                    Thread.sleep(coffeeTypes.get(coffeeType).getTimeToMake());
                    statusMachine.setStatus(Status.READY);
                } else {
                    throw new ResourcesExceptions("Resources");
                }

                log.debug("Make success");

            } else {
                throw new ResourcesExceptions("Off");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clean() {
        try {
            lock.lock();
            if (!stop) {

                log.debug("Clean");

                statusMachine.setStatus(Status.CLEAN);
                Thread.sleep(clean);
                statusMachine.setStatus(Status.READY);
            } else {
                throw new ResourcesExceptions("Off");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void restart() {
        try {
            lock.lock();

            log.debug("Restart");

            statusMachine.setStatus(Status.STOP);
            Thread.sleep(restart);
            stop = false;
            statusMachine.setStatus(Status.READY);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean stop() {
        try {
            lock.lock();
            if(!stop) {
                log.debug("Stop");

                stop = true;
                statusMachine.setStatus(Status.STOP);
                return true;
            }
        } finally {
            lock.unlock();
        }
        return false;
    }

    @Override
    public boolean start() {
        try {
            lock.lock();
            if(stop) {
                stop = false;
                Thread.sleep(100);
                statusMachine.setStatus(Status.READY);
                return true;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return false;
    }

    public Lock getLock() {
        return lock;
    }
}
