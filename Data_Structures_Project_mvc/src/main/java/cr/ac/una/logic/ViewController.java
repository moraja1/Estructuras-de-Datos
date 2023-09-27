package cr.ac.una.logic;

import cr.ac.una.data.Simon;
import cr.ac.una.presentation.View;
import cr.ac.una.service.Configuration;

public class ViewController {
    private final Configuration configuration;
    private final View window;
    private final Simon model;
    public ViewController(Configuration configuration) {
        this.configuration = configuration;
        window = new View(this);
        model = new Simon(window);
    }

    public void init() {
        window.init();
        model.getLastGameState(configuration);
    }
}
