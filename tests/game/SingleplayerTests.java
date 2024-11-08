package game;

import AbstractFactory.IPacMan;
import Factory.Vaiduoklis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SingleplayerTests {

    @Test
    public void testPacmanMoveLeft() {
        Game game = new Game(false, false);  // Non-multiplayer mode
        IPacMan pacman = game.getPacman();
        int initialX = pacman.getX();

        game.keyPressed(new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A'));

        pacman.move(game.getMaze());

        assertEquals(initialX - 1, pacman.getX(), "Pacman should move one step left.");
    }

    @Test
    public void testPacmanMoveRight() {
        Game game = new Game(false, false);  // Non-multiplayer mode
        IPacMan pacman = game.getPacman();
        int initialX = pacman.getX();

        game.keyPressed(new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D'));

        pacman.move(game.getMaze());

        assertEquals(initialX + 1, pacman.getX(), "Pacman should move one step right.");
    }

    @Test
    public void testPacmanMoveUp() {
        Game game = new Game(false, false);
        IPacMan pacman = game.getPacman();
        int initialY = pacman.getY();

        game.keyPressed(new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W'));

        pacman.move(game.getMaze());

        assertEquals(initialY - 1, pacman.getY(), "Pacman should move one step up.");
    }

    @Test
    public void testPacmanMoveDown() {
        Game game = new Game(false, false);
        IPacMan pacman = game.getPacman();
        int initialY = pacman.getY();

        game.keyPressed(new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S'));

        pacman.move(game.getMaze());

        assertEquals(initialY + 1, pacman.getY(), "Pacman should move one step down.");
    }

    @Test
    public void testGhostsFunctioning() {
        Game game = new Game(false, false);
        List<Vaiduoklis> ghosts = game.getVaiduoklis();
        IPacMan pacman = game.getPacman();

        for (Vaiduoklis ghost : ghosts) {
            int initialX = ghost.getX();
            int initialY = ghost.getY();

            ghost.move(game.getMaze(), pacman);

            // Ensure the ghost position has been updated (or logic followed).
            assertTrue(ghost.getX() != initialX || ghost.getY() != initialY, "Ghost should have moved from its initial position.");
        }
    }

    @Test
    public void testScoreUpdate() {
        Game game = new Game(false, false);
        ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance();
        scoreCounter.resetScore();

        int initialScore = scoreCounter.getScore();
        IPacMan pacman = game.getPacman();

        //pacman.eatPellet(game.getMaze());

        game.keyPressed(new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W'));

        pacman.move(game.getMaze());

        pacman.eatPellet(game.getMaze());

        int bruh = scoreCounter.getScore();

        assertTrue(scoreCounter.getScore() > initialScore, "Score should increase after Pacman eats a pellet.");
    }

    @Test
    public void testPacmanCollisionWithGhosts() {
        Game game = new Game(false, false);
        game.getTimer().stop();  // Stop game timer for controlled testing

        IPacMan pacman = game.getPacman();
        Vaiduoklis ghost = game.getVaiduoklis().get(0);

        // Position Pacman and ghost at the same spot
        pacman.setPosition(5, 5);
        ghost.setPosition(5, 5);

        game.checkCollision();

        // Verify if the game stopped after the collision
        assertFalse(game.getTimer().isRunning(), "Game should stop when Pacman collides with a ghost.");
    }

    @Test
    public void testPacmanCollisionWithWalls() {
        Game game = new Game(false, false);
        IPacMan pacman = game.getPacman();

        //Move not into wall
        game.keyPressed(new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W'));

        pacman.move(game.getMaze());

        //Move into wall
        game.keyPressed(new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A'));

        pacman.move(game.getMaze());

        // Ensure Pacman didn’t move
        assertEquals(17, pacman.getX(), "Pacman should not move into a wall.");
        assertEquals(18, pacman.getY(), "Pacman should not move into a wall.");
    }
}
