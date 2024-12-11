package Command;

import AbstractFactory.IPacMan;

public class MoveUpCommand implements Command {
    private final IPacMan pacman;

    public MoveUpCommand(IPacMan pacman) {
        this.pacman = pacman;
    }

    @Override
    public void execute() {
        pacman.setDirection(0, -1);  // Move up
    }
}
