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
                {'#', '.', '.', '.', '.', '*', '.', '.', '#', '.', '.', '.', '.', '.', '#', '.', '.', '*', '.', '.', '.', '.', '#'},
                {'#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '#', '#'},
                {'#', '.', '.', '.', '#', '.', '#', '.', '#', '.', '#', ' ', '#', '.', '#', '.', '#', '.', '#', '.', '.', '.', '#'},
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
                {'#', '.', '.', '.', '#', '.', '#', '.', '#', '.', '#', ' ', '#', '.', '#', '.', '#', '.', '#', '.', '.', '.', '#'},
                {'#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '#', '#'},
                {'#', '.', '.', '.', '.', '*', '.', '.', '#', '.', '.', '.', '.', '.', '#', '.', '.', '*', '.', '.', '.', '.', '#'},
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
                } else if (grid[i][j] == '*') {
                    g.setColor(Color.WHITE);  // Pellet color
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
}