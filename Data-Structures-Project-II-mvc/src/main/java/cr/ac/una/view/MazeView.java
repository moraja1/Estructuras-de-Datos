package cr.ac.una.view;

import cr.ac.una.model.ViewModel;

import javax.swing.*;
import java.awt.*;

public class MazeView extends JFrame {
    private final ViewModel mazeInfo;

    public MazeView(ViewModel mazeInfo) {
        super(mazeInfo.getName());
        setMinimumSize(new Dimension(480, 360));
        this.mazeInfo = mazeInfo;
        final JPanel mazeBoard = new JPanel() {
            @Override
            public void paint(Graphics g) {

            }
        };
        mazeBoard.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(mazeBoard, BorderLayout.CENTER);
    }
}
