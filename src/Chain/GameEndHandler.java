package Chain;

import TemplateMethod.GameOverHandler;
import TemplateMethod.MultiplayerGameOverHandler;
import TemplateMethod.SinglePlayerGameOverHandler;
import game.Game;

public class GameEndHandler extends GameHandler {

    @Override
    public void handle(GameContext context) {
        if(context.isGameOver()) {
            GameOverHandler handler = context.isMultiplayer() ? new MultiplayerGameOverHandler() : new SinglePlayerGameOverHandler();
            handler.handleGameOver(false, context.getScore(), context.getGame());
            context.getGame().endGameLogic();
        }
        if(nextHandler != null)
        {nextHandler.handle(context);}
    }
}

