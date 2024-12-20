package Chain;

public interface Handler {
    public void setNextHandler(GameHandler nextHandler);
    public abstract void handle(GameContext context);
}
