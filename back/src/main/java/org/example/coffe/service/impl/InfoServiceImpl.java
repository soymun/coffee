package org.example.coffe.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.coffe.model.InfoCoffee;
import org.example.coffe.model.Status;
import org.example.coffe.service.InfoService;
import org.example.coffe.sockets.SocketContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InfoServiceImpl implements InfoService {

    private final SocketContext socketContext;

    @Override
    public Status getStatus(String machine) throws InterruptedException {
        return socketContext.status(machine);
    }

    @Override
    public InfoCoffee getInfo(String machine) throws InterruptedException {
        return socketContext.info(machine);
    }

    public List<String> machine() {
        return socketContext.getCoffeeMachines();
    }
}
