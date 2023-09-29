package cr.ac.una.logic;

import cr.ac.una.data.Simon;
import cr.ac.una.presentation.SliceButton;
import cr.ac.una.presentation.View;
import cr.ac.una.service.Configuration;
import cr.ac.una.service.Sounds;
import cr.ac.una.service.util.MouseClickedListener;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.UnaryOperator;

public class ViewController extends MouseClickedListener implements ActionListener {
    private final List<SliceButton> slicesList = new ArrayList<>();
    private Queue<Color> sequenceCopy = new ArrayDeque<>();

    //--------------CONFIGURATION KEYS-------------------------
    private final String COLORS = "colors";
    private final String MIN_TIME = "minTime";
    private final String MAX_TIME = "maxTime";
    private final String LAST_SCORE_PROPERTY = "lastScore";
    private final String HIGHER_SCORE_PROPERTY = "higherScore";
    private final String USER_TIME = "userTime";
    private final String MAX_ROUNDS = "maxRounds";
    private final Configuration configuration;

    //-------------PROJECT ELEMENTS-----------------------------
    private final View window;
    private final Simon model;
    private Thread executor;
    private int level = 0;
    private Timer timer;
    private boolean isPlayingSound = false;

    public ViewController(Configuration configuration) {
        this.configuration = configuration;
        model = new Simon();
        model.setGameConfigurations(
                Double.parseDouble(configuration.getProperty(MIN_TIME)),
                Double.parseDouble(configuration.getProperty(MAX_TIME)),
                Double.parseDouble(configuration.getProperty(LAST_SCORE_PROPERTY)),
                Double.parseDouble(configuration.getProperty(HIGHER_SCORE_PROPERTY)),
                Integer.parseInt(configuration.getProperty(USER_TIME)),
                Integer.parseInt(configuration.getProperty(MAX_ROUNDS))
        );
        model.setUpColors(Integer.parseInt((String) configuration.get(COLORS)));
        setInitialState();
        window = new View(this);
    }

    public void init() {
        SwingUtilities.invokeLater(() -> window.init(model));
        setupSlices();
        model.setObserver(window);
    }

    private void setupSlices() {
        double ARC_ANGLE = (double) 360 / model.getColors().size();
        if(!slicesList.isEmpty()) slicesList.clear();
        for (int i = 0; i < model.getColors().size(); i++) {
            Color colorToAdd = model.getColors().get(i);
            slicesList.add(new SliceButton(
                    colorToAdd,
                    getLightColor(colorToAdd),
                    (int) (i * ARC_ANGLE),
                    (int) ARC_ANGLE)
            );
        }
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
        timer.stop();
        model.setInGameOver(true);
        model.setInHud(true);
        model.setPlaying(false);
        model.setUserTime(Integer.parseInt(configuration.getProperty(USER_TIME)));
    }

    private void setNewLevelState() {
        model.setInNewLevel(true);
        model.setInGameOver(false);
        model.setInHud(true);
        model.setPlaying(false);
        model.clearSequence();
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
                Sounds.instance().playSound(Sounds.Tracks.BLIP);
                slice.setLightning(true);
                break;
            }
        }

    }

    private void turnOffSlices() {
        for (SliceButton slice : slicesList) {
            if(slice.isLightning()) {
                slice.setLightning(false);
            }
        }
    }

    public List<SliceButton> getSlices() {
        return slicesList;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (model.isInHud()) {
            executor = new Thread(() -> {
                SwingUtilities.invokeLater(() -> Sounds.instance().playSound(Sounds.Tracks.START_BEEP));
                try {
                    Thread.sleep((long) (model.getMaxTime() * 1000));
                } catch (InterruptedException ignored) {}
                if(model.isInNewLevel()) {
                    if(model.getMaxTime() > model.getMinTime()) model.setMaxTime(model.getMaxTime() - model.getMinTime());
                    if(model.getMaxTime() <= 0) model.setMaxTime(model.getMinTime());
                    int colors = Integer.parseInt((String) configuration.get(COLORS)) + level;
                    if (colors > 6) colors = 6;
                    model.setUpColors(colors);
                    model.setRoundSequence(Integer.parseInt(configuration.getProperty(MAX_ROUNDS)));
                    setupSlices();
                }
                levelUp();
            });
        }  else if (model.isPlaying()) {
            executor = new Thread(() -> {
                int x = e.getX();
                int y = e.getY();
                for (SliceButton s: slicesList) {
                    if(s.contains(x, y)) {
                        if (s.getButtonColor().equals(sequenceCopy.poll())) {
                            model.setUserTime(Integer.parseInt(configuration.getProperty(USER_TIME)));
                            if(sequenceCopy.isEmpty()) {
                                timer.stop();
                                SwingUtilities.invokeLater(() -> Sounds.instance().playSound(Sounds.Tracks.SUCCESS));
                                try {
                                    Thread.sleep((long) (model.getMaxTime() * 1000));
                                } catch (InterruptedException ignored) {}
                                model.setRoundSequence(model.getRoundSequence()-1);
                                levelUp();
                            } else {
                                SwingUtilities.invokeLater(() -> Sounds.instance().playSound(Sounds.Tracks.CORRECT));
                            }
                        } else {
                            SwingUtilities.invokeLater(() -> Sounds.instance().playSound(Sounds.Tracks.FAIL));
                            try {
                                Thread.sleep((long) (model.getMaxTime() * 1000));
                            } catch (InterruptedException ignored) {}
                            setGameOverState();
                        }
                    }
                }
            });
        }
        if(!executor.isAlive()) executor.start();
    }

    private void levelUp() {
        if (model.getRoundSequence() == 0) {
            level++;
            setNewLevelState();
        } else {
            setPresentationState();
            model.updateSequence(SequenceGenerator.getRandomColor(model.getColors()));
            sequenceCopy = model.getSequence();
            sendSequence(model.getSequence());
            setPlayingState();
            SwingUtilities.invokeLater(() -> Sounds.instance().playSound(Sounds.Tracks.TIMER));
            startTimer();
        }
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
                setGameOverState();
            }
        }).start();
    }
}
