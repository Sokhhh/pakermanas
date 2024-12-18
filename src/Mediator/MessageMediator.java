package Mediator;

import AbstractFactory.IPacMan;
import Bridge.DoubleSound;
import Bridge.EventSound;
import Decorator.DoublePointDecorator;
import SoundAdapter.MP3Adapter;
import ui.GameMessage;

import java.util.Objects;

public class MessageMediator implements Mediator {
    private IPacMan pacman;
    private GameMessage gameMessage;
    private EventSound eventSound;
    public MessageMediator(){}
    public MessageMediator(IPacMan pacman){
        gameMessage = new GameMessage();
    }
    public MessageMediator(IPacMan pacman, GameMessage gameMessage) {
        this.pacman = pacman;
        this.gameMessage = gameMessage;
    }

    @Override
    public void notify(String event, Object data) {
        if (Objects.equals(event, "Double")) {
            pacman = (IPacMan) data;
            ((DoublePointDecorator) pacman).activateDoublePoints(this);
            System.out.println("Mediator: Double Points Activated");
        } else if (Objects.equals(event, "Invincibility")) {
            // Handle other events, e.g., Invincibility
            gameMessage.addMessage("Invincibility Mode!", 3000);
            System.out.println("Mediator: Invincibility Mode Activated");
        }
        else if(Objects.equals(event, "Double message")) {
            gameMessage.addMessage("Double Points Activated!", 10000);
        }
        else if(Objects.equals(event, "Sound Double")) {
            eventSound = new DoubleSound(new MP3Adapter(), this);
            eventSound.play();
        }
    }
}
