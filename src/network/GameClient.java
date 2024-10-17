package network;

import entities.Ghost;

import java.io.*;
import java.net.*;

public class GameClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Ghost ghost;

    public GameClient(Ghost ghost) {
        this.ghost = ghost;
    }

    public void start(String host, int port) throws IOException {
        clientSocket = new Socket(host, port);
        System.out.println("Connected to server!");

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        new Thread(this::listenToServer).start();  // Listen for Pac-Man's position from the server
    }

    private void listenToServer() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                // Synchronize Pac-Man movement from server
                String[] position = inputLine.split(",");
                int pacX = Integer.parseInt(position[0]);
                int pacY = Integer.parseInt(position[1]);

                // Update Pac-Man position on client
                // Synchronize the local Pac-Man position with the server's position
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendGhostPosition(int x, int y) {
        if (out != null) {
            out.println(x + "," + y);  // Send the ghost's position to the server
        }
    }

    public void stop() {
        try {
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
