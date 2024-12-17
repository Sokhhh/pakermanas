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
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString(message, 100, 300);
    }

    public static void display(String message, Game game) {
        JFrame frame = new JFrame("Game Over");
        GameOverScreen screen = new GameOverScreen(message);
        frame.add(screen);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        JButton retryButton = new JButton("Retry");
        JButton exitButton = new JButton("Exit");

        retryButton.addActionListener((ActionEvent e) -> {
            frame.dispose();
            game.restoreGameState(); // Restore the previous game state
        });

        exitButton.addActionListener((ActionEvent e) -> System.exit(0));

        panel.add(label);
        panel.add(retryButton);
        panel.add(exitButton);

        frame.add(panel);


        frame.setVisible(true);
    }
}
