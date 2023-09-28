package cr.ac.una.presentation;

import cr.ac.una.data.ViewModel;
import cr.ac.una.logic.ViewController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class View extends JFrame implements PropertyChangeListener {
    private final ViewController controller;
    private ViewModel viewModel;
    private JPanel mainPanel;

    public View(ViewController controller) {
        this.controller = controller;
        setTitle("Simon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        int MIN_WIDTH = 300;
        int MIN_HEIGHT = 300;
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        int PREF_WIDTH = 600;
        int PREF_HEIGHT = 400;
        Dimension defSize = new Dimension(PREF_WIDTH, PREF_HEIGHT);
        setSize(defSize);
    }

    public void init(ViewModel viewModel) {
        this.viewModel = viewModel;
        mainPanel = setupSimonPanel();
        mainPanel.addMouseListener(controller);
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel setupSimonPanel() {
        return new JPanel() {

            private final int MIN_DIAMETER = 180;
            private final int MARGIN = 50;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int diameter = Math.min(getWidth() - MARGIN * 2, getHeight() - MARGIN * 2);
                if (diameter < MIN_DIAMETER) diameter = MIN_DIAMETER;
                int x = (getWidth() - diameter) / 2;
                int y = (getHeight() - diameter) / 2;

                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
                drawSimon(g, x, y, diameter);

                if (viewModel.isInHud()) drawHud(g);
                drawPlayerFeedback(g);
            }

            void drawSimon(Graphics g, int x, int y, int diameter) {
                g.setColor(Color.BLACK);

                //Dibuja slices
                for (SliceButton sliceButton : controller.getSlices()) {
                    sliceButton.draw(g, x, y, diameter);
                }

                int diameterCent = diameter / 5;
                int xMid = (getWidth() / 2) - (diameterCent / 2);
                int yMid = (getHeight() / 2) - (diameterCent / 2);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.DARK_GRAY);
                g.fillOval(xMid, yMid, diameterCent, diameterCent);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(6));
                g.drawOval(xMid, yMid, diameterCent, diameterCent);
            }

            void drawHud(Graphics g) {
                g.setColor(Color.BLACK);
                //Place HUD
                GradientPaint gradient = new GradientPaint(0f, 0f, Color.BLACK, 0f, getHeight() * 4, new Color(0f, 0f, 0f, 0f));
                ((Graphics2D) g).setPaint(gradient);
                g.fillRect(0, 0, getWidth(), getHeight());

                String text;
                if(viewModel.isInGameOver()) text = "Perdiste! Haz click para volver a empezar!";
                else if(viewModel.isInNewLevel()) text = "Felicidades! Superaste esta ronda!\n\nDale click para aumentar de nivel!";
                else text = "Haz click para empezar!";

                g.setColor(Color.WHITE);
                Font font = new Font("Arial", Font.BOLD, 24);
                FontMetrics metrics = g.getFontMetrics(font);
                int xs = (getWidth() - metrics.stringWidth(text)) / 2;
                int ys = getHeight() / 5;
                g.setFont(font);
                g.drawString(text, xs, ys);
            }

            private void drawPlayerFeedback(Graphics g) {
                //Dibuja el timer
                g.setColor(Color.BLACK);
                int userTime = viewModel.getUserTime();
                String userTimeValue = String.format("Tiempo Restante: %d", userTime);
                Font userTimeFont = new Font("Arial", Font.BOLD, 12);
                int xs = 15;
                int ys = 40;
                g.setFont(userTimeFont);
                if (viewModel.isPlaying()) g.drawString(userTimeValue, xs, ys);

                //Dibuja la instrucción
                userTimeValue = viewModel.isPlaying() ? "Es tu turno! Repite la secuencia!" : "Observa la secuencia y trata de recordarla!";
                userTimeFont = new Font("Arial", Font.BOLD, 12);
                ys = 25;
                g.setFont(userTimeFont);
                g.drawString(userTimeValue, xs, ys);
            }
        };
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        new Thread(() -> mainPanel.repaint()).start();
    }
}