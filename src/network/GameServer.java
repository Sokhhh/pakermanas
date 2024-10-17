package network;

import entities.PacMan;

import java.io.*;
import java.net.*;

public class GameServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private PacMan pacman;

    public GameServer(PacMan pacman) {
        this.pacman = pacman;
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        clientSocket = serverSocket.accept();  // Wait for client connection
        System.out.println("Client connected!");

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        new Thread(this::listenToClient).start();  // Listen for client's ghost movements
    }

    private void listenToClient() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                // Synchronize ghost movement from client
                String[] position = inputLine.split(",");
                int ghostX = Integer.parseInt(position[0]);
                int ghostY = Integer.parseInt(position[1]);

                // Update ghost position on server
                // Ensure this updates the ghost locally in the server logic
                // (e.g., send ghost position to all players if necessary)
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPacManPosition(int x, int y) {
        if (out != null) {
            out.println(x + "," + y);  // Send Pac-Man's position to the client
        }
    }

    public void stop() {
        try {
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
