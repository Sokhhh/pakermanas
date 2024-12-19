package Chain;

import game.Game;
import game.ScoreCounterSingleton;

public class ScoreUpdateHandler extends GameHandler {
    @Override
    public void handle(GameContext context) {
        if (!context.isGameOver()) {
            ScoreCounterSingleton scoreCounter = ScoreCounterSingleton.getInstance();
            if(scoreCounter.getScore() < 0) {nextHandler = null;}
            context.setScore(scoreCounter.getScore());

        }

        if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }
}

