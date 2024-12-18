package game;

import Flyweight.Pellet;
import Flyweight.PelletFactory;

import java.awt.*;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class Maze implements Serializable {
    private char[][] grid;

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


    public List<Pellet> getPellets() {
        List<Pellet> pellets = new ArrayList<>();

        // Use grid dimensions to ensure you're iterating within bounds
        for (int i = 0; i < grid.length; i++) {  // Iterate over rows
            for (int j = 0; j < grid[i].length; j++) {  // Iterate over columns
                if (grid[i][j] == '.') {
                    // Regular Pellet
                    pellets.add(PelletFactory.getPellet("regular", j, i));  // The PelletFactory will handle color, size, and position
                } else if (grid[i][j] == 'I') {
                    // Invincibility Pellet
                    pellets.add(PelletFactory.getPellet("invincibility", j, i));  // The PelletFactory will handle color, size, and position
                } else if (grid[i][j] == 'D') {
                    // Double Points Pellet
                    pellets.add(PelletFactory.getPellet("doublePoints", j, i));  // The PelletFactory will handle color, size, and position
                } else if (grid[i][j] == 'T') {
                    // Teleporter Pellet
                    pellets.add(PelletFactory.getPellet("teleporter", j, i));  // The PelletFactory will handle color, size, and position
                }
            }
        }

        return pellets;
    }


    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 800);  // Fill the entire panel with black

        for (int i = 0; i < grid.length; i++) {  // Loop through rows
            for (int j = 0; j < grid[i].length; j++) {  // Loop through columns
                if (grid[i][j] == '#') {
                    g.setColor(Color.BLUE);  // Wall color
                    g.drawRect(j * 20, i * 20, 20, 20);
                } else if (grid[i][j] == '.') {
                    // Create regular Pellet at the position (j, i)
                    Pellet regularPellet = PelletFactory.getPellet("regular", j, i);  // The PelletFactory handles the creation
                    regularPellet.render(g, j, i);  // Pass position to render method
                } else if (grid[i][j] == 'I') {
                    // Create invincibility Pellet at the position (j, i)
                    Pellet invincibilityPellet = PelletFactory.getPellet("invincibility", j, i);  // The PelletFactory handles the creation
                    invincibilityPellet.render(g, j, i);  // Pass position to render method
                } else if (grid[i][j] == 'D') {
                    // Create doublePoints Pellet at the position (j, i)
                    Pellet doublePointsPellet = PelletFactory.getPellet("doublePoints", j, i);  // The PelletFactory handles the creation
                    doublePointsPellet.render(g, j, i);  // Pass position to render method
                } else if (grid[i][j] == 'T') {
                    // Create teleporter Pellet at the position (j, i)
                    Pellet teleporterPellet = PelletFactory.getPellet("teleporter", j, i);  // The PelletFactory handles the creation
                    teleporterPellet.render(g, j, i);  // Pass position to render method
                }
            }
        }
    }


    // Other existing methods like isWall, eatPellet, etc.
    public boolean isWall(int x, int y) {
        return grid[y][x] == '#';
    }

    public boolean eatPellet(int x, int y) {
        char pelletType = grid[y][x];  // Get the character representing the pellet at the position

        // Check if the pellet exists and handle accordingly
        switch (pelletType) {
            case '.':
                // Regular pellet
                grid[y][x] = ' ';  // Remove the pellet from the grid
                return true;

            case 'I':
                // Invincibility Pellet
                grid[y][x] = ' ';  // Remove the invincibility pellet
                // Implement logic for invincibility (e.g., activate power-up)
                return true;

            case 'D':
                // Double Points Pellet
                grid[y][x] = ' ';  // Remove the double points pellet
                // Implement logic for double points (e.g., activate power-up)
                return true;

            case 'T':
                // Teleporter Pellet
                grid[y][x] = ' ';  // Remove the teleporter pellet
                // Implement logic for teleportation (e.g., teleport Pac-Man)
                return true;

            default:
                // If it's not a valid pellet, do nothing
                return false;
        }
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
