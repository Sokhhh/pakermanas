package Chain;

public class SoundHandler extends GameHandler{

    @Override
    public void handle(GameContext context) {
        if(context.isGameOver())
        {
            context.getMediator().notify("Death Sound", context.getPacMan());
        }
    }
}
