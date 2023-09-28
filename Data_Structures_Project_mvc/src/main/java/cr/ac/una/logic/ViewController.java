package cr.ac.una.logic;

import com.sun.istack.NotNull;
import cr.ac.una.data.Simon;
import cr.ac.una.presentation.SliceButton;
import cr.ac.una.presentation.View;
import cr.ac.una.service.Configuration;
import cr.ac.una.service.util.MouseClickedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.UnaryOperator;

public class ViewController extends MouseClickedListener implements ActionListener {
    private final List<SliceButton> slicesList = new ArrayList<>();

    //--------------CONFIGURATION KEYS-------------------------
    private final String COLORS = "colors";
    private final String MIN_TIME = "minTime";
    private final String MAX_TIME = "maxTime";
    private final String LAST_SCORE_PROPERTY = "lastScore";
    private final String HIGHER_SCORE_PROPERTY = "higherScore";
    private final String USER_TIME = "userTime";
    private final String ROUND_SEQUENCE = "maxRounds";
    private final Configuration configuration;

    //-------------PROJECT ELEMENTS-----------------------------
    private final View window;
    private final Simon model;
    private Thread executor;
    private Thread auxiliar;
    private Timer timer;

    public ViewController(Configuration configuration) {
        this.configuration = configuration;
        model = new Simon();
        model.setGameConfigurations(
                Double.parseDouble(configuration.getProperty(MIN_TIME)),
                Double.parseDouble(configuration.getProperty(MAX_TIME)),
                Double.parseDouble(configuration.getProperty(LAST_SCORE_PROPERTY)),
                Double.parseDouble(configuration.getProperty(HIGHER_SCORE_PROPERTY)),
                Integer.parseInt(configuration.getProperty(USER_TIME)),
                Integer.parseInt(configuration.getProperty(ROUND_SEQUENCE))
        );
        model.setUpColors(Integer.parseInt((String) configuration.get(COLORS)));
        setInitialState();
        window = new View(this);
    }

    public void init() {
        SwingUtilities.invokeLater(() -> window.init(model));
        double ARC_ANGLE = (double) 360 / model.getColors().size();
        for (int i = 0; i < model.getColors().size(); i++) {
            Color colorToAdd = model.getColors().get(i);
            slicesList.add(new SliceButton(
                    colorToAdd,
                    getLightColor(colorToAdd),
                    (int) (i * ARC_ANGLE),
                    (int) ARC_ANGLE)
            );
        }
        model.setObserver(window);
        for(SliceButton slice : slicesList) {
            slice.setObserver(window);
        }
    }

    public Color getLightColor(Color colorToAdd) {
        UnaryOperator<Integer> colorLighter = v -> v > 0 ? v + 105 : 0;
        return new Color(
                colorLighter.apply(colorToAdd.getRed()),
                colorLighter.apply(colorToAdd.getGreen()),
                colorLighter.apply(colorToAdd.getBlue())
        );
    }

    private Color getNaturalColor(Color colorToNaturalize) {
        UnaryOperator<Integer> colorNaturalize = v -> v == 255 ? 150 : v;
        return new Color(
                colorNaturalize.apply(colorToNaturalize.getRed()),
                colorNaturalize.apply(colorToNaturalize.getGreen()),
                colorNaturalize.apply(colorToNaturalize.getBlue())
        );
    }

    private void startTimer() {
        timer = new Timer(1000, this);
        timer.start();
    }

    private void setInitialState() {
        model.setInHud(true);
        model.setInGameOver(false);
        model.setPlaying(false);
    }

    private void setPlayingState() {
        model.setInGameOver(false);
        model.setInHud(false);
        model.setPlaying(true);
    }

    private void setPresentationState() {
        model.setInGameOver(false);
        model.setInHud(false);
        model.setPlaying(false);
    }

    private void setGameOverState() {
        model.setInGameOver(true);
        model.setInHud(true);
        model.setPlaying(false);
        model.setUserTime(Integer.parseInt(configuration.getProperty(USER_TIME)));
    }

    private void sendSequence(Queue<Color> sequence) {

        for (Color color : sequence) {
            turnOnSlice(color);
            try {
                Thread.sleep((long) (model.getMaxTime() * 1000));
            } catch (InterruptedException ignored) {}
            turnOffSlices();
        }

        try {
            Thread.sleep((long) (model.getMaxTime() * 1000));
        } catch (InterruptedException ignored) {}
    }

    private void turnOnSlice(Color color) {
        try {
            Thread.sleep((long) (model.getMaxTime() * 1000));
        } catch (InterruptedException ignored) {}

        for (SliceButton slice : slicesList) {
            if (slice.getButtonColor().equals(color)) {
                slice.setLightning(true);
                break;
            }
        }

    }

    private void turnOffSlices() {
        for (SliceButton slice : slicesList) {
            if(slice.isLightning()) slice.setLightning(false);
        }
    }

    public List<SliceButton> getSlices() {
        return slicesList;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (model.isInHud()) {
            executor = new Thread(() -> {
                setPresentationState();
                model.updateSequence(SequenceGenerator.getRandomColor(model.getColors()));
                sendSequence(model.getSequence());
                setPlayingState();
                startTimer();
            });
        }  else if (model.isPlaying()) {
            executor = new Thread(() -> {
                int x = e.getX();
                int y = e.getY();

                for (SliceButton s: slicesList) {

                }
            });
        }
        if(!executor.isAlive()) executor.start();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(model.isPlaying()) {
            new Thread(() -> {
                int x = e.getX();
                int y = e.getY();

                for (SliceButton s: slicesList) {
                    if(s.contains(x, y)) s.setLightning(true);
                }
            }).start();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if(model.isPlaying()) {
            new Thread(() -> {
                int x = e.getX();
                int y = e.getY();

                for (SliceButton s: slicesList) {
                    if(s.contains(x, y)) s.setLightning(false);
                }
            }).start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(() -> {
            if(model.getUserTime() > 0) {
                model.setUserTime(model.getUserTime()-1);
            }
            else {
                timer.stop();
                setGameOverState();
            }
        }).start();
    }
}
