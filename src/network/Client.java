package network;

import entities.Ghost;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;

    public void connectToServer() {
        try {
            socket = new Socket("localhost", 6666);
            System.out.println("Connected to the server!");

            // Handle communication with the server here
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Ghost getGhostFromServer() {
        // Get the ghost position from the server (simplified)
        return new Ghost(null, null, 0, 0);  // Example return value
    }
}