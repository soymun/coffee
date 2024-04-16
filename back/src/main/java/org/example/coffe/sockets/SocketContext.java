package org.example.coffe.sockets;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import lombok.extern.slf4j.Slf4j;
import org.example.coffe.model.CoffeeType;
import org.example.coffe.model.InfoCoffee;
import org.example.coffe.model.Status;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class SocketContext extends AbstractVerticle {

    private final AtomicInteger atomicInteger = new AtomicInteger(1);

    private final Map<String, SocketInfo> context = new HashMap<>();

    @Override
    public void start() throws Exception {
        super.start();
        NetServer server = vertx.createNetServer();
        server.connectHandler(this::handleConnection);
        server.listen(9090);
        log.info("Server is start");
    }

    private void handleConnection(NetSocket socket) {
        log.info("Client with id connect {}", socket.writeHandlerID());
        Buffer bufferSocket = Buffer.buffer();
        socket.handler(bufferSocket::appendBuffer);
        context.put("Coffee machine " + atomicInteger.getAndIncrement(), new SocketInfo(socket, 0, bufferSocket));
        socket.closeHandler((var) -> {
            String name = null;
            for (Map.Entry<String, SocketInfo> entry : context.entrySet()) {
                if (entry.getValue().getNetSocket().remoteAddress().host().equals(socket.remoteAddress().host()) && entry.getValue().getNetSocket().remoteAddress().port() == socket.remoteAddress().port()) {
                    name = entry.getKey();
                    break;
                }
            }
            if (name != null) {
                context.remove(name);
            }
        });

    }

    public boolean makeCoffee(String type) {
        try {
            for (SocketInfo info : context.values()) {
                info.getNetSocket().write(Buffer.buffer(new byte[]{0}));
                Thread.sleep(200);
                boolean lock = getBoolean(info);
                if (!lock) {
                    continue;
                }
                info.getNetSocket().write(Buffer.buffer(new byte[]{1, (byte) CoffeeType.valueOf(type).ordinal()}));
                Thread.sleep(200);
                boolean resources = getBoolean(info);
                if (resources) {
                    info.getNetSocket().write(Buffer.buffer(new byte[]{2, (byte) CoffeeType.valueOf(type).ordinal()}));
                    Thread.sleep(700);
                    boolean response = getBoolean(info);
                    if (response) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            log.error("Error: ", e);
        }
        return false;
    }

    public boolean clean(String machine) throws InterruptedException {
        context.get(machine).getNetSocket().write(Buffer.buffer(new byte[]{4}));
        Thread.sleep(200);
        return getBoolean(context.get(machine));
    }

    public void stop(String machine) throws InterruptedException {
        context.get(machine).getNetSocket().write(Buffer.buffer(new byte[]{5}));
        Thread.sleep(200);
    }

    public void restart(String machine) throws InterruptedException {
        context.get(machine).getNetSocket().write(Buffer.buffer(new byte[]{6}));
        Thread.sleep(200);
    }

    public Status status(String machine) throws InterruptedException {
        context.get(machine).getNetSocket().write(Buffer.buffer(new byte[]{3}));
        Thread.sleep(300);
        return Status.getEnum(getEnum(context.get(machine)));
    }

    public InfoCoffee info(String machine) throws InterruptedException {
        context.get(machine).getNetSocket().write(Buffer.buffer(new byte[]{7}));
        Thread.sleep(300);
        return getInfoCoffee(context.get(machine));
    }

    public List<String> getCoffeeMachines() {
        return context.keySet().stream().toList();
    }

    private boolean getBoolean(SocketInfo socketInfo) {
        byte value = socketInfo.getBuffer().getByte(socketInfo.getBias());
        socketInfo.setBias(socketInfo.getBias() + 1);
        return value != 0;
    }

    private byte getEnum(SocketInfo socketInfo) {
        byte value = socketInfo.getBuffer().getByte(socketInfo.getBias());
        socketInfo.setBias(socketInfo.getBias() + 1);
        return value;
    }

    private InfoCoffee getInfoCoffee(SocketInfo socketInfo) {
        byte[] values = socketInfo.getBuffer().getBytes(socketInfo.getBias(), socketInfo.getBias() + 16);
        socketInfo.setBias(socketInfo.getBias() + 16);
        return new InfoCoffee(values);
    }
}
