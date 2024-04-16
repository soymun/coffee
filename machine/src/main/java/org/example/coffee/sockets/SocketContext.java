package org.example.coffee.sockets;

import lombok.extern.slf4j.Slf4j;
import org.example.coffee.coffeeMachine.coffee.CoffeeMachine;
import org.example.coffee.coffeeMachine.info.Info;
import org.example.coffee.coffeeMachine.status.Status;
import org.example.coffee.coffeeMachine.status.StatusMachine;
import org.example.coffee.model.typeCoffe.CoffeeRecipe;
import org.example.coffee.model.typeCoffe.CoffeeType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class SocketContext implements InitializingBean {

    @Value("${back.host}")
    private String host;

    @Value("${back.port}")
    private Integer port;

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
        CompletableFuture.runAsync(this::work);
    }

    public void work() {
        while (true) {
            try {
                processMessage(socket.getInputStream().read());
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

    private void processMessage(int values) throws IOException {
        if (values != -1) {
            switch (values) {
                case 0 -> lockAndStatus();
                case 1 -> resources(socket.getInputStream().read(), socket.getInputStream().read(), socket.getInputStream().read(), socket.getInputStream().read());
                case 2 -> make(socket.getInputStream().read(), socket.getInputStream().read(), socket.getInputStream().read(), socket.getInputStream().read());
                case 3 -> {
                    socket.getOutputStream().write((byte) statusMachine.getStatus().ordinal());
                    socket.getOutputStream().flush();
                }
                case 4 -> {
                    try {
                        machine.clean();
                    } catch (Exception e) {
                        socket.getOutputStream().write(convertBoolean(false));
                    }
                }
                case 5 -> machine.stop();
                case 6 -> machine.restart();
                case 7 -> info();
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
        socket.getOutputStream().write(convertBoolean(result));
        socket.getOutputStream().flush();
    }

    private void resources(int value,int count, int milkByte, int sugar) throws IOException {
        boolean milk = milkByte != 0;
        CoffeeRecipe coffeeType = coffeeTypes.get(CoffeeType.getType(value));
        socket.getOutputStream().write(convertBoolean(info.isEnoughFor(coffeeType, milk, count, sugar)));
        socket.getOutputStream().flush();
    }

    private void make(int value,int count, int milkByte, int sugar) throws IOException {
        try {
            boolean milk = milkByte != 0;
            info.allocate(coffeeTypes.get(CoffeeType.getType(value)), milk, count, sugar);
            machine.make(CoffeeType.getType(value), milk, count, sugar);
            socket.getOutputStream().write(convertBoolean(true));
            socket.getOutputStream().flush();
        } catch (Exception e) {
            socket.getOutputStream().write(convertBoolean(false));
            socket.getOutputStream().flush();
        }
    }

    private void info() throws IOException {
        socket.getOutputStream().write(info.getInfo().serialize());
        socket.getOutputStream().flush();
    }

    public byte convertBoolean(boolean result) {
        if (result) {
            return 1;
        } else {
            return 0;
        }
    }
}
