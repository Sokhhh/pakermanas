package Bridge;

import Mediator.Mediator;
import SoundAdapter.SoundPlayer;

public class DoubleSound extends EventSound {
    Mediator mediator;
    public DoubleSound(SoundPlayer player, Mediator mediator) {
        super(player);
        this.mediator = mediator;
    }

    @Override
    public void play() {
        player.play("sounds/double.mp3");
        mediator.notify("Sound double good", this.mediator);
    }
}