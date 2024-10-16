package game;

import entities.PacMan;
import entities.Ghost;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends JPanel implements KeyListener {
    private boolean multiplayer;
    private Maze maze;
    private PacMan pacman;
    private Ghost[] ghosts = new Ghost[5];
    private boolean running = true;
    private JFrame gameFrame;

    public Game(boolean multiplayer) {
        this.multiplayer = multiplayer;
        this.maze = new Maze();
        this.pacman = new PacMan(maze, 1, 1);  // Pacman starts at (1,1)

        for (int i = 0; i < ghosts.length; i++) {
            ghosts[i] = new Ghost(maze, pacman, 28, 28);  // Ghosts start at (28, 28)
        }

        // Set up the game window (UI)
        gameFrame = new JFrame("Pac-Man Game");
        gameFrame.setSize(600, 600);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.add(this);
        gameFrame.setVisible(true);
        gameFrame.addKeyListener(this);

        start();
    }

    public void start() {
        // Game loop using Timer for updating visuals and game state
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!running) {
                    timer.cancel();
                    return;
                }

                // Move Pac-Man and ghosts
                pacman.move();
                for (Ghost ghost : ghosts) {
                    ghost.move();
                }

                repaint();  // Redraw the game

                // Check if Pac-Man was caught
                if (pacman.isCaught(ghosts)) {
                    System.out.println("Game Over! Pac-Man was caught.");
                    running = false;
                }
            }
        }, 400, 150);  // Game updates every 100ms (10 frames per second)
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw maze, pacman, and ghosts
        maze.render(g);
        pacman.render(g);
        for (Ghost ghost : ghosts) {
            ghost.render(g);
        }
    }

    // Handle keyboard input for Pac-Man movement (WASD keys)
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            pacman.setDirection(0, -1);  // Move up (W)
        } else if (key == KeyEvent.VK_S) {
            pacman.setDirection(0, 1);  // Move down (S)
        } else if (key == KeyEvent.VK_A) {
            pacman.setDirection(-1, 0);  // Move left (A)
        } else if (key == KeyEvent.VK_D) {
            pacman.setDirection(1, 0);  // Move right (D)
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Optional: Stop movement when keys are released if desired
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
