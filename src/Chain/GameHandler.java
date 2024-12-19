package Chain;

import game.Game;

public abstract class GameHandler {
    protected GameHandler nextHandler;

    public void setNextHandler(GameHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handle(GameContext context);
}

