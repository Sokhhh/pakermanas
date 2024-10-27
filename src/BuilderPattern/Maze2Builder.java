package BuilderPattern;
import game.Maze;
// Maze2Builder.java
// Maze2Builder.java
// Maze2Builder.java
// Maze2Builder.java
// Maze2Builder.java
public class Maze2Builder implements MazeBuilder {
    private char[][] grid;

    public Maze2Builder() {
        grid = new char[25][25]; // Define a 25x25 grid
        initializeGrid();
    }

    private void initializeGrid() {
        // Set the outer boundary walls correctly within bounds
        for (int i = 1; i < 22; i++) {
            grid[0][i] = '#';       // Top wall
            grid[24][i] = '#';      // Bottom wall (last row within bounds)
            grid[i][0] = '#';       // Left wall
            grid[i][24] = '#';      // Right wall (last column within bounds)
        }

        // Fill inner area with empty spaces initially
        for (int i = 1; i < 24; i++) {
            for (int j = 1; j < 24; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    @Override
    public void buildWalls() {
        // Define inner walls based on an Atari-style layout, leaving the outer wall intact

        // Example internal wall structure, matching a simplified Atari style
        addHorizontalWall(2, 2, 5);
        addHorizontalWall(2, 8, 5);
        addHorizontalWall(2, 14, 5);
        addHorizontalWall(2, 20, 3);

        addVerticalWall(4, 2, 3);
        addVerticalWall(4, 8, 3);
        addVerticalWall(4, 14, 3);
        addVerticalWall(4, 20, 3);

        // Center ghost house walls
        addHorizontalWall(12, 10, 5);
        addHorizontalWall(12, 16, 5);
        addVerticalWall(10, 12, 5);
        addVerticalWall(10, 18, 5);

        // Bottom-left section walls
        addHorizontalWall(16, 2, 5);
        addHorizontalWall(16, 8, 5);
        addHorizontalWall(16, 14, 5);
        addHorizontalWall(16, 20, 3);

        addVerticalWall(18, 2, 3);
        addVerticalWall(18, 8, 3);
        addVerticalWall(18, 14, 3);
        addVerticalWall(18, 20, 3);
    }

    @Override
    public void placePellets() {
        // Place pellets in all open spaces
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                if (grid[i][j] == ' ') {
                    grid[i][j] = '.'; // Place pellet
                }
            }
        }

        // Clear specific areas for Pac-Man start and ghost area
        grid[1][1] = ' '; // Pac-Man start position
        grid[12][12] = ' '; // Example ghost area center
        grid[12][13] = ' ';
    }

    @Override
    public Maze getMaze() {
        return new Maze(grid);
    }

    // Helper methods to add walls
    private void addHorizontalWall(int row, int colStart, int length) {
        for (int i = 0; i < length; i++) {
            grid[row][colStart + i] = '#';
        }
    }

    private void addVerticalWall(int col, int rowStart, int length) {
        for (int i = 0; i < length; i++) {
            grid[rowStart + i][col] = '#';
        }
    }
}
