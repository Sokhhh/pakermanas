package game;

import Flyweight.Pellet;
import Flyweight.PelletFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Maze {
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

    // New function to get all pellets in the maze
    public List<Pellet> getPellets() {
        List<Pellet> pellets = new ArrayList<>();

        // Use grid dimensions to ensure you're iterating within bounds
        for (int i = 0; i < grid.length; i++) {  // Iterate over rows
            for (int j = 0; j < grid[i].length; j++) {  // Iterate over columns
                if (grid[i][j] == '.') {
                    // Regular Pellet
                    pellets.add(PelletFactory.getPellet("regular", j, i));
                } else if (grid[i][j] == 'I') {
                    // Invincibility Pellet
                    pellets.add(PelletFactory.getPellet("invincibility", j, i));
                } else if (grid[i][j] == 'D') {
                    // Double Points Pellet
                    pellets.add(PelletFactory.getPellet("doublePoints", j, i));
                } else if (grid[i][j] == 'T') {
                    // Teleporter Pellet
                    pellets.add(PelletFactory.getPellet("teleporter", j, i));
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
                    Pellet regularPellet = PelletFactory.getPellet("regular", j, i);
                    regularPellet.render(g, j, i);  // Pass position to render method
                } else if (grid[i][j] == 'I') {
                    // Create invincibility Pellet at the position (j, i)
                    Pellet invincibilityPellet = PelletFactory.getPellet("invincibility", j, i);
                    invincibilityPellet.render(g, j, i);  // Pass position to render method
                } else if (grid[i][j] == 'D') {
                    // Create doublePoints Pellet at the position (j, i)
                    Pellet doublePointsPellet = PelletFactory.getPellet("doublePoints", j, i);
                    doublePointsPellet.render(g, j, i);  // Pass position to render method
                } else if (grid[i][j] == 'T') {
                    // Create teleporter Pellet at the position (j, i)
                    Pellet teleporterPellet = PelletFactory.getPellet("teleporter", j, i);
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
        if (grid[y][x] == '.') {
            grid[y][x] = ' ';  // Pac-Man eats the pellet

            return true;  // Indicate that a pellet was eaten
        }
        return false;  // No pellet eaten
    }

    // Other methods for eating different types of pellets...
    public boolean eatInvincibilityPellet(int x, int y) {
        if (grid[y][x] == 'I') {
            grid[y][x] = ' ';  // Pac-Man eats the invincibility pellet
            return true;
        }
        return false;
    }

    public boolean eatDoublePointsPellet(int x, int y) {
        if (grid[y][x] == 'D') {
            grid[y][x] = ' ';  // Pac-Man eats the double points pellet
            return true;
        }
        return false;
    }

    public boolean eatTeleporterPellet(int x, int y) {
        if (grid[y][x] == 'T') {
            grid[y][x] = ' ';  // Pac-Man eats the teleporter pellet
            return true;
        }
        return false;
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
