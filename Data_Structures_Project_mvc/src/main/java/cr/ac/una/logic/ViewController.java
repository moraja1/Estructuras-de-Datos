package cr.ac.una.logic;

import cr.ac.una.data.Simon;
import cr.ac.una.presentation.SliceButton;
import cr.ac.una.presentation.View;
import cr.ac.una.service.Configuration;
import cr.ac.una.service.util.MouseClickedListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.UnaryOperator;

public class ViewController extends MouseClickedListener {
    private final List<SliceButton> slicesList = new ArrayList<>();
    private final String COLORS = "colors";
    private final String MIN_TIME = "minTime";
    private final String MAX_TIME = "maxTime";
    private final String LAST_SCORE_PROPERTY = "lastScore"; //Este atributo es para leer y guardar el estado en el xml
    private final String HIGHER_SCORE_PROPERTY = "higherScore"; //Este atributo es para leer y guardar el estado en el xml
    private final String USER_TIME = "userTime";
    private final String ROUND_SEQUENCE = "maxRounds";
    private final Configuration configuration;
    private final View window;
    private final Simon model;
    private Thread executor;

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
        window = new View(this);
    }

    public void init() {
        window.init(model);
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
    }

    public Color getLightColor(Color colorToAdd) {
        UnaryOperator<Integer> colorLighter = v -> v > 0 ? v + 105 : 0;
        return new Color(
                colorLighter.apply(colorToAdd.getRed()),
                colorLighter.apply(colorToAdd.getGreen()),
                colorLighter.apply(colorToAdd.getBlue())
        );
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        executor = new Thread(new Runnable() {
            @Override
            public void run() {
                if (model.isInHud()) {
                    model.updateSequence(SequenceGenerator.getRandomColor(model.getColors()));
                    model.startPresentation(); //Osea que los click no se procesan
                    window.repaint();
                    sendSequence(model.getSequence());
                    model.startPlaying(); //Osea que se reciben los clicks
                    window.repaint();
                }
            }
        });
        executor.start();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        executor = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Color color = new Robot().getPixelColor(e.getXOnScreen(), e.getYOnScreen());
                    turnOnSlice(color);
                    window.repaint();
                } catch (AWTException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        if (model.isPlaying()) executor.start();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        executor = new Thread(new Runnable() {
            @Override
            public void run() {
                turnOffSlices();
                window.repaint();
            }
        });
        if (model.isPlaying()) executor.start();
    }

    private void sendSequence(Queue<Color> sequence) {
        try {
            Thread.sleep((long) (model.getMaxTime() * 1000));
        } catch (InterruptedException ignored) {
        }
        for (Color color : sequence) {
            turnOnSlice(color);
        }
        window.repaint();
        try {
            Thread.sleep((long) (model.getMaxTime() * 1000));
        } catch (InterruptedException ignored) {
        }
        turnOffSlices();
        window.repaint();
        try {
            Thread.sleep((long) (model.getMaxTime() * 1000));
        } catch (InterruptedException ignored) {
        }
    }

    private void turnOnSlice(Color color) {
        for (SliceButton slice : slicesList) {
            if (slice.getButtonColor().equals(color)) {
                slice.setLightning(true);
                break;
            }
        }
    }

    private void turnOffSlices() {
        for (SliceButton slice : slicesList) {
            slice.setLightning(false);
        }
    }

    public List<SliceButton> getSlices() {
        return slicesList;
    }
}
