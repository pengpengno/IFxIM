package com.ifx.connect.connection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class SocketConnect implements ServerConnect<ServerSocket> {
    @Resource(name = "socketPool")
    private ExecutorService socketPool;

    private ConcurrentHashMap<InetAddress,Socket> socketConcurrentMap = new ConcurrentHashMap<>();

    @Override
    public void getConnect(ServerSocket socket) {
        socketPool.submit(() -> {
            Socket accept = null;
            try {
                accept = socket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            InetAddress inetAddress = accept.getInetAddress();
            socketConcurrentMap.put(inetAddress,accept);
        });

    }

}
