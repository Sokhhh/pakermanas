package TemplateMethod;

import game.Game;

public abstract class GameOverHandler {
    public final void handleGameOver(boolean isWin, int score, Game game) {
        stopGameLoop();
        String message = generateGameOverMessage(isWin, score);
        displayGameOverScreen(message, game);
        saveScore(score);
        offerReplayOption();
    }

    // Steps of the template method
    protected abstract void stopGameLoop();

    protected abstract String generateGameOverMessage(boolean isWin, int score);

    protected abstract void displayGameOverScreen(String message, Game game);

    protected abstract void saveScore(int score);

    protected abstract void offerReplayOption();
}
