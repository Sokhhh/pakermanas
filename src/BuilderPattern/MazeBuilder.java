package BuilderPattern;
import game.Maze;

public interface MazeBuilder {
    void buildWalls();
    void placePellets();
    Maze getMaze();
}
