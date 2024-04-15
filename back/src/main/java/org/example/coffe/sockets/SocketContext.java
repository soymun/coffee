package org.example.coffe.sockets;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import lombok.extern.slf4j.Slf4j;
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
                info.getNetSocket().write("type:check-lock\n");
                Thread.sleep(200);
                boolean lock = Boolean.parseBoolean(getInfo(info));
                if (!lock) {
                    continue;
                }

                info.getNetSocket().write("type:check-resources,coffee:" + type + "\n");
                Thread.sleep(200);
                boolean resources = Boolean.parseBoolean(getInfo(info));
                if (resources) {
                    info.getNetSocket().write("type:create-coffee,coffee:" + type + "\n");
                    Thread.sleep(700);
                    boolean response = Boolean.parseBoolean(getInfo(info));
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

    public String clean(String machine) {
        context.get(machine).getNetSocket().write("type:clean\n");
        try {
            Thread.sleep(600);
            String info = getInfo(context.get(machine));
            return info.split(":")[1];
        } catch (Exception ignore) {

        }
        return "Ok";
    }

    public void stop(String machine){
        context.get(machine).getNetSocket().write("type:stop\n");
    }

    public void restart(String machine) {
        context.get(machine).getNetSocket().write("type:restart\n");
    }

    public String status(String machine) throws InterruptedException {
        context.get(machine).getNetSocket().write("type:status\n");
        Thread.sleep(300);
        return getInfo(context.get(machine));
    }

    public String info(String machine) throws InterruptedException {
        context.get(machine).getNetSocket().write("type:info\n");
        Thread.sleep(300);
        return getInfo(context.get(machine));
    }

    public List<String> getCoffeeMachines() {
        return context.keySet().stream().toList();
    }

    private String getInfo(SocketInfo socketInfo) {
        StringBuilder infoLength = new StringBuilder();
        String value = "";
        int start = socketInfo.getBias();
        int end = socketInfo.getBias() + 1;
        while (!(value = socketInfo.getBuffer().getString(start, end)).equals("|")) {
            infoLength.append(value);
            start = end;
            end += 1;
        }
        start = end;
        int length = Integer.parseInt(infoLength.toString());
        String result = socketInfo.getBuffer().getString(start, start + length);
        socketInfo.setBias(start + length);
        return result.trim();
    }
}
