package SoundAdapter;
import javax.sound.sampled.*;
import java.io.File;

public class WAWAdapter implements SoundPlayer {
    private Clip clip;
    private boolean isLooping = false;

    @Override
    public void play(String soundFile) {
        try {
            File file = new File(soundFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            if (isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    @Override
    public void setLooping(boolean loop) {
        this.isLooping = loop;
    }
}
