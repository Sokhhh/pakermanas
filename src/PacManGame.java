import AbstractFactory.IPacMan;
import AbstractFactory.SPPacMan;
import Bridge.PreGameMusic;
import Interpreter.CommandInterpreter;
import SoundAdapter.MP3Adapter;
import SoundAdapter.SoundPlayer;
import game.Maze;
import ui.Menu;
import Bridge.BackgroundSound;

import java.util.Scanner;

public class PacManGame {
    public static void main(String[] args) {
        SoundPlayer backgroundMusic = new MP3Adapter();
        BackgroundSound backgroundSound = new PreGameMusic(backgroundMusic);
        backgroundSound.play();

        Menu menu = new Menu(backgroundMusic);
        menu.display();


//        IPacMan pacMan = new SPPacMan(0,0); // Replace with your Pac-Man implementation
//        Maze maze = new Maze(); // Replace with your Maze implementation
//        CommandInterpreter interpreter = new CommandInterpreter();
//// Console-based commands
//        System.out.println("Welcome to Pac-Man! Enter commands (e.g., move up, pause, resume, powerup):");
//
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            System.out.print("> ");
//            String input = scanner.nextLine();
//
//            if (input.equalsIgnoreCase("exit")) {
//                System.out.println("Exiting the game. Goodbye!");
//                backgroundSound.stop(); // Stop background music
//                break;
//            }
//
//            // Interpret and execute the command
//            interpreter.interpretCommand(input, pacMan, maze);
//        }
//
//        scanner.close();
    }
}
