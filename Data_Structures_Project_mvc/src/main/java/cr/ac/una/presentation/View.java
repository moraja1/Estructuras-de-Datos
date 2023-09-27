package cr.ac.una.presentation;

import cr.ac.una.logic.ViewController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View extends JFrame implements PropertyChangeListener {
    private final ViewController controller;

    public View(ViewController controller) {
        this.controller = controller;
        setTitle("Simon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(300, 300));

        int PREF_WIDTH = 600;
        int PREF_HEIGHT = 400;
        Dimension defSize = new Dimension(PREF_WIDTH, PREF_HEIGHT);
        setSize(defSize);

        //add(new SimonModel(WindowController.getSlices()));
    }

    public void init() {
        setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.printf("Value: %s%n", evt.getPropertyName());
        System.out.printf("OldValue: %s%n", evt.getOldValue());
        System.out.printf("NewValue: %s%n", evt.getNewValue());
    }
}
