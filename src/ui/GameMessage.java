package ui;

import Mediator.Mediator;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import Mediator.MessageMediator;

public class GameMessage {
    private static class Message {
        String text;
        long displayEndTime;
        Mediator mediator;
        public Message(String text, long durationMillis) {
            this.text = text;
            this.displayEndTime = System.currentTimeMillis() + durationMillis;
            this.mediator = new MessageMediator();
        }
    }

    private final Queue<Message> messageQueue = new LinkedList<>();
    private final Font font = new Font("Yellow", Font.BOLD, 15);
    private final Color color = Color.WHITE;

    public void addMessage(String text, long durationMillis) {
        messageQueue.add(new Message(text, durationMillis));
    }

    public void render(Graphics g, int x, int y) {
        if (!messageQueue.isEmpty()) {
            Message currentMessage = messageQueue.peek();
            if (System.currentTimeMillis() > currentMessage.displayEndTime) {
                messageQueue.poll(); // Remove the message when its time is up
            } else {
                // Draw the current message
                g.setColor(color);
                g.setFont(font);
                g.drawString(currentMessage.text, x, y);
            }
        }
    }
}
