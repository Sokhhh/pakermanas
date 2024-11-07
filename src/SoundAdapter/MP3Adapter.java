package SoundAdapter;
import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class MP3Adapter implements SoundPlayer {
    private Player player;
    private boolean isLooping;

    @Override
    public void play(String soundFile) {
        try {
            FileInputStream fis = new FileInputStream(soundFile);
            player = new Player(fis);
            new Thread(() -> {
                try {
                    player.play();
                    if (isLooping) play(soundFile); // Recursive call if looping is set
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if (player != null) player.close();
    }

    @Override
    public void setLooping(boolean loop) {
        this.isLooping = loop;
    }
}
