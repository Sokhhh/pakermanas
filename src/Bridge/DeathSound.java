package Bridge;
import SoundAdapter.SoundPlayer;

public class DeathSound extends EventSound {
    public DeathSound(SoundPlayer player) {
        super(player);
    }

    @Override
    public void play() {
        player.play("sounds/death.wav");
    }
}
