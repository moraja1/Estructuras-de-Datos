package cr.ac.una.data;

import cr.ac.una.presentation.View;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class Simon implements ViewModel{
    private final Queue<Color> SEQUENCE = new ArrayDeque<>();
    private static final HashMap<Integer, Color> POSSIBLE_COLORS = new HashMap<>();
    private final List<Color> colorsEnabled = new ArrayList<>();
    private double higherScore = 0;
    private double lastScore = 0;
    private double currentScore = 0;
    private double userTime = 0;
    private final PropertyChangeSupport support;
    private boolean playing;
    private boolean inHud;
    private boolean inGameOver;

    static {
        POSSIBLE_COLORS.put(0, new Color(0, 0, 150));
        POSSIBLE_COLORS.put(1, new Color(0, 150, 0));
        POSSIBLE_COLORS.put(2, new Color(150, 0, 0));
        POSSIBLE_COLORS.put(3, new Color(0, 150, 150));
        POSSIBLE_COLORS.put(4, new Color(150, 150, 0));
        POSSIBLE_COLORS.put(5, new Color(150, 0, 150));
    }

    public Simon() {
        support = new PropertyChangeSupport(this);
        playing = false;
        inHud = true;
        inGameOver = false;
        setCurrentScore(0);
    }
    public void setLastGameState(double lastScore, double higherScore, double userTime) {
        setLastScore(lastScore);
        setHigherScore(higherScore);
        setUserTime(userTime);
    }

    public void setUpColors(int quantity) {
        for(int i = 0; i < quantity; i++) {
            colorsEnabled.add(POSSIBLE_COLORS.get(i));
        }
    }
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
    public void setHigherScore(double higherScore) {
        this.higherScore = higherScore;
    }
    public void setLastScore(double lastScore) {
        this.lastScore = lastScore;
    }
    public void setCurrentScore(double currentScore) {
        this.currentScore = currentScore;
    }

    public void setUserTime(double userTime) {
        this.userTime = userTime;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
    public void setInHud(boolean inHud) {
        this.inHud = inHud;
    }
    public void setInGameOver(boolean inGameOver) {
        this.inGameOver = inGameOver;
    }
    public boolean isPlaying() {
        return playing;
    }
    @Override
    public boolean isInHud() {
        return inHud;
    }
    @Override
    public boolean isInGameOver() {
        return inGameOver;
    }
    @Override
    public Double getUserTime() {
        return userTime;
    }
    public List<Color> getColors() {
        return colorsEnabled;
    }
    public void setObserver(PropertyChangeListener observer) {
        addPropertyChangeListener(observer);
    }
    @Override
    public double getHigherScore() {
        return higherScore;
    }

    @Override
    public double getLastScore() {
        return lastScore;
    }

    @Override
    public double getCurrentScore() {
        return currentScore;
    }
}
