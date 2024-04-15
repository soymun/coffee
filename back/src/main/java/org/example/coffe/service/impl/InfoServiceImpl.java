package org.example.coffe.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.coffe.model.InfoCoffee;
import org.example.coffe.model.Status;
import org.example.coffe.service.InfoService;
import org.example.coffe.sockets.SocketContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InfoServiceImpl implements InfoService {

    private final SocketContext socketContext;

    @Override
    public Status getStatus(String machine) throws InterruptedException {
        return Status.valueOf(socketContext.status(machine));
    }

    @Override
    public InfoCoffee getInfo(String machine) throws InterruptedException {
        Map<String, String> map = Arrays.stream(socketContext.info(machine).split(",")).map(value -> {
            String[] values = value.split(":");
            return new Pair(values[0].trim(), values[1].trim());
        }).collect(Collectors.toMap(Pair::var1, Pair::var2));

        return new InfoCoffee(Integer.parseInt(map.get("cups")), Integer.parseInt(map.get("water")),Integer.parseInt(map.get("milk")),Integer.parseInt(map.get("bean")));
    }

    public List<String> machine(){
        return socketContext.getCoffeeMachines();
    }

    record Pair(String var1, String var2){}
}
