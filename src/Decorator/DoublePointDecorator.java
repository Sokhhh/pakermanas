package Decorator;

import AbstractFactory.IPacMan;
import Mediator.Mediator;
import Mediator.MessageMediator;
import PacManState.PacManState;
import game.Maze;
import game.ScoreCounterSingleton;
import Visitor.Visitor;

import java.awt.*;

public class DoublePointDecorator extends PacManDecorator {

    private int mouthAngle = 45;  // Starting mouth angle for animation
    private boolean doublePointsActive;
    private long doublePointsEndTime;
    private static final long DEFAULT_DOUBLE_POINTS_DURATION = 10000; // Default duration: 10 seconds
    Mediator mediator;
    public DoublePointDecorator(IPacMan decoratedPacMan) {
        super(decoratedPacMan);
        this.doublePointsActive = false;
    }

    @Override
    public void move(Maze maze) {
        if (doublePointsActive && System.currentTimeMillis() > doublePointsEndTime) {
            // Deactivate invincibility when the timer expires
            doublePointsActive = false;
        }
        super.move(maze); // Delegate movement to the decorated PacMan
    }

    @Override
    public void render(Graphics g) {
        if (doublePointsActive) {
            // Render Pac-Man with double points effect
            g.setColor(Color.RED); // Cyan for double points mode
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

    @Override
    public void eatPellet(Maze maze) {
        if (doublePointsActive) {
            // If double points are active, double the points
            if (maze.eatPellet(decoratedPacMan.getX(), decoratedPacMan.getY())) {
                ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance(); // Get the ScoreCounter instance
                scoreCounter.addScore(2);  // Increment score by 2 when a pellet is eaten
            }
        } else {
            decoratedPacMan.eatPellet(maze);  // Default behavior
        }
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

    // Activate double points mode for a set duration (default 10 seconds)
    public void activateDoublePoints(Mediator m) {
        this.mediator = m;
        mediator.notify("Sound Double", decoratedPacMan);
        activateDoublePoints(DEFAULT_DOUBLE_POINTS_DURATION);
    }

    public void activateDoublePoints(long duration) {
        this.doublePointsActive = true;
        this.doublePointsEndTime = System.currentTimeMillis() + duration;
        mediator.notify("Double message", decoratedPacMan);
    }

    public boolean isDoublePointsActive() {
        return doublePointsActive;
    }

    // Method for accepting the visitor
    public void accept(Visitor visitor) {
        visitor.visit(this);  // Let the visitor operate on this DoublePointDecorator
    }
}
