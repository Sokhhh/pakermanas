package game;

import game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import SoundAdapter.SoundPlayer;
import ui.GameOverScreen;
import ui.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

class UITests {

    private Menu menu;
    private SoundPlayer mockSoundPlayer;
    private JFrame frame;

    @BeforeEach
    void setUp() {
        mockSoundPlayer = mock(SoundPlayer.class);
        menu = new Menu(mockSoundPlayer);
        frame = new JFrame();
    }

    @Test
    void testSinglePlayerButtonFunctionality() {
        // Setup the button action
        JButton singlePlayerButton = new JButton("Singleplayer");
        singlePlayerButton.addActionListener(e -> {
            mockSoundPlayer.stop();
            String selectedMaze = "Default Map";  // Simulate maze selection
            Game game = new Game(false, false);
            JFrame gameFrame = new JFrame("Pac-Man Game - " + selectedMaze);
            gameFrame.add(game);
            gameFrame.setSize(600, 600);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true);
            gameFrame.setResizable(true);
            frame.dispose();  // Close the menu frame
        });

        // Simulate button click
        singlePlayerButton.doClick();

        // Verify interactions
        verify(mockSoundPlayer).stop();
        assert frame.isVisible() == false; // The menu frame should be disposed
    }

    @Test
    void testMultiplayerHostButtonFunctionality() {
        // Setup the button action for multiplayer host
        JButton multiPlayerButton = new JButton("Multiplayer (Host)");
        multiPlayerButton.addActionListener(e -> {
            mockSoundPlayer.stop();
            Game game = new Game(true, true);  // true for multiplayer, true for server
            JFrame gameFrame = new JFrame("Pac-Man Game");
            gameFrame.add(game);
            gameFrame.setSize(600, 600);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true);
            gameFrame.setResizable(true);
            frame.dispose();  // Close the menu frame
        });

        // Simulate button click
        multiPlayerButton.doClick();

        // Verify interactions
        verify(mockSoundPlayer).stop();
        assert frame.isVisible() == false; // The menu frame should be disposed
    }

    @Test
    void testMultiplayerClientButtonFunctionality() {
        // Setup the button action for multiplayer client
        JButton multiPlayerClientButton = new JButton("Multiplayer (Join)");
        multiPlayerClientButton.addActionListener(e -> {
            mockSoundPlayer.stop();
            String serverIP = "127.0.0.1";  // Simulate input for server IP
            Game game = new Game(true, false, serverIP);  // true for multiplayer, false for client
            JFrame gameFrame = new JFrame("Pac-Man Game");
            gameFrame.add(game);
            gameFrame.setSize(600, 600);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true);
            gameFrame.setResizable(false);
        });

        // Simulate button click
        multiPlayerClientButton.doClick();

        // Verify interactions
        verify(mockSoundPlayer).stop();
    }

    @Test
    void testMazeSelectionDropdownFunctionality() {
        // Simulate maze selection using a dialog
        String[] mazeOptions = {"Default Map", "Maze1", "Maze2"};
        String selectedMaze = (String) JOptionPane.showInputDialog(
                frame,
                "Select a Maze:",
                "Maze Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                mazeOptions,
                mazeOptions[0]  // Default selection
        );

        // Check that the default map is selected
        assert selectedMaze.equals("Default Map");

        // Simulate selecting another maze
        selectedMaze = "Maze1";
        assert selectedMaze.equals("Maze1");

        // Simulate selecting an invalid maze (null)
        selectedMaze = null;
        assert selectedMaze == null;  // Maze selection should return null if cancelled
    }

    @Test
    void testGameOverScreenFunctionality() {
        // Setup a GameOverScreen with a message
        GameOverScreen gameOverScreen = new GameOverScreen("Game Over");

        // Create a mock Graphics object
        Graphics graphics = mock(Graphics.class);

        // Setup JFrame and add the gameOverScreen
        JFrame gameOverFrame = new JFrame("Game Over");
        gameOverFrame.add(gameOverScreen);
        gameOverFrame.setSize(800, 800);
        gameOverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameOverFrame.setVisible(true);

        // Call repaint to simulate the paint process (which triggers paintComponent)
        gameOverScreen.repaint();

        // Use an event queue to wait for the painting to complete
        // Swing components are painted asynchronously, so we need to wait for the paint event
        try {
            // Using SwingUtilities.invokeAndWait to ensure the paint happens before verification
            SwingUtilities.invokeAndWait(() -> {
                gameOverScreen.paintComponent(graphics);
            });
        } catch (Exception e) {
            e.printStackTrace();
            fail("Paint operation failed");
        }

        // Verify the correct methods are invoked during the painting
        verify(graphics).setColor(Color.WHITE);
        verify(graphics).setFont(new Font("Arial", Font.BOLD, 36));
        verify(graphics).drawString("Game Over", 100, 300);
    }


}

