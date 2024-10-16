package network;

import java.io.*;
import java.net.*;
import entities.Ghost;

public class GameClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private Ghost ghost;

    public GameClient(Ghost ghost) {
        this.ghost = ghost;
    }

    public void start(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        System.out.println("Connected to server!");

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        new Thread(this::listenToServer).start();  // Listen for Pac-Man's position updates
    }

    private void listenToServer() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                // Parse Pac-Man movement from server and update accordingly
                String[] position = inputLine.split(",");
                int pacmanX = Integer.parseInt(position[0]);
                int pacmanY = Integer.parseInt(position[1]);

                // You can update Pac-Man's position on the client side if needed
                System.out.println("Pac-Man moved to: " + pacmanX + ", " + pacmanY);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendGhostPosition(int x, int y) {
        // Send ghost's position to the server
        out.println(x + "," + y);
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}