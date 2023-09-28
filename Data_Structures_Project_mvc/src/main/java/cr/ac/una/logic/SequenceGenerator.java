package cr.ac.una.logic;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class SequenceGenerator {
    public static Color getRandomColor(List<Color> colors) {
        Random rdm = new Random();
        int randomNum = rdm.nextInt(colors.size());
        return colors.get(randomNum);
    }
}
