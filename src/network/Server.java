package network;

import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;

    public void start() {
        try {
            serverSocket = new ServerSocket(6666);
            System.out.println("Server started. Waiting for clients...");
            socket = serverSocket.accept();
            System.out.println("Client connected!");

            // Handle game state between server and client here
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}