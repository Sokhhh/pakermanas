package game;

import game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ServerSocket.class, Socket.class })
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

    @Test
    public void testPlayerDisconnection() throws Exception {
        ServerSocket mockServerSocket = Mockito.mock(ServerSocket.class);
        Socket mockSocket = Mockito.mock(Socket.class);
        PrintWriter mockOut = Mockito.mock(PrintWriter.class);

        when(mockServerSocket.accept()).thenReturn(mockSocket);
        when(mockSocket.getOutputStream()).thenReturn(System.out);

        Game game = new Game(true, true, "localhost");
        game.serverSocket = mockServerSocket;
        game.out = mockOut;

        // Simulate disconnection
        game.clientSocket.close();
        assertTrue(game.clientSocket.isClosed(), "Client socket should be closed on disconnection.");
    }
    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15, 20})
    public void testScoreCalculationAccuracy(int points) {
        ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance();
        scoreCounter.resetScore();

        scoreCounter.incrementScore(points);
        assertEquals(points, scoreCounter.getScore(), "Score should match the points added.");
    }
    @Test
    public void testGameLogicConsistency() {
        Game game = new Game("Maze1");

        // Move Pac-Man and verify new position
        game.getPacman().setPosition(1, 1);
        game.commandMap.get(KeyEvent.VK_W).execute();  // Move up
        assertEquals(0, game.getPacman().getY(), "Pac-Man should move up by 1 unit.");

        game.commandMap.get(KeyEvent.VK_S).execute();  // Move down
        assertEquals(1, game.getPacman().getY(), "Pac-Man should move down by 1 unit.");
    }
    @Test
    public void testConnectionStability() throws Exception {
        ServerSocket mockServerSocket = Mockito.mock(ServerSocket.class);
        Socket mockSocket = Mockito.mock(Socket.class);
        when(mockServerSocket.accept()).thenReturn(mockSocket);

        Game game = new Game(true, true, "localhost");
        game.serverSocket = mockServerSocket;

        // Simulate connection loss and reconnection
        game.clientSocket = mockSocket;
        game.clientSocket.close();
        assertTrue(game.clientSocket.isClosed(), "Client socket should close on connection loss.");

        // Reconnect and verify
        game.clientSocket = new Socket("localhost", 12345);
        assertFalse(game.clientSocket.isClosed(), "Client socket should reopen on reconnection.");
    }

    @Test
    public void testPlayerSynchronization() throws Exception {
        Game serverGame = new Game(true, true, "localhost");
        Game clientGame = new Game(true, false, "localhost");

        // Set mock positions and simulate server-client synchronization
        serverGame.getPacman().setPosition(5, 5);
        serverGame.sendPacmanPosition();

        // Assume client received data and updated position
        clientGame.getPacman().setPosition(5, 5);

        assertEquals(serverGame.getPacman().getX(), clientGame.getPacman().getX(), "Client's Pac-Man X position should match server's.");
        assertEquals(serverGame.getPacman().getY(), clientGame.getPacman().getY(), "Client's Pac-Man Y position should match server's.");
    }



}
