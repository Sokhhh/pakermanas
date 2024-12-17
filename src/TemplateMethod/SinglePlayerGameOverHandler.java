package TemplateMethod;
import game.Game;
import ui.GameOverScreen;

public final class SinglePlayerGameOverHandler extends GameOverHandler {

    @Override
    protected void stopGameLoop() {
        System.out.println("Stopping the game loop for SinglePlayer mode.");
    }

    @Override
    protected String generateGameOverMessage(boolean isWin, int score) {
        return isWin
                ? "You Win! All pellets collected. Your score: " + score
                : "Game Over! Pac-Man was caught. Your score: " + score;
    }

    @Override
    protected void displayGameOverScreen(String message, Game game) {
        System.out.println("Displaying SinglePlayer Game Over screen...");
        GameOverScreen.display(message, game);
    }

    @Override
    protected void saveScore(int score) {
        System.out.println("Saving score for SinglePlayer mode: " + score);
    }

    @Override
    protected void offerReplayOption() {
        System.out.println("Offering replay option for SinglePlayer mode.");
    }
}

