package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import game.Game;

public class GameOverScreen extends JPanel {

    private String message;

    public GameOverScreen(String message) {
        this.message = message;
        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 26));
        g.drawString(message, 100, 300);
    }

    public static void display(String message, Game game) {
        JFrame frame = new JFrame("Game Over");
        GameOverScreen screen = new GameOverScreen(message);
        frame.add(screen);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel with BoxLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK); // Match the background for consistency

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setForeground(Color.WHITE); // Set text color to contrast with black background
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create buttons and set their preferred sizes
        JButton retryButton = new JButton("Retry");
        retryButton.setPreferredSize(new Dimension(100, 40)); // Smaller button size
        retryButton.setMaximumSize(new Dimension(100, 40));  // Enforce maximum size
        retryButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(100, 40)); // Smaller button size
        exitButton.setMaximumSize(new Dimension(100, 40));  // Enforce maximum size
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        retryButton.addActionListener((ActionEvent e) -> {
            frame.dispose();
            game.restoreGameState(); // Restore the previous game state
        });

        exitButton.addActionListener((ActionEvent e) -> System.exit(0));

        // Add components to the panel with spacing
        panel.add(Box.createRigidArea(new Dimension(0, 500))); // Spacer for top margin
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer between label and buttons
        panel.add(retryButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer between buttons
        panel.add(exitButton);

        frame.add(panel);
        frame.setResizable(false); // Prevent resizing
        frame.setVisible(true);
    }

}
