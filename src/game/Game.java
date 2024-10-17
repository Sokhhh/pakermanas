package game;

import entities.GhostCPU;
import entities.PacMan;
import entities.Ghost;
import network.GameServer;
import network.GameClient;
import ui.GameOverScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Game extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private PacMan pacman;
    private List<Ghost> ghosts = new ArrayList<>();  // List of ghosts (multiple ghosts)
    private Maze maze;

    private boolean isMultiplayer;
    private boolean isServer;
    private String serverIP;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Game(boolean isMultiplayer, boolean isServer, String serverIP) {
        this.isMultiplayer = isMultiplayer;
        this.isServer = isServer;
        this.pacman = new PacMan(11, 21);  // Start Pac-Man at (1,1)
        ghosts.add(new Ghost(11,11));  // List of ghosts (multiple ghosts)

        this.maze = new Maze();  // Generate the maze
        this.serverIP = serverIP;

        if (isMultiplayer) {
            if (isServer) {
                startServer();
            } else {
                startClient();
            }
        }
        else {
            ghosts.add(new GhostCPU(11,11));
        }

        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(100, this);
        timer.start();
    }

    public Game(boolean isMultiplayer, boolean isServer) {
        this.isMultiplayer = isMultiplayer;
        this.isServer = isServer;
        this.pacman = new PacMan(11, 21);  // Start Pac-Man at (1,1)
        this.maze = new Maze();  // Generate the maze

        if (isMultiplayer) {
            ghosts.add(new Ghost(11,11));
            if (isServer) {
                startServer();
            } else {
                startClient();
            }
        }
        else {
            //this.ghost = new GhostCPU(28,28);
            ghosts.add(new GhostCPU(11,10));
            ghosts.add(new GhostCPU(11,11));
            ghosts.add(new GhostCPU(11,12));


        }

        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(100, this);
        timer.start();
    }

    // Multiplayer - Server Side (Host)
    private void startServer() {
        try {
            serverSocket = new ServerSocket(12345);  // Listen on port 12345
            System.out.println("Waiting for client...");
            clientSocket = serverSocket.accept();  // Accept client connection
            System.out.println("Client connected.");

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Thread to listen for ghost movement from the client
            new Thread(this::listenToClient).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Multiplayer - Client Side (Ghost)
    private void startClient() {
        try {
            clientSocket = new Socket(serverIP, 12345);  // Connect to the host server
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Thread to listen for Pac-Man movement from the server
            new Thread(this::listenToServer).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Host listens to the ghost's movements (from client)
    private void listenToClient() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                String[] position = inputLine.split(",");
                int ghostX = Integer.parseInt(position[0]);
                int ghostY = Integer.parseInt(position[1]);

                if (!ghosts.isEmpty()) {
                    ghosts.get(0).setPosition(ghostX, ghostY);  // Update first ghost's position
                }

                repaint();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Client listens to Pac-Man's movements (from host)
    private void listenToServer() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                String[] position = inputLine.split(",");
                int pacX = Integer.parseInt(position[0]);
                int pacY = Integer.parseInt(position[1]);

                // Update Pac-Man's position on client's screen
                pacman.setPosition(pacX, pacY);
                repaint();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Send Pac-Man's position (from host to client)
    private void sendPacmanPosition() {
        if (out != null && isServer) {
            out.println(pacman.getX() + "," + pacman.getY());  // Send Pac-Man position to client
        }
    }

    // Send Ghost's position (from client to host)
    private void sendGhostPosition() {
        if (out != null && !isServer) {
            Ghost clientGhost = ghosts.get(0);  // In multiplayer, assume first ghost is controlled by the client
            out.println(clientGhost.getX() + "," + clientGhost.getY());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pacman.move(maze);  // Move Pac-Man based on the current direction
        for (Ghost ghost : ghosts) {
            ghost.move(maze, pacman);
        }

        if (isMultiplayer) {
            if (isServer) {
                sendPacmanPosition();  // Send Pac-Man's position to the client
            } else {
                sendGhostPosition();  // Send Ghost's position to the host
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        maze.render(g);  // Draw the maze
        pacman.render(g);  // Draw Pac-Man
        for (Ghost ghost : ghosts) {
            ghost.render(g);
        }
        checkCollision();
        checkWinCondition();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (!isMultiplayer || isServer) {
            // Pac-Man controls (for server/host)
            if (key == KeyEvent.VK_W) pacman.setDirection(0, -1);  // Up
            if (key == KeyEvent.VK_S) pacman.setDirection(0, 1);   // Down
            if (key == KeyEvent.VK_A) pacman.setDirection(-1, 0);  // Left
            if (key == KeyEvent.VK_D) pacman.setDirection(1, 0);   // Right
        }

        if (isMultiplayer && !isServer) {
            // Ghost controls (for client)
            if (key == KeyEvent.VK_W && !ghosts.isEmpty()) ghosts.get(0).setDirection(0, -1);   // Up
            if (key == KeyEvent.VK_S && !ghosts.isEmpty()) ghosts.get(0).setDirection(0, 1);    // Down
            if (key == KeyEvent.VK_A && !ghosts.isEmpty()) ghosts.get(0).setDirection(-1, 0);   // Left
            if (key == KeyEvent.VK_D && !ghosts.isEmpty()) ghosts.get(0).setDirection(1, 0);    // Right
        }
    }

    private void checkCollision() {
        for (Ghost ghost : ghosts) {
            if (ghost.collidesWith(pacman)) {
                System.out.println("Game Over! Pac-Man has been caught by the ghost.");
                timer.stop(); // Stop the game loop
                GameOverScreen.display("Game Over! Pac-Man was caught.");
                break;
            }
        }
    }

    private void checkWinCondition() {
        if (maze.allPelletsCollected()) {
            timer.stop(); // Stop the game loop
            GameOverScreen.display("You Win! All pellets collected.");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
