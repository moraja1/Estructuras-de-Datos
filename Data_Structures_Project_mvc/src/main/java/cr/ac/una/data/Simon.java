package cr.ac.una.data;

import cr.ac.una.presentation.View;
import cr.ac.una.service.Configuration;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayDeque;
import java.util.Queue;

public class Simon implements ViewModel{
    private final Queue<Color> SEQUENCE = new ArrayDeque<>();
    private final String LAST_SCORE_PROPERTY = "lastScore"; //Este atributo es para leer y guardar el estado en el xml
    private final String HIGHER_SCORE_PROPERTY = "higherScore"; //Este atributo es para leer y guardar el estado en el xml
    private double higherScore = 0;
    private double lastScore = 0;
    private double currentScore = 0;
    private final PropertyChangeSupport support;
    private boolean playing;
    private boolean inHud;
    private boolean inGameOver;

    public Simon(View observer) {
        support = new PropertyChangeSupport(this);
        playing = false;
        inHud = true;
        inGameOver = false;
        addPropertyChangeListener(observer);
    }

    public void getLastGameState(Configuration conf) {
        setLastScore(Double.parseDouble(conf.getProperty(LAST_SCORE_PROPERTY)));
        setHigherScore(Double.parseDouble(conf.getProperty(HIGHER_SCORE_PROPERTY)));
        setCurrentScore(0);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void setHigherScore(double higherScore) {
        support.firePropertyChange("higherScore", this.higherScore, higherScore);
        this.higherScore = higherScore;
    }

    public void setLastScore(double lastScore) {
        support.firePropertyChange("lastScore", this.lastScore, lastScore);
        this.lastScore = lastScore;
    }

    public void setCurrentScore(double currentScore) {
        support.firePropertyChange("currentScore", this.currentScore, currentScore);
        this.currentScore = currentScore;
    }

    public void setPlaying(boolean playing) {
        support.firePropertyChange("playing", this.playing, playing);
        this.playing = playing;
    }

    public void setInHud(boolean inHud) {
        support.firePropertyChange("inHud", this.inHud, inHud);
        this.inHud = inHud;
    }

    public void setInGameOver(boolean inGameOver) {
        support.firePropertyChange("inGameOver", this.inGameOver, inGameOver);
        this.inGameOver = inGameOver;
    }

    @Override
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
}
