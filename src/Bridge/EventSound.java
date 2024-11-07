package Bridge;
import SoundAdapter.SoundPlayer;

public abstract class EventSound {
    protected SoundPlayer player;

    protected EventSound(SoundPlayer player) {
        this.player = player;
    }

    public abstract void play();
    public void stop() {
        player.stop();
    }
}
