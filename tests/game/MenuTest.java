package game;

import Bridge.BackgroundSound;
import Bridge.PreGameMusic;
import SoundAdapter.MP3Adapter;
import SoundAdapter.SoundPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.*;

import ui.Menu;

public class MenuTest {

    SoundPlayer backgroundMusic = new MP3Adapter();
    BackgroundSound backgroundSound = new PreGameMusic(backgroundMusic);
    Menu menu = new Menu(backgroundMusic);
    private JFrame frame;

    @BeforeEach
    public void setUp() {
        // Create a JFrame that will be used in the Menu class
        menu.display();
        frame = menu.getFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Test
    public void testSinglePlayerButtonAction() {
        // Create the button and simulate the action
        JButton singlePlayerButton = findButtonByText("Singleplayer");

        // Check that the button is present and set an action
        assertNotNull(singlePlayerButton);

        // Simulate button click
        singlePlayerButton.doClick();

        // Verify that the new game window (JFrame) is created
        assertEquals(1, JFrame.getWindows().length); // We expect the game window to open
    }

    @Test
    public void testMultiplayerHostButtonAction() {
        // Create the button and simulate the action
        JButton multiPlayerButton = findButtonByText("Multiplayer (Host)");

        // Check that the button is present and set an action
        assertNotNull(multiPlayerButton);

        // Simulate button click
        multiPlayerButton.doClick();

        // Verify that the new game window (JFrame) is created
        assertEquals(1, JFrame.getWindows().length); // We expect the game window to open
    }

    @Test
    public void testMultiplayerClientButtonAction() {
        // Create the button and simulate the action
        JButton multiPlayerClientButton = findButtonByText("Multiplayer (Join)");

        // Check that the button is present and set an action
        assertNotNull(multiPlayerClientButton);

        // Simulate button click
        multiPlayerClientButton.doClick();

        // Verify that the new game window (JFrame) is created
        assertEquals(1, JFrame.getWindows().length); // We expect the game window to open
    }

    @Test
    public void testMazeSelectionOnSinglePlayer() {
        // Simulate the user action on the "Singleplayer" button
        JButton singlePlayerButton = findButtonByText("Singleplayer");

        // Simulate button click
        singlePlayerButton.doClick();

        // Simulate selecting the maze option
        String selectedMaze = (String) JOptionPane.showInputDialog(
                frame,
                "Select a Maze:",
                "Maze Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                new String[]{"Default Map", "Maze1", "Maze2"},
                "Default Map"
        );

        // Assert that a maze is selected (not null)
        assertNotNull(selectedMaze);

        // Verify that game frame is created with the correct title
        Window[] windows = JFrame.getWindows();
        assertTrue(windows.length > 0);
        if (windows[0] instanceof JFrame) {
            JFrame gameFrame = (JFrame) windows[0];
            assertTrue(gameFrame.getTitle().contains(selectedMaze));
        }
    }

    // Helper method to find a button by its text
    private JButton findButtonByText(String text) {
        // Check all components within the frame
        for (Component component : frame.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                System.out.println("Found button: " + button.getText());  // Debug output
                if (button.getText().equals(text)) {
                    return button;
                }
            }
        }
        return null;
    }
}
