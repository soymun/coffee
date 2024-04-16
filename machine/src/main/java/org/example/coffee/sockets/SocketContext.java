package org.example.coffee.sockets;

import lombok.extern.slf4j.Slf4j;
import org.example.coffee.coffeeMachine.coffee.CoffeeMachine;
import org.example.coffee.coffeeMachine.info.Info;
import org.example.coffee.coffeeMachine.info.InfoCoffee;
import org.example.coffee.coffeeMachine.status.Status;
import org.example.coffee.coffeeMachine.status.StatusMachine;
import org.example.coffee.model.typeCoffe.CoffeeRecipe;
import org.example.coffee.model.typeCoffe.CoffeeType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SocketContext implements InitializingBean {

    @Value("${back.host}")
    private String host;

    @Value("${back.port}")
    private Integer port;

    private BufferedReader bufferedReader;

    private final Socket socket = new Socket();

    private final CoffeeMachine machine;

    private final Info info;

    private final Map<CoffeeType, CoffeeRecipe> coffeeTypes;

    private final StatusMachine statusMachine;

    @Autowired
    public SocketContext(CoffeeMachine machine, Info info, Map<CoffeeType, CoffeeRecipe> coffeeTypes, StatusMachine statusMachine) {
        this.machine = machine;
        this.info = info;
        this.coffeeTypes = coffeeTypes;
        this.statusMachine = statusMachine;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        socket.connect(new InetSocketAddress(host, port));
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        CompletableFuture.runAsync(this::work);
    }

    public void work() {
        while (true) {
            try {
                processMessage(Arrays.stream(bufferedReader.readLine().split(",")).map(string -> {
                    String[] value = string.split(":");
                    return new Pair(value[0], value[1]);
                }).collect(Collectors.toMap(Pair::var1, Pair::var2)));
            } catch (SocketException e) {
                while (!socket.isConnected()) {
                    try {
                        socket.connect(new InetSocketAddress(host, port));
                    } catch (Exception ignore) {

                    }
                }
            } catch (Exception e) {
                log.error("Error: ", e);
            }
        }
    }

    private void processMessage(Map<String, String> values) throws IOException {
        if (values.containsKey("type")) {
            switch (values.get("type")) {
                case "check-lock" -> lockAndStatus();
                case "check-resources" -> resources(values);
                case "create-coffee" -> make(values);
                case "status" ->
                        socket.getOutputStream().write(convertString(statusMachine.getStatus().toString()).getBytes());
                case "clean" -> {
                    try {
                        machine.clean();
                    } catch (Exception e) {
                        socket.getOutputStream().write(convertString("errors:" + e.getMessage()).getBytes());
                    }
                }
                case "stop" -> machine.stop();
                case "restart" -> machine.restart();
                case "info" -> info();
            }
        }
    }

    private void lockAndStatus() throws IOException {
        boolean result = false;
        try {
            result = machine.getLock().tryLock();
        } finally {
            if (result) {
                machine.getLock().unlock();
            }
        }
        if (statusMachine.getStatus().equals(Status.STOP)) {
            result = false;
        }
        socket.getOutputStream().write(convertBoolean(result).getBytes());
        socket.getOutputStream().flush();
    }

    private void resources(Map<String, String> values) throws IOException {
        String coffee = values.getOrDefault("coffee", "");
        CoffeeRecipe coffeeType = coffeeTypes.get(CoffeeType.valueOf(coffee));
        socket.getOutputStream().write(convertBoolean(info.isEnoughFor(coffeeType)).getBytes());
        socket.getOutputStream().flush();
    }

    private void make(Map<String, String> values) throws IOException {
        String coffee = values.getOrDefault("coffee", "");
        try {
            info.allocate(coffeeTypes.get(CoffeeType.valueOf(coffee)));
            machine.make(CoffeeType.valueOf(coffee));
            socket.getOutputStream().write(convertBoolean(true).getBytes());
            socket.getOutputStream().flush();
        } catch (Exception e) {
            socket.getOutputStream().write((convertString("errors:" + e.getMessage())).getBytes());
            socket.getOutputStream().flush();
        }
    }

    private void info() throws IOException {
        InfoCoffee infoCoffee = info.getInfo();
        String value = "cups:" + infoCoffee.getCups() +
                ",water:" + infoCoffee.getWater() +
                ",milk:" + infoCoffee.getMilk() +
                ",bean:" + infoCoffee.getBean();
        socket.getOutputStream().write(convertString(value).getBytes());
        socket.getOutputStream().flush();
    }

    public String convertBoolean(boolean result) {
        if (result) {
            return "4|true";
        } else {
            return "5|false";
        }
    }

    public String convertString(String value) {
        return value.length() + "|" + value;
    }


    record Pair(String var1, String var2) {
    }
}
