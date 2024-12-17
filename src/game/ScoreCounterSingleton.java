package game;

public class ScoreCounterSingleton {
    private static ScoreCounterSingleton instance;

    // Score property
    private int score;

    //Use synchronized block for thread safety

    //Explanation
    //First Check: The initial check for instance == null happens without synchronization. This allows for fast access if the instance has already been created.
    //
    //Synchronized Block: The synchronized block ensures that only one thread can execute this block at a time. This is crucial when multiple threads may try to create the instance simultaneously.
    //
    //Second Check: The second check (inside the synchronized block) is necessary because another thread may have created the instance after the first check and before the current thread enters the synchronized block. This ensures that the instance is only created if it is still null.
    public static ScoreCounterSingleton getInstance() {
        if (instance == null) {
            synchronized (ScoreCounterSingleton.class) {
                if (instance == null) {
                    instance = new ScoreCounterSingleton();
                }
            }
        }
        return instance;
    }

    // Private constructor prevents instantiation from other classes
    private ScoreCounterSingleton() {
        this.score = 0;  // Initialize the score to 0
    }

    // Getter method for score
    public int getScore() {
        return score;
    }

    // Method to add to the score
    public void addScore(int points) {
        this.score += points;
    }

    public void setScore(int points) {this.score = points; }
    // Method to reset the score
    public void resetScore() {
        this.score = 0;
    }
}
