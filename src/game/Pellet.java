package game;

public class Pellet {
    private int x, y;
    private boolean eaten;

    public Pellet(int x, int y) {
        this.x = x;
        this.y = y;
        this.eaten = false;
    }

    public void eat() {
        this.eaten = true;
    }

    public boolean isEaten() {
        return eaten;
    }
}