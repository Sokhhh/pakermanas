package TemplateMethod;

public abstract class GameOverHandler {
    public final void handleGameOver(boolean isWin, int score) {
        stopGameLoop();
        String message = generateGameOverMessage(isWin, score);
        displayGameOverScreen(message);
        saveScore(score);
        offerReplayOption();
    }

    // Steps of the template method
    protected abstract void stopGameLoop();

    protected abstract String generateGameOverMessage(boolean isWin, int score);

    protected abstract void displayGameOverScreen(String message);

    protected abstract void saveScore(int score);

    protected abstract void offerReplayOption();
}
