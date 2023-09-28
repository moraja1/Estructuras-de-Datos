package cr.ac.una.presentation;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Esta clase representa un boton que se ilumina en el SimonModel basado en una secuencia
 * controlada por el SimonModel.
 */
public class SliceButton {
    private final int startAngle;
    private final int arcAngle;
    private final Color buttonColor;
    private final Color lightColor;
    private boolean isLightning;
    private final PropertyChangeSupport support;

    public SliceButton(Color buttonColor, Color lightColor, int startAngle, int arcAngle) {
        support = new PropertyChangeSupport(this);
        this.startAngle = startAngle;
        this.arcAngle = arcAngle;
        this.buttonColor = buttonColor;
        this.lightColor = lightColor;
        isLightning = false;
    }

    /**
     * Este método sirve para dibujar el elemento en el JPanel del SimonModel.
     * @param g
     * @param x
     * @param y
     * @param diameter
     */
    public void draw(Graphics g, int x, int y, int diameter){
        g.setColor((isLightning) ? lightColor : buttonColor);
        // Dibujar un sector circular relleno con el centro en (100, 100) y el radio de 80
        // El ángulo inicial es starAngle y el ángulo de extensión es arcAngle
        g.fillArc(x, y, diameter, diameter, startAngle, arcAngle);

        g.setColor(Color.BLACK);
        ((Graphics2D)g).setStroke(new BasicStroke(8f));

        // Dibuja el contorno del arco
        g.drawArc(x, y, diameter, diameter, startAngle, arcAngle);

        // Calcula las coordenadas del centro del arco
        int centerX = x + diameter / 2;
        int centerY = y + diameter / 2;

        // Calcula las coordenadas del punto donde comienza el arco
        double start = Math.toRadians(startAngle);
        int startX = (int) (centerX + diameter / 2 * Math.cos(start));
        int startY = (int) (centerY - diameter / 2 * Math.sin(start));

        // Calcula las coordenadas del punto donde termina el arco
        double end = Math.toRadians(startAngle + arcAngle);
        int endX = (int) (centerX + diameter / 2 * Math.cos(end));
        int endY = (int) (centerY - diameter / 2 * Math.sin(end));

        // Dibuja las líneas internas del "slice" de pizza
        g.drawLine(centerX, centerY, startX, startY);
        g.drawLine(centerX, centerY, endX, endY);
    }
    public void setLightning(boolean isLightning) {
        support.firePropertyChange("isLightning", this.isLightning, isLightning);
        this.isLightning = isLightning;
    }
    public boolean isLightning() {
        return isLightning;
    }
    public Color getLightColor() {
        return lightColor;
    }
    public Color getButtonColor() {
        return buttonColor;
    }
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void setObserver(PropertyChangeListener observer) {
        addPropertyChangeListener(observer);
    }
}
