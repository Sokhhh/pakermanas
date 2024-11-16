package Decorator;

import AbstractFactory.IPacMan;
import PacManState.PacManState;
import game.Maze;

import java.awt.*;

public class InvincibilityDecorator extends PacManDecorator {

    private int mouthAngle = 45;  // Starting mouth angle for animation
    private boolean invincibilityActive;
    private long invincibilityEndTime;
    private static final long DEFAULT_INVINCIBILITY_DURATION = 10000; // Default duration: 10 seconds

    public InvincibilityDecorator(IPacMan decoratedPacMan) {
        super(decoratedPacMan);
        this.invincibilityActive = false;
    }

    @Override
    public void move(Maze maze) {
        if (invincibilityActive && System.currentTimeMillis() > invincibilityEndTime) {
            // Deactivate invincibility when the timer expires
            invincibilityActive = false;
        }
        super.move(maze); // Delegate movement to the decorated PacMan
    }

    @Override
    public void render(Graphics g) {
        if (invincibilityActive) {
            // Render Pac-Man with invincibility effects
            g.setColor(Color.CYAN); // Cyan for invincibility mode
            int pacmanSize = 20;

            // Get the Pac-Man's position and direction
            int pacmanX = decoratedPacMan.getX();
            int pacmanY = decoratedPacMan.getY();
            int startAngle = getMouthDirectionAngle();

            // Draw Pac-Man with an animated mouth direction
            g.fillArc(pacmanX * pacmanSize, pacmanY * pacmanSize, pacmanSize, pacmanSize, startAngle, 360 - 2 * mouthAngle);
        } else {
            // Render the base Pac-Man
            decoratedPacMan.render(g);
        }
    }

    @Override
    public void setPacmanState(PacManState state) {
        decoratedPacMan.setPacmanState(state);
    }

    @Override
    public PacManState getPacmanState() {
        return decoratedPacMan.getPacmanState();
    }

    private int getMouthDirectionAngle() {
        // Determine mouth opening angle based on Pac-Man's movement direction
        int dx = decoratedPacMan.getDx();
        int dy = decoratedPacMan.getDy();

        if (dx == 1 && dy == 0) return mouthAngle;          // Moving right
        if (dx == -1 && dy == 0) return 180 + mouthAngle;   // Moving left
        if (dx == 0 && dy == -1) return 90 + mouthAngle;    // Moving up
        if (dx == 0 && dy == 1) return 270 + mouthAngle;    // Moving down

        return mouthAngle; // Default to moving right if no direction is set
    }

    // Activate invincibility mode for a set duration (default 10 seconds)
    public void activateInvincibility() {
        activateInvincibility(DEFAULT_INVINCIBILITY_DURATION);
    }

    public void activateInvincibility(long duration) {
        this.invincibilityActive = true;
        this.invincibilityEndTime = System.currentTimeMillis() + duration;
    }

    public boolean isInvincibilityActive() {
        return invincibilityActive;
    }

    @Override
    public void eatPellet(Maze maze){
        decoratedPacMan.eatPellet(maze);
    }
}
