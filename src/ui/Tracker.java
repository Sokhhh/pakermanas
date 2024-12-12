package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Tracker {
    private long lastTime = System.nanoTime();  // Time of last update (in nanoseconds)
    private int frames = 0;                     // Number of frames rendered
    private int fps = 0;                        // Calculated FPS
    private long msPerFrame = 0;                // Time in milliseconds per frame
    private long lastFpsUpdateTime = 0;         // Time when FPS was last updated
    private static final long NANOSECONDS_IN_SECOND = 1_000_000_000L; // 1 second in nanoseconds

    // Call this method to update FPS and MS values
    public void update() {
        frames++;
        long currentTime = System.nanoTime();
        long elapsedTime = currentTime - lastTime;

        // Calculate FPS and MS every second (1,000,000,000 nanoseconds)
        if (elapsedTime >= NANOSECONDS_IN_SECOND) {
            fps = frames;
            msPerFrame = elapsedTime / frames / 1_000_000; // Convert to milliseconds
            frames = 0; // Reset frame counter
            lastTime = currentTime; // Reset the time
        }
    }

    // Call this method to get the current FPS
    public int getFps() {
        return fps;
    }

    // Call this method to get the current MS per frame (how long each frame takes)
    public long getMsPerFrame() {
        return msPerFrame;
    }

    // Display FPS and MS on screen (called in paintComponent)
    public void render(Graphics g) {
        g.setColor(Color.WHITE);  // Set text color
        g.setFont(new Font("Arial", Font.BOLD, 14));  // Set font
        String fpsText = "FPS: " + fps;
        String msText = "MS per frame: " + msPerFrame;

        // Draw FPS and MS text at the top-left corner
        g.drawString(fpsText, 10, 20);
        g.drawString(msText, 10, 40);
    }
}
