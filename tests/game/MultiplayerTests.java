package game;

import game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class MultiplayerTests {
    @Test
    public void testGameEndsOnCollision() {
        Game game = new Game(false, true); // Create a single-player game

        // Set Pac-Man and Ghost to collide
        game.getPacman().setPosition(5, 5);
        game.getVaiduoklis().get(0).setPosition(5, 5);

        game.checkCollision();  // Invoke collision check

        // Verify game stops on collision
        assertFalse(game.getTimer().isRunning(), "Game should stop when Pac-Man is caught by a ghost.");
    }
    @Test
    public void testPlayerScoreSynchronization() {
        ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance();
        scoreCounter.resetScore();  // Reset score

        // Simulate Pac-Man collecting points
        scoreCounter.incrementScore(10);
        assertEquals(10, scoreCounter.getScore(), "Score should be 10 after increment.");

        // Reset and verify synchronization in multiplayer instance
        scoreCounter.resetScore();
        assertEquals(0, scoreCounter.getScore(), "Score should reset to 0.");
    }



}
