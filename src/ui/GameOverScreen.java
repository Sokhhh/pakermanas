package ui;

import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends JPanel {

    private final String message;

    public GameOverScreen(String message) {
        this.message = message;
        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.BLACK);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString(message, 100, 300);
    }

    public static void display(String message) {
        JFrame frame = new JFrame("Game Over");
        GameOverScreen screen = new GameOverScreen(message);
        frame.add(screen);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
