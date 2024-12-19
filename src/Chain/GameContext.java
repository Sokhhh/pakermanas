package Chain;

import AbstractFactory.IPacMan;
import Factory.Vaiduoklis;
import game.Game;

import java.util.ArrayList;
import java.util.List;
import Mediator.Mediator;

public class GameContext {
    private int score;
    private boolean isCollision;
    private boolean isGameOver;
    private boolean isMultiplayer;
    private Game game;
    private List<Vaiduoklis> vaiduoklis;
    private IPacMan pacMan;
    private Mediator mediator;

    // Getters and setters
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public boolean isCollision() { return isCollision; }
    public void setCollision(boolean collision) { isCollision = collision; }
    public boolean isGameOver() { return isGameOver; }
    public void setGameOver(boolean gameOver) { isGameOver = gameOver; }
    public boolean isMultiplayer(){return isMultiplayer;};
    public void setMultiplayer(boolean multiplayer){this.isMultiplayer = multiplayer;}
    public void setGame(Game game){this.game = game;}
    public Game getGame(){return this.game;}
    public void setVaiduoklis(List<Vaiduoklis> v){vaiduoklis = v;}
    public List<Vaiduoklis> getVaiduoklis(){return vaiduoklis;}
    public void setPacMan(IPacMan pacMan){this.pacMan = pacMan;}
    public IPacMan getPacMan(){return this.pacMan;}
    public void setMediator(Mediator m){mediator = m;}
    public Mediator getMediator(){return mediator;}


}

