package network;

import java.io.*;
import java.net.*;
import entities.PacMan;

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
                // Parse ghost movement from client and update accordingly
                String[] position = inputLine.split(",");
                int ghostX = Integer.parseInt(position[0]);
                int ghostY = Integer.parseInt(position[1]);

                // You can update ghost position on the server side if needed
                System.out.println("Ghost moved to: " + ghostX + ", " + ghostY);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPacManPosition(int x, int y) {
        // Send Pac-Man's position to the client
        out.println(x + "," + y);
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}