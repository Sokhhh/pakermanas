import SoundAdapter.JLayerSoundAdapter;
import SoundAdapter.SoundPlayer;
import ui.Menu;

public class PacManGame {
    public static void main(String[] args) {
        SoundPlayer backgroundMusic = new JLayerSoundAdapter();
        //backgroundMusic.setLooping(true);
        backgroundMusic.play("sounds/intro.mp3");

        Menu menu = new Menu(backgroundMusic);
        menu.display();
    }
}
