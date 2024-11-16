package TemplateMethod;
import ui.GameOverScreen;

public final class MultiplayerGameOverHandler extends GameOverHandler {

    @Override
    protected void stopGameLoop() {
        System.out.println("Stopping the game loop for Multiplayer mode.");
    }

    @Override
    protected String generateGameOverMessage(boolean isWin, int score) {
        return isWin
                ? "Multiplayer Victory! All pellets collected. Team score: " + score
                : "Game Over! Pac-Man was caught. Team score: " + score;
    }

    @Override
    protected void displayGameOverScreen(String message) {
        System.out.println("Displaying Multiplayer Game Over screen...");
        GameOverScreen.display(message);
    }

    @Override
    protected void saveScore(int score) {
        System.out.println("Saving score for Multiplayer mode: " + score);
    }

    @Override
    protected void offerReplayOption() {
        System.out.println("Offering replay option for Multiplayer mode.");
    }
}

