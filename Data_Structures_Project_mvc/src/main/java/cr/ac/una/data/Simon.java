package cr.ac.una.data;

import cr.ac.una.presentation.View;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayDeque;
import java.util.Queue;

public class Simon implements ViewModel{
    private final Queue<Color> SEQUENCE = new ArrayDeque<>();
    private final String LAST_SCORE = "lastScore";
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

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
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
