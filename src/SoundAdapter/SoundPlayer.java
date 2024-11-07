package SoundAdapter;

public interface SoundPlayer {
    void play(String soundFile);
    void stop();
    void setLooping(boolean loop);
}
