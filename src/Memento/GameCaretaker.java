package Memento;

public class GameCaretaker {
    private GameMemento savedState;

    public void saveState(GameMemento state) {
        this.savedState = state;
    }

    public GameMemento getSavedState() {
        return savedState;
    }
}
