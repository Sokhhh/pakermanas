package game;

import BuilderPattern.Maze1Builder;
import BuilderPattern.Maze2Builder;
import BuilderPattern.MazeBuilder;
import BuilderPattern.MazeDirector;
import Factory.Vaiduoklis;
import Factory.VaiduoklisFactory;
import entities.GhostCPU;
import entities.PacMan;
import entities.Ghost;
import network.GameServer;
import network.GameClient;
import ui.GameOverScreen;

import SoundAdapter.JavaSoundAdapter;
import SoundAdapter.SoundPlayer;
import Observer.SoundOnCollision;
import Observer.CollisionObserver;

//Deivio
import Command.*;
import AbstractFactory.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Game extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private IPacMan pacman;
    private List<Ghost> ghosts = new ArrayList<>();  // List of ghosts (multiple ghosts)
    private Maze maze;

    private Map<Integer, Command> commandMap = new HashMap<>();

    private boolean isMultiplayer;
    private boolean isServer;
    private String serverIP;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    // Score display

    private AbstractEntityFactory entityFactory;

    private List<CollisionObserver> collisionObservers = new ArrayList<>();


    //VaiduoklisFactory vaiduoklisFactory = new VaiduoklisFactory();
    //Vaiduoklis aggressiveGhost = vaiduoklisFactory.createVaiduoklis("Aggressive", 10, 10);
    //Vaiduoklis randomGhost = vaiduoklisFactory.createVaiduoklis("Random", 5, 5);
    //Vaiduoklis cautiousGhost = vaiduoklisFactory.createVaiduoklis("Cautious", 15, 15);
    private List<Vaiduoklis> vaiduoklis = new ArrayList<>();

    public Game(boolean isMultiplayer, boolean isServer, String serverIP) {
        this.isMultiplayer = isMultiplayer;
        this.isServer = isServer;
        //this.pacman = new PacMan(11, 21);  // Start Pac-Man at (1,1)
        //ghosts.add(new Ghost(11,11));  // List of ghosts (multiple ghosts)
        this.entityFactory = isMultiplayer ? new MPEntityFactory() : new SPEntityFactory();
        this.pacman = entityFactory.createPacMan(11,21);
        //this.pacman = new PacMan(11, 21);  // Start Pac-Man at (1,1)

        this.maze = new Maze();  // Generate the maze
        this.serverIP = serverIP;

        // Initialize ScoreCounterSingleton
        ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance();
        scoreCounter.resetScore(); // Reset score at the start of the game

        initializeCommands();
        initializeGhosts();

        // Set up the JPanel layout
        this.setLayout(new BorderLayout());

        //Observer
        SoundPlayer deathSound = new JavaSoundAdapter();
        addCollisionObserver(new SoundOnCollision(deathSound));

        if (isMultiplayer) {
            if (isServer) {
                startServer();
            } else {
                startClient();
            }
        }
        else {
            //ghosts.add(new GhostCPU(11,11));
        }

        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(100, this);
        timer.start();
    }

    public Game(boolean isMultiplayer, boolean isServer) {
        this.isMultiplayer = isMultiplayer;
        this.isServer = isServer;
        this.entityFactory = isMultiplayer ? new MPEntityFactory() : new SPEntityFactory();
        this.pacman = entityFactory.createPacMan(11,21);
        //this.pacman = new PacMan(11, 21);  // Start Pac-Man at (1,1)
        this.maze = new Maze();  // Generate the maze

        initializeCommands();
        initializeGhosts();

        //Observer
        SoundPlayer deathSound = new JavaSoundAdapter();
        addCollisionObserver(new SoundOnCollision(deathSound));

        if (isMultiplayer) {
            //ghosts.add(new Ghost(11,11));
            if (isServer) {
                startServer();
            } else {
                startClient();
            }
        }
        else {
            //this.ghost = new GhostCPU(28,28);
//            ghosts.add(new GhostCPU(11,10));
//            ghosts.add(new GhostCPU(11,11));
//            ghosts.add(new GhostCPU(11,12));
            //vaiduoklis.add(aggressiveGhost);
            //vaiduoklis.add(randomGhost);
            //vaiduoklis.add(cautiousGhost);

        }

        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(100, this);
        timer.start();
    }
    // Constructor for single-player with selected maze type
    public Game(String mazeType) {
        MazeBuilder builder;
        if (mazeType.equals("Maze1")) {
            builder = new Maze1Builder();
        } else {
            builder = new Maze2Builder();
        }

        //Observer
        SoundPlayer deathSound = new JavaSoundAdapter();
        addCollisionObserver(new SoundOnCollision(deathSound));

        MazeDirector director = new MazeDirector(builder);
        this.maze = director.constructMaze();  // Build and retrieve the maze

        this.pacman = new PacMan(11, 21);  // Initialize Pac-Man at starting position
        ghosts.add(new GhostCPU(11,10));
        ghosts.add(new GhostCPU(11,11));
        ghosts.add(new GhostCPU(11,12));

        initializeCommands();

        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(100, this);
        timer.start();
    }

    private void initializeGhosts() {
        // Add ghosts based on factory method
        if (isMultiplayer) {
            vaiduoklis.add(entityFactory.createVaiduoklis("Zaidejas", 11, 10));
        } else {
            vaiduoklis.add(entityFactory.createVaiduoklis("Aggressive", 11, 10));
            vaiduoklis.add(entityFactory.createVaiduoklis("Random", 11, 11));
            vaiduoklis.add(entityFactory.createVaiduoklis("Cautious", 11, 12));
        }
    }

    private void initializeCommands() {
        commandMap.put(KeyEvent.VK_W, new MoveUpCommand(pacman));      // Up
        commandMap.put(KeyEvent.VK_S, new MoveDownCommand(pacman));    // Down
        commandMap.put(KeyEvent.VK_A, new MoveLeftCommand(pacman));    // Left
        commandMap.put(KeyEvent.VK_D, new MoveRightCommand(pacman));   // Right
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

                if (!vaiduoklis.isEmpty()) {
                    vaiduoklis.get(0).setPosition(ghostX, ghostY);  // Update first ghost's position
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
            //Ghost clientGhost = ghosts.get(0);  // In multiplayer, assume first ghost is controlled by the client
            Vaiduoklis clientGhost = vaiduoklis.get(0);
            out.println(clientGhost.getX() + "," + clientGhost.getY());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pacman.move(maze);  // Move Pac-Man based on the current direction

        for (Ghost ghost : ghosts) {
            ghost.move(maze, pacman);
        }
        for (Vaiduoklis vaiduoklis : vaiduoklis) {
            vaiduoklis.move(maze, pacman);
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
        for (Vaiduoklis vaiduoklis : vaiduoklis) {
            vaiduoklis.render(g);
        }
        checkCollision();
        checkWinCondition();

        // Draw the score in the top right corner
        ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance();
        g.setColor(Color.WHITE);  // Set text color
        g.setFont(new Font("Arial", Font.BOLD, 20));  // Set font
        String scoreText = "Score: " + scoreCounter.getScore();
        FontMetrics metrics = g.getFontMetrics();
        int x = getWidth() - metrics.stringWidth(scoreText) - 10; // 10 pixels from the right
        int y = metrics.getHeight(); // Height of the font
        g.drawString(scoreText, x, y);  // Draw score
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (!isMultiplayer || isServer) {
            Command command = commandMap.get(key);  // Get the command based on the key
            if (command != null) {
                command.execute();  // Execute the command
            }
        }

        if (isMultiplayer && !isServer) {
            // Ghost controls (for client)
            if (key == KeyEvent.VK_W && !vaiduoklis.isEmpty()) vaiduoklis.get(0).setDirection(0, -1);   // Up
            if (key == KeyEvent.VK_S && !vaiduoklis.isEmpty()) vaiduoklis.get(0).setDirection(0, 1);    // Down
            if (key == KeyEvent.VK_A && !vaiduoklis.isEmpty()) vaiduoklis.get(0).setDirection(-1, 0);   // Left
            if (key == KeyEvent.VK_D && !vaiduoklis.isEmpty()) vaiduoklis.get(0).setDirection(1, 0);    // Right
        }
    }

    private void checkCollision() {
        for (Ghost ghost : ghosts) {
            if (ghost.collidesWith(pacman)) {
                System.out.println("Game Over! Pac-Man has been caught by the ghost.");
                timer.stop(); // Stop the game loop
                notifyCollisionObservers();
                ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance();
                GameOverScreen.display("Game Over! Pac-Man was caught. Your score: " + scoreCounter.getScore());
                break;
            }
        }
        for (Vaiduoklis vaiduoklis : vaiduoklis) {
            if (vaiduoklis.collidesWith(pacman)) {
                System.out.println("Game Over! Pac-Man has been caught by the ghost.");
                timer.stop(); // Stop the game loop
                notifyCollisionObservers();
                ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance();
                GameOverScreen.display("Game Over! Pac-Man was caught. Your score: " + scoreCounter.getScore());
                break;
            }
        }
    }

    private void checkWinCondition() {
        if (maze.allPelletsCollected()) {
            timer.stop(); // Stop the game loop
            ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance();
            GameOverScreen.display("You Win! All pellets collected. Your score: " + scoreCounter.getScore());
        }
    }
    public void addCollisionObserver(CollisionObserver observer) {
        collisionObservers.add(observer);
    }
    private void notifyCollisionObservers() {
        for (CollisionObserver observer : collisionObservers) {
            observer.onCollision();
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
