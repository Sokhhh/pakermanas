package Observer;
import SoundAdapter.SoundPlayer;

public class SoundOnCollision implements CollisionObserver {
    private final SoundPlayer soundPlayer;

    public SoundOnCollision(SoundPlayer soundPlayer) {
        this.soundPlayer = soundPlayer;
    }

    @Override
    public void onCollision() {
        soundPlayer.play("sounds/death.wav");
    }
}
