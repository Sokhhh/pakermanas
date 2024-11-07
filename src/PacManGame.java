import Bridge.PreGameMusic;
import SoundAdapter.MP3Adapter;
import SoundAdapter.SoundPlayer;
import ui.Menu;
import Bridge.BackgroundSound;

public class PacManGame {
    public static void main(String[] args) {
        SoundPlayer backgroundMusic = new MP3Adapter();
        BackgroundSound backgroundSound = new PreGameMusic(backgroundMusic);
        backgroundSound.play();

        Menu menu = new Menu(backgroundMusic);
        menu.display();
    }
}
