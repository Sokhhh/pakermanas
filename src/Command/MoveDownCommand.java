package Command;

import AbstractFactory.IPacMan;
import entities.PacMan;

public class MoveDownCommand implements Command {
    private IPacMan pacman;

    public MoveDownCommand(IPacMan pacman) {
        this.pacman = pacman;
    }

    @Override
    public void execute() {
        pacman.setDirection(0, 1);  // Move down
    }
}
