package Chain;

import Factory.Vaiduoklis;

public class CollisionHandler extends GameHandler {
    @Override
    public void handle(GameContext context) {
        for (Vaiduoklis vaiduoklis : context.getVaiduoklis())
        {
            if(vaiduoklis.collidesWith(context.getPacMan()))
            {
                context.getGame().endGameLogic();
                context.setGameOver(true);
                break;
            }
            else
            {
                context.setGameOver(false);
            }

        }
        if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }
}
