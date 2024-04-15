package org.example.coffe.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.coffe.entities.CoffeeLog;
import org.example.coffe.entities.Command;
import org.example.coffe.model.CoffeeType;
import org.example.coffe.repositories.CoffeeLogRepository;
import org.example.coffe.repositories.CommandRepository;
import org.example.coffe.service.LogService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Primary
@Slf4j
public class LogServiceImpl implements LogService {

    private final CommandRepository commandRepository;
    private final CoffeeLogRepository coffeeLogRepository;

    @Override
    @Transactional
    public void log(Integer type, String message, String machine) {
        log.info("Выполнение команды {}, сообщение {}", type, message);
        saveCommand(type, message, machine);
    }

    @Override
    @Transactional
    public void logWithCoffee(Integer type, String message, CoffeeType coffeeType) {
        log.info("Приготовление кофе {}, сообщение {}", coffeeType, message);
        Command command = saveCommand(type, message, null);
        coffeeLogRepository.save(new CoffeeLog(coffeeType.name(), command.getId()));

    }

    private Command saveCommand(Integer type, String message, String machine){
        return commandRepository.save(new Command(message, type, LocalDateTime.now(), machine));
    }
}
