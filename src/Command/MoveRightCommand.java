package Command;

import AbstractFactory.IPacMan;

public class MoveRightCommand implements Command {
    private final IPacMan pacman;

    public MoveRightCommand(IPacMan pacman) {
        this.pacman = pacman;
    }

    @Override
    public void execute() {
        pacman.setDirection(1, 0);  // Move up
    }
}
