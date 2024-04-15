package org.example.coffe.sockets;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocketInfo {

    private NetSocket netSocket;

    private Integer bias;

    private Buffer buffer;
}
