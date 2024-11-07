package Observer;

import Bridge.EventSound;

public class SoundOnCollision implements CollisionObserver {
    private final EventSound sound;

    public SoundOnCollision(EventSound sound) {
        this.sound = sound;
    }

    @Override
    public void onCollision() {
        sound.play();
    }
}