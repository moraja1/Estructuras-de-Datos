package cr.ac.una.data;

public interface ViewModel {
    double getHigherScore();
    double getLastScore();
    double getCurrentScore();
    boolean isPlaying();
    boolean isInHud();
    boolean isInGameOver();
    Integer getUserTime();
}
