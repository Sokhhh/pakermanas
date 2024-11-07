package Bridge;
import SoundAdapter.SoundPlayer;

public abstract class BackgroundSound {
    protected SoundPlayer player;

    protected BackgroundSound(SoundPlayer player) {
        this.player = player;
    }

    public abstract void play();
    public abstract void stop();
    public abstract void setLooping(boolean loop);
}