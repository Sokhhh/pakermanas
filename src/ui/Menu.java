package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import game.Game;
import SoundAdapter.SoundPlayer;

public class Menu {
    private final SoundPlayer backgroundMusic;

    public Menu(SoundPlayer backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }
    public void display() {

        JFrame frame = new JFrame("Pac-Man Game");
        JButton singlePlayerButton = new JButton("Singleplayer");
        JButton multiPlayerButton = new JButton("Multiplayer (Host)");
        JButton multiPlayerClientButton = new JButton("Multiplayer (Join)");

        // Action for single-player button
        singlePlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backgroundMusic.stop();
                // Add "Default Map" as an option for maze selection
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

                // Check if a maze was selected (not null)
                if (selectedMaze != null) {
                    Game game;
                    if (selectedMaze.equals("Default Map")) {
                        game = new Game(false, false);// Use default constructor for the default map
                    } else {
                        game = new Game(selectedMaze);  // Pass the selected maze type to the Game class
                    }

                    JFrame gameFrame = new JFrame("Pac-Man Game - " + selectedMaze);
                    gameFrame.add(game);
                    gameFrame.setSize(600, 600);
                    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    gameFrame.setVisible(true);
                    gameFrame.setResizable(true);
                    frame.dispose(); // Close the menu frame
                }
            }
        });

        // Action for multiplayer host button
        multiPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backgroundMusic.stop();
                Game game = new Game(true, true);  // true for multiplayer, true for server
                JFrame gameFrame = new JFrame("Pac-Man Game");
                gameFrame.add(game);
                gameFrame.setSize(600, 600);
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.setVisible(true);
                gameFrame.setResizable(true);
                frame.dispose(); // Close the menu frame
            }
        });

        // Action for multiplayer client button
        multiPlayerClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backgroundMusic.stop();
                // Show an input dialog to get the server IP from the player
                String serverIP = JOptionPane.showInputDialog(
                        frame,
                        "Enter the IP address of the host:",
                        "Join Multiplayer Game",
                        JOptionPane.PLAIN_MESSAGE
                );

                // If the player clicked "OK" and provided an IP address
                if (serverIP != null && !serverIP.trim().isEmpty()) {
                    Game game = new Game(true, false, serverIP);  // true for multiplayer, false for client, with server IP
                    JFrame gameFrame = new JFrame("Pac-Man Game");
                    gameFrame.add(game);
                    gameFrame.setSize(600, 600);
                    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    gameFrame.setVisible(true);
                    gameFrame.setResizable(false);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid IP address. Please try again.");
                }
            }
        });

        frame.add(singlePlayerButton);
        frame.add(multiPlayerButton);
        frame.add(multiPlayerClientButton);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
