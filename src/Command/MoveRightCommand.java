package Command;

import AbstractFactory.IPacMan;
import entities.PacMan;

public class MoveRightCommand implements Command {
    private IPacMan pacman;

    public MoveRightCommand(IPacMan pacman) {
        this.pacman = pacman;
    }

    @Override
    public void execute() {
        pacman.setDirection(1, 0);  // Move up
    }
}
