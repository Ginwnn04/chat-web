package com.example.chat_web;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component

public class ServerCommandLineRunner implements CommandLineRunner {
    @Autowired
    public SocketIOServer server;

    @Override
    public void run(String... args) throws Exception {
        server.start();
        System.out.println("Server is running...");
    }
}
