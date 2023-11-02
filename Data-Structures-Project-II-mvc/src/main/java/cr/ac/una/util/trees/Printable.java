package cr.ac.una.util.trees;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface Printable {

    public void paint(Graphics g);

    public void paint(Graphics g, Rectangle bounds);

}
