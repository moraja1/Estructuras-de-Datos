package cr.ac.una.logic;

import cr.ac.una.data.Simon;
import cr.ac.una.presentation.View;
import cr.ac.una.service.Configuration;
import cr.ac.una.service.util.MouseClickedListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.UnaryOperator;

public class ViewController extends MouseClickedListener {
    private final String LAST_SCORE_PROPERTY = "lastScore"; //Este atributo es para leer y guardar el estado en el xml
    private final String HIGHER_SCORE_PROPERTY = "higherScore"; //Este atributo es para leer y guardar el estado en el xml
    private final String USER_TIME = "userTime";
    private final Configuration configuration;
    private final View window;
    private final Simon model;
    public ViewController(Configuration configuration) {
        this.configuration = configuration;
        model = new Simon();
        model.setLastGameState(
                Double.parseDouble(configuration.getProperty(LAST_SCORE_PROPERTY)),
                Double.parseDouble(configuration.getProperty(HIGHER_SCORE_PROPERTY)),
                Double.parseDouble(configuration.getProperty(USER_TIME))

                );
        model.setUpColors(Integer.parseInt((String) configuration.get("colors")));
        window = new View(this, model.getColors());
    }

    public void init() {
        window.init(model);
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

    }
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
}
