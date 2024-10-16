package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import game.Game;

public class Menu {
    public void display() {
        JFrame frame = new JFrame("Pac-Man Game");
        JButton singlePlayerButton = new JButton("Singleplayer");
        JButton multiPlayerButton = new JButton("Multiplayer");

        singlePlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game game = new Game(false);  // false for singleplayer
                game.start();
            }
        });

        multiPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game game = new Game(true);  // true for multiplayer
                game.start();
            }
        });

        frame.add(singlePlayerButton);
        frame.add(multiPlayerButton);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}