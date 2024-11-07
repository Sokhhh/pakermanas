package Command;

import AbstractFactory.IPacMan;

public class MoveLeftCommand implements Command {
    private IPacMan pacman;

    public MoveLeftCommand(IPacMan pacman) {
        this.pacman = pacman;
    }

    @Override
    public void execute() {
        pacman.setDirection(-1, 0);  // Move up
    }
}