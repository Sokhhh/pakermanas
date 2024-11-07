package game;

import java.awt.*;

public class Maze {
    private char[][] grid;
//    public Maze() {
//        grid = new char[30][30];
//        generateMaze();
//    }

    public Maze() {
        grid = new char[][]{
                // Maze layout as per the desired design
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                {'#', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '#'},
                {'#', '.', '#', '.', '#', '.', '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', '#', '.', '#', '.', '#', '.', '#'},
                {'#', '.', '.', '.', '.', 'T', '.', '.', '#', '.', '.', '.', '.', '.', '#', '.', '.', 'D', '.', '.', '.', '.', '#'},
                {'#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '#', '#'},
                {'#', '.', '.', '.', '#', '.', '#', '.', '#', '.', '#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '.', '.', '#'},
                {'#', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#'},
                {'#', '.', '.', '.', '#', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '#', '.', '.', '.', '#'},
                {'#', '#', '#', '#', '#', '.', '#', '.', '#', '#', '#', '#', '#', '#', '#', '.', '#', '.', '#', '#', '#', '#', '#'},
                {'#', '.', '.', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '#', '#', '#', '#', '#', '.', '#', '#', '#', '.', '#', '#', '#', '.', '#', '#', '#', '#', '#', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '#', ' ', ' ', ' ', ' ', ' ', '#', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', '#', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '.', '.', '#'},
                {'#', '#', '#', '#', '#', '.', '#', '.', '#', '#', '#', '#', '#', '#', '#', '.', '#', '.', '#', '#', '#', '#', '#'},
                {'#', '.', '.', '.', '#', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '#', '.', '.', '.', '#'},
                {'#', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#'},
                {'#', '.', '.', '.', '#', '.', '#', '.', '#', '.', '#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '.', '.', '#'},
                {'#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '#', '#'},
                {'#', '.', '.', '.', '.', 'I', '.', '.', '#', '.', '.', '.', '.', '.', '#', '.', '.', 'T', '.', '.', '.', '.', '#'},
                {'#', '.', '#', '.', '#', '.', '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', '#', '.', '#', '.', '#', '.', '#'},
                {'#', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '#'},
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
        };
    }
    public Maze(char[][] grid) {
        this.grid = grid;
    }

    public char[][] getGrid() {
        return grid;
    }


    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 800);  // Fill the entire panel with black

        for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 23; j++) {
                if (grid[i][j] == '#') {
                    g.setColor(Color.BLUE);  // Wall color
                    g.drawRect(j * 20, i * 20, 20, 20);
                } else if (grid[i][j] == '.') {
                    g.setColor(Color.WHITE);  // Pellet color
                    g.fillOval(j * 20 + 7, i * 20 + 7, 6, 6);
                } else if (grid[i][j] == 'T') {
                    g.setColor(Color.WHITE);  // Pellet color
                    int offset = (20 - 12) / 2;  // 12x12 pellet, centered in 20x20 grid
                    g.fillOval(j * 20 + offset, i * 20 + offset, 12, 12); // Bigger pellet with 12x12 size
                } else if (grid[i][j] == 'D') {
                    g.setColor(Color.YELLOW);  // Pellet color
                    int offset = (20 - 12) / 2;  // 12x12 pellet, centered in 20x20 grid
                    g.fillOval(j * 20 + offset, i * 20 + offset, 12, 12); // Bigger pellet with 12x12 size
                } else if (grid[i][j] == 'I') {
                    g.setColor(Color.CYAN);  // Pellet color
                    int offset = (20 - 12) / 2;  // 12x12 pellet, centered in 20x20 grid
                    g.fillOval(j * 20 + offset, i * 20 + offset, 12, 12); // Bigger pellet with 12x12 size
                }
            }
        }
    }

    public boolean isWall(int x, int y) {
        return grid[y][x] == '#';
    }

    public boolean eatPellet(int x, int y) {
        if (grid[y][x] == '.') {
            grid[y][x] = ' ';  // Pac-Man eats the pellet

            return true;// Indicate that a pellet was eaten
        }

        return false;// No pellet eaten
    }

    public boolean eatInvincibilityPellet(int x, int y) {
        // Check if there's a power pellet at the given position
        if (grid[y][x] == 'I') {
            grid[y][x] = ' ';  // Pac-Man eats the pellet

            return true;// Indicate that a pellet was eaten
        }

        return false;// No pellet eaten
    }

    public boolean eatDoublePointsPellet(int x, int y) {
        // Check if there's a power pellet at the given position
        if (grid[y][x] == 'D') {
            grid[y][x] = ' ';  // Pac-Man eats the pellet

            return true;// Indicate that a pellet was eaten
        }

        return false;// No pellet eaten
    }

    public boolean eatTeleporterPellet(int x, int y) {
        // Check if there's a power pellet at the given position
        if (grid[y][x] == 'T') {
            grid[y][x] = ' ';  // Pac-Man eats the pellet

            return true;// Indicate that a pellet was eaten
        }

        return false;// No pellet eaten
    }

    public boolean eatPowerPellet(int x, int y) {
        // Check if there's a power pellet at the given position
        if (grid[y][x] == '*') {
            grid[y][x] = ' ';  // Pac-Man eats the pellet

            return true;// Indicate that a pellet was eaten
        }

        return false;// No pellet eaten
    }

    public boolean allPelletsCollected() {
        for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 23; j++) {
                if (grid[i][j] == '.') {  // If any pellets remain
                    return false;
                }
            }
        }
        return true;  // All pellets have been eaten
    }

    public int getWidth() {
        return grid[0].length;  // The number of columns in the maze (length of the first row)
    }

    public int getHeight() {
        return grid.length;  // The number of rows in the maze
    }

    public boolean isValidPosition(int newX, int newY) {
        // Check if the coordinates are within the maze boundaries
        if (newX < 0 || newX >= getWidth() || newY < 0 || newY >= getHeight()) {
            return false;  // Out of bounds
        }

        // Check if the position is a wall
        return grid[newY][newX] != '#';
    }

}