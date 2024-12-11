package BuilderPattern;
import game.Maze;

// Maze1Builder.java
public class Maze1Builder implements MazeBuilder {
    private final char[][] grid;

    public Maze1Builder() {
        grid = new char[23][23];
        initializeGrid();
    }

    private void initializeGrid() {
        // Fill with empty spaces
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    @Override
    public void buildWalls() {
        // Define outer walls
        for (int i = 0; i < grid[0].length; i++) {
            grid[0][i] = '#'; // Top wall
            grid[22][i] = '#'; // Bottom wall
            grid[i][0] = '#'; // Left wall
            grid[i][22] = '#'; // Right wall
        }

        // Define inner walls (simplified version of the classic Pac-Man maze layout)
        // Horizontal walls
        for (int i = 2; i < 21; i += 2) {
            grid[2][i] = '#';
            grid[6][i] = '#';
            grid[10][i] = '#';
            grid[14][i] = '#';
            grid[18][i] = '#';
        }

        // Vertical walls
        for (int j = 2; j < 21; j += 2) {
            grid[j][2] = '#';
            grid[j][6] = '#';
            grid[j][10] = '#';
            grid[j][14] = '#';
            grid[j][18] = '#';
        }

        // Add additional internal walls for the classic maze layout
        grid[5][5] = '#';
        grid[5][17] = '#';
        grid[17][5] = '#';
        grid[17][17] = '#';

        // Customize these positions to further refine the classic maze layout as desired
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

        // Leave the Pac-Man start area and ghost house empty
        grid[11][11] = ' '; // Example position for Pac-Man start
        grid[10][11] = ' '; // Example ghost house position
        grid[12][11] = ' ';
    }

    @Override
    public Maze getMaze() {
        return new Maze(grid);
    }
}

