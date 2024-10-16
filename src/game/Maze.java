package game;

import java.awt.*;

public class Maze {
    private char[][] grid;

    public Maze() {
        grid = new char[30][30];
        generateMaze();
    }

    private void generateMaze() {
        // Initialize grid with open spaces
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (i == 0 || j == 0 || i == 29 || j == 29) {
                    grid[i][j] = '#';  // Outer walls
                } else {
                    grid[i][j] = '.';  // Pellets (dots)
                }
            }
        }

        // Add internal walls to create a maze-like structure
        // Horizontal walls
        for (int i = 2; i < 28; i += 4) {
            for (int j = 2; j < 28; j++) {
                if (j % 4 != 0) {
                    grid[i][j] = '#';  // Horizontal walls
                }
            }
        }

        // Vertical walls
        for (int j = 4; j < 26; j += 4) {
            for (int i = 3; i < 27; i++) {
                if (i % 4 != 0) {
                    grid[i][j] = '#';  // Vertical walls
                }
            }
        }

        // Create open pathways
        grid[1][1] = '.';  // Pac-Man start position open
        grid[28][28] = '.';  // Ghost start position open

        // Ensure some open spaces for movement
        grid[15][15] = '.';  // Central area
        grid[5][5] = '.';  // Open area for variety
        grid[10][10] = '.';  // Another open spot
        grid[20][20] = '.';  // Open spot near bottom-right
    }

    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 600);  // Fill the entire panel with black

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (grid[i][j] == '#') {
                    g.setColor(Color.BLUE);  // Wall color
                    g.fillRect(j * 20, i * 20, 20, 20);
                } else if (grid[i][j] == '.') {
                    g.setColor(Color.WHITE);  // Pellet color
                    g.fillOval(j * 20 + 7, i * 20 + 7, 6, 6);
                }
            }
        }
    }

    public boolean isWall(int x, int y) {
        return grid[y][x] == '#';
    }

    public void eatPellet(int x, int y) {
        if (grid[y][x] == '.') {
            grid[y][x] = ' ';  // Pac-Man eats the pellet
        }
    }
}