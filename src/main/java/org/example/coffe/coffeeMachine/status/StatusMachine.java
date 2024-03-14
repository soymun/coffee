package org.example.coffe.coffeeMachine.status;

public interface StatusMachine {

    /**
     * Получение статуса
     * @return статус
     */
    Status getStatus();

    /**
     * Изменение статуса
     * @param status статус
     */
    void setStatus(Status status);
}
