package game;

import BuilderPattern.Maze1Builder;
import BuilderPattern.Maze2Builder;
import BuilderPattern.MazeBuilder;
import BuilderPattern.MazeDirector;
import Decorator.DoublePointDecorator;
import Decorator.InvincibilityDecorator;
import Decorator.PacManDecorator;
import Decorator.TeleporterDecorator;
import Factory.Vaiduoklis;
import Flyweight.Pellet;
import Interpreter.Expression;
import Interpreter.MovementCommand;
import Mediator.MessageMediator;
import Mediator.Mediator;
import PacManState.DoublePointsState;
import PacManState.TeleportingState;
import TemplateMethod.GameOverHandler;
import TemplateMethod.MultiplayerGameOverHandler;
import TemplateMethod.SinglePlayerGameOverHandler;


import Visitor.CollisionVisitor;
import Visitor.PowerUpVisitor;
import Visitor.ScoreVisitor;
import ui.GameOverScreen;
//Gusto
import ui.GameMessage;
import SoundAdapter.WAWAdapter;
import SoundAdapter.SoundPlayer;
import Observer.SoundOnCollision;
import Observer.CollisionObserver;
import Bridge.EventSound;
import Bridge.DeathSound;

//Deivio
import Command.*;
import AbstractFactory.*;
import Memento.GameCaretaker;
import Memento.GameMemento;
import Proxy.NetworkProxy;
import Proxy.ServerProxy;
import Proxy.ClientProxy;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private IPacMan pacman;
    private Maze maze;
    private Map<Integer, Command> commandMap = new HashMap<>();

    private final Expression movementCommand; // Expression interface
    private ExecutorService commandListenerThread; // For listening to console commands
    private boolean isMultiplayer;
    private boolean isServer;
    private String serverIP;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private GameMessage gameMessage;
    private Mediator mediator;
    // Score display

    private NetworkProxy networkProxy;
    private final GameCaretaker caretaker = new GameCaretaker();
    private String token;

    private AbstractEntityFactory entityFactory;
    private List<CollisionObserver> collisionObservers = new ArrayList<>();
    private List<Vaiduoklis> vaiduoklis = new ArrayList<>();

    public Game(boolean isMultiplayer, boolean isServer, String serverIP, String token) {
        this.isMultiplayer = isMultiplayer;
        this.isServer = isServer;
        this.token = token;
        this.entityFactory = isMultiplayer ? new MPEntityFactory() : new SPEntityFactory();
        initializePacMan();

        gameMessage = new GameMessage();
        this.mediator = new MessageMediator(this.pacman, gameMessage);

        this.movementCommand = new MovementCommand(); // MovementCommand as the concrete expression


        this.maze = new Maze();  // Generate the maze
        this.serverIP = serverIP;

        // Initialize ScoreCounterSingleton
        ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance();
        scoreCounter.resetScore(); // Reset score at the start of the game

        initializeCommands();
        initializeGhosts();

        // Set up the JPanel layout
        this.setLayout(new BorderLayout());

        //Bridge
        SoundPlayer player = new WAWAdapter(); // your implementation of SoundPlayer
        EventSound deathSound = new DeathSound(player);
        //Observer
        addCollisionObserver(new SoundOnCollision(deathSound));

        if (isMultiplayer) {
            networkProxy = isServer ? new ServerProxy(12345,token) : new ClientProxy(serverIP, 12345, token);
        }
//        if (isMultiplayer) {
//            if (isServer) {
//                startServer();
//            } else {
//                startClient();
//            }
//        }

        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(100, this);
        timer.start();

        // Start the console command listener
        startCommandListener();
        initializeGame();
        saveGameState();
    }

    public Game(boolean isMultiplayer, boolean isServer, String token) {
        this.isMultiplayer = isMultiplayer;
        this.isServer = isServer;
        this.token = token;
        this.entityFactory = isMultiplayer ? new MPEntityFactory() : new SPEntityFactory();
        initializePacMan();
        this.movementCommand = new MovementCommand(); // MovementCommand as the concrete expression
        gameMessage = new GameMessage();
        this.mediator = new MessageMediator(this.pacman, gameMessage);
        this.maze = new Maze();  // Generate the maze

        initializeCommands();
        initializeGhosts();

        //Bridge
        SoundPlayer player = new WAWAdapter(); // your implementation of SoundPlayer
        EventSound deathSound = new DeathSound(player);
        //Observer
        addCollisionObserver(new SoundOnCollision(deathSound));

        if (isMultiplayer) {
            networkProxy = isServer ? new ServerProxy(12345,token) : new ClientProxy(serverIP, 12345, token);
        }

//        if (isMultiplayer) {
//            if (isServer) {
//                startServer();
//            } else {
//                startClient();
//            }
//        }

        setFocusable(true);
        addKeyListener(this);


        timer = new Timer(100, this);
        timer.start();
        // Start the console command listener
        startCommandListener();
        initializeGame();
        saveGameState();
    }
    // Constructor for single-player with selected maze type
    public Game(String mazeType) {
        MazeBuilder builder;
        if (mazeType.equals("Maze1")) {
            builder = new Maze1Builder();
        } else {
            builder = new Maze2Builder();
        }

        //Bridge
        SoundPlayer player = new WAWAdapter(); // your implementation of SoundPlayer
        EventSound deathSound = new DeathSound(player);
        //Observer
        addCollisionObserver(new SoundOnCollision(deathSound));

        MazeDirector director = new MazeDirector(builder);
        this.maze = director.constructMaze();  // Build and retrieve the maze
        this.entityFactory = isMultiplayer ? new MPEntityFactory() : new SPEntityFactory();
        initializePacMan();

        gameMessage = new GameMessage();
        this.mediator = new MessageMediator(this.pacman, gameMessage);

        initializeCommands();
        initializeGhosts();

        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(100, this);
        timer.start();

        this.movementCommand = new MovementCommand(); // MovementCommand as the concrete expression
        startCommandListener();
    }

    private void initializeGame() {
        // Game setup logic (e.g., loading Pac-Man, ghosts, maze)
        if (isMultiplayer) {
            listenToNetwork(); // Start listening for incoming messages
        }
    }

    private void initializePacMan(){
//        this.pacman = entityFactory.createPacMan();
//        this.pacman = new InvincibilityDecorator(new DoublePointDecorator(pacman));
        this.pacman = new InvincibilityDecorator(new DoublePointDecorator(new TeleporterDecorator(entityFactory.createPacMan())));
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
    private void startCommandListener() {
        commandListenerThread = Executors.newSingleThreadExecutor();
        System.out.println("Starting console command listener..."); // Debug message

        commandListenerThread.execute(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Console commands active. Use 'move up', 'move down', 'move left', 'move right', or type 'exit' to quit.");

            while (true) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Stopping console command listener...");
                    break;
                }

                // Interpret and execute the command
                movementCommand.interpret(pacman, maze, input);
                repaint(); // Refresh the game screen after executing a command
            }

            commandListenerThread.shutdown();
        });
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
//        if (out != null && isServer) {
//            out.println(pacman.getX() + "," + pacman.getY());  // Send Pac-Man position to client
//        }
        if (networkProxy != null && isServer) {
            networkProxy.send(pacman.getX() + "," + pacman.getY());
        }
    }

    // Send Ghost's position (from client to host)
    private void sendGhostPosition() {
//        if (out != null && !isServer) {
//            //Ghost clientGhost = ghosts.get(0);  // In multiplayer, assume first ghost is controlled by the client
//            Vaiduoklis clientGhost = vaiduoklis.get(0);
//            out.println(clientGhost.getX() + "," + clientGhost.getY());
//        }
        if (networkProxy != null && !isServer) {
            Vaiduoklis clientGhost = vaiduoklis.get(0);
            networkProxy.send(clientGhost.getX() + "," + clientGhost.getY());
        }
    }

    private void listenToNetwork() {
        if (networkProxy == null) return;

        new Thread(() -> {
            while (true) {
                String message = networkProxy.receive();
                if (message == null) {
                    networkProxy.close();
                    System.exit(0);
                    break;
                }

                String[] position = message.split(",");
                int x = Integer.parseInt(position[0]);
                int y = Integer.parseInt(position[1]);

                if (isServer) {
                    // Update ghost position
                    if (!vaiduoklis.isEmpty()) vaiduoklis.get(0).setPosition(x, y);
                } else {
                    // Update Pac-Man position
                    pacman.setPosition(x, y);
                }

                repaint();
            }
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pacman.move(maze);  // Move Pac-Man based on the current direction

        // Use PowerUpVisitor to check for power-ups
        PowerUpVisitor powerUpVisitor = new PowerUpVisitor(pacman, maze);
        ScoreVisitor scoreVisitor = new ScoreVisitor(pacman);

        for (Pellet pellet : maze.getPellets()) {
            pellet.accept(scoreVisitor);
            pellet.accept(powerUpVisitor);
        }

        checkWinCondition();

        // Use CollisionVisitor to check collisions between PacMan and ghosts/pellets
        CollisionVisitor collisionVisitor = new CollisionVisitor(pacman);
        for (Vaiduoklis vaiduoklis : vaiduoklis) {
            vaiduoklis.move(maze, pacman);
            vaiduoklis.accept(collisionVisitor);  // Check if PacMan collides with this ghost
        }

        for (Pellet pellet : maze.getPellets()) {
            pellet.accept(collisionVisitor);  // Check if PacMan eats this pellet
        }

        if (!((InvincibilityDecorator) pacman).isInvincibilityActive()) {
            checkCollision();  // Ignore ghost collisions when in super mode.
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

        for (Vaiduoklis vaiduoklis : vaiduoklis) {
            vaiduoklis.render(g);
        }

        // Draw the score in the top right corner
        ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance();
        g.setColor(Color.WHITE);  // Set text color
        g.setFont(new Font("Arial", Font.BOLD, 20));  // Set font
        String scoreText = "Score: " + scoreCounter.getScore();
        FontMetrics metrics = g.getFontMetrics();
        int x = getWidth() - metrics.stringWidth(scoreText) - 10; // 10 pixels from the right
        int y = metrics.getHeight(); // Height of the font
        g.drawString(scoreText, x, y);  // Draw score
        gameMessage.render(g, x - 5, y + 15);
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
        // Collision checks with ghosts
        for (Vaiduoklis vaiduoklis : vaiduoklis) {
            if (vaiduoklis.collidesWith(pacman)) {
                System.out.println("Game Over! Pac-Man has been caught by the ghost.");
                timer.stop();  // Stop the game loop
                notifyCollisionObservers();

                // Get the score
                ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance();
                int score = scoreCounter.getScore();

                GameOverHandler handler = isMultiplayer ? new MultiplayerGameOverHandler() : new SinglePlayerGameOverHandler();
                handler.handleGameOver(false, score, this);

                //GameOverScreen.display("Game Over! Pac-Man was caught. Your score: " + scoreCounter.getScore());
                if(isMultiplayer)
                {
                    networkProxy.close();
                }

                break;
            }
        }
    }


    private void checkWinCondition() {
        if (maze.allPelletsCollected()) {
            timer.stop(); // Stop the game loop
            ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance();
            int score = scoreCounter.getScore();

            GameOverHandler handler = isMultiplayer ? new MultiplayerGameOverHandler() : new SinglePlayerGameOverHandler();
            handler.handleGameOver(true, score, this);
            if(isMultiplayer)
            {
                networkProxy.close();
            }
            //GameOverScreen.display("You Win! All pellets collected. Your score: " + scoreCounter.getScore());
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


    public void saveGameState() {
        int currentScore = ScoreCounterSingleton.getInstance().getScore();
        caretaker.saveState(new GameMemento(maze, pacman, vaiduoklis, currentScore));
    }

    public void restoreGameState() {
        GameMemento memento = caretaker.getSavedState();
        if (memento != null) {
            this.pacman = memento.getPacman();
            this.vaiduoklis = memento.getGhosts();
            ScoreCounterSingleton.getInstance().setScore(memento.getScore());
            this.maze = memento.getMaze();

            initializeCommands();
            addKeyListener(this);
            timer.start(); // Resume the game loop
            repaint();     // Refresh the screen
        } else {
            System.out.println("No saved state to restore!");
        }
    }
}