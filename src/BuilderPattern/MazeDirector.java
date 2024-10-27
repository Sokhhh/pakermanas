package BuilderPattern;
import game.Maze;

public class MazeDirector {
    private MazeBuilder builder;

    public MazeDirector(MazeBuilder builder) {
        this.builder = builder;
    }

    public Maze constructMaze() {
        builder.buildWalls();
        builder.placePellets();
        return builder.getMaze();
    }
}