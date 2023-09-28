package cr.ac.una.data;

public interface ViewModel {
    double getHigherScore();
    double getLastScore();
    double getCurrentScore();
    boolean isInHud();
    boolean isInGameOver();
    Double getUserTime();
}
