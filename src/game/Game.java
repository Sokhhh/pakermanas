package game;

import entities.PacMan;
import entities.Ghost;
import network.GameServer;
import network.GameClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;

public class Game extends JPanel implements KeyListener {
    private Maze maze;
    private PacMan pacman;
    private Ghost[] ghosts = new Ghost[1];  // One ghost for multiplayer
    private boolean running = true;
    private GameServer server;
    private GameClient client;
    private boolean isServer;

    public Game(boolean isServer) {
        this.isServer = isServer;
        this.maze = new Maze();
        this.pacman = new PacMan(maze, 1, 1);  // Pac-Man starts at (1, 1)
        this.ghosts[0] = new Ghost(maze, pacman, 28, 28);  // Ghost starts at (28, 28)

        // Set up the game window
        JFrame gameFrame = new JFrame("Pac-Man Multiplayer");
        gameFrame.setSize(600, 600);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.add(this);
        gameFrame.setVisible(true);
        gameFrame.addKeyListener(this);

        if (isServer) {
            // Start server for Pac-Man
            try {
                server = new GameServer(pacman);
                server.start(12345);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Start client for Ghost
            try {
                client = new GameClient(ghosts[0]);
                client.start("localhost", 12345);  // Replace "localhost" with server IP in real scenario
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        start();
    }

    public void start() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!running) {
                    timer.cancel();
                    return;
                }

                pacman.move();
                ghosts[0].move();

                if (isServer) {
                    // Server sends Pac-Man's position
                    server.sendPacManPosition(pacman.getX(), pacman.getY());
                } else {
                    // Client sends Ghost's position
                    client.sendGhostPosition(ghosts[0].getX(), ghosts[0].getY());
                }

                repaint();
            }
        }, 0, 100);  // Game updates every 100ms
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (isServer) {
            // Pac-Man controls (server side)
            if (key == KeyEvent.VK_W) pacman.setDirection(0, -1);  // Up
            if (key == KeyEvent.VK_S) pacman.setDirection(0, 1);   // Down
            if (key == KeyEvent.VK_A) pacman.setDirection(-1, 0);  // Left
            if (key == KeyEvent.VK_D) pacman.setDirection(1, 0);   // Right
        } else {
            // Ghost controls (client side)
            if (key == KeyEvent.VK_W) ghosts[0].setDirection(0, -1);  // Up
            if (key == KeyEvent.VK_S) ghosts[0].setDirection(0, 1);   // Down
            if (key == KeyEvent.VK_A) ghosts[0].setDirection(-1, 0);  // Left
            if (key == KeyEvent.VK_D) ghosts[0].setDirection(1, 0);   // Right
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        maze.render(g);
        pacman.render(g);
        ghosts[0].render(g);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}