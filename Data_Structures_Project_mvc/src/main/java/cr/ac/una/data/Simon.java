package cr.ac.una.data;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class Simon implements ViewModel {
    private final Queue<Color> SEQUENCE = new ArrayDeque<>();
    private static final HashMap<Integer, Color> POSSIBLE_COLORS = new HashMap<>();
    private final List<Color> colorsEnabled = new ArrayList<>();
    private double minTime = 0;
    private double maxTime = 0;
    private double higherScore = 0;
    private double lastScore = 0;
    private double currentScore = 0;
    private int userTime = 0;
    private int roundSequence = 0;
    private final PropertyChangeSupport support;
    private boolean playing = false;
    private boolean inHud = false;
    private boolean inGameOver = false;
    private boolean inNewLevel = false;

    static {
        POSSIBLE_COLORS.put(0, new Color(0, 0, 150));
        POSSIBLE_COLORS.put(1, new Color(0, 150, 0));
        POSSIBLE_COLORS.put(2, new Color(150, 0, 0));
        POSSIBLE_COLORS.put(3, new Color(150, 150, 0));
        POSSIBLE_COLORS.put(4, new Color(0, 150, 150));
        POSSIBLE_COLORS.put(5, new Color(150, 0, 150));
    }

    public Simon() {
        support = new PropertyChangeSupport(this);
        currentScore = 0;
    }

    public void setGameConfigurations(double minTime, double maxTime, double lastScore, double higherScore, int userTime, int roundSequence) {
        setMinTime(minTime);
        setMaxTime(maxTime);
        setLastScore(lastScore);
        setHigherScore(higherScore);
        setUserTime(userTime);
        setRoundSequence(roundSequence);
    }

    public void setUpColors(int quantity) {
        if(!colorsEnabled.isEmpty()) colorsEnabled.clear();
        for (int i = 0; i < quantity; i++) {
            colorsEnabled.add(POSSIBLE_COLORS.get(i));
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void setMinTime(double minTime) {
        this.minTime = minTime;
    }

    public void setMaxTime(double maxTime) {
        this.maxTime = maxTime;
    }

    public void setHigherScore(double higherScore) {
        this.higherScore = higherScore;
    }

    public void setLastScore(double lastScore) {
        this.lastScore = lastScore;
    }

    public void setCurrentScore(double currentScore) {
        if (this.currentScore != currentScore)
            support.firePropertyChange("currentScore", this.currentScore, currentScore);
        this.currentScore = currentScore;
    }

    public void setUserTime(int userTime) {
        if (this.userTime != userTime) support.firePropertyChange("userTime", this.userTime, userTime);
        this.userTime = userTime;
    }

    public void setRoundSequence(int roundSequence) {
        this.roundSequence = roundSequence;
    }

    public void setPlaying(boolean playing) {
        if (this.playing != playing) support.firePropertyChange("playing", this.playing, playing);
        this.playing = playing;
    }

    public void setInHud(boolean inHud) {
        if (this.inHud != inHud) support.firePropertyChange("inHud", this.inHud, inHud);
        this.inHud = inHud;
    }

    public void setInGameOver(boolean inGameOver) {
        if (inGameOver) clearSequence();
        this.inGameOver = inGameOver;
    }

    public void setInNewLevel(boolean inNewLevel) {
        this.inNewLevel = inNewLevel;
    }

    @Override
    public boolean isPlaying() {
        return playing;
    }

    @Override
    public boolean isInHud() {
        return inHud;
    }

    public double getMinTime() {
        return minTime;
    }

    public double getMaxTime() {
        return maxTime;
    }

    public int getRoundSequence() {
        return roundSequence;
    }

    @Override
    public boolean isInGameOver() {
        return inGameOver;
    }

    @Override
    public boolean isInNewLevel() {
        return inNewLevel;
    }

    @Override
    public Integer getUserTime() {
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

    public void updateSequence(Color randomColor) {
        SEQUENCE.add(randomColor);
    }

    public Queue<Color> getSequence() {
        return new ArrayDeque<>(SEQUENCE);
    }

    public void clearSequence(){
        SEQUENCE.clear();
    }
}
