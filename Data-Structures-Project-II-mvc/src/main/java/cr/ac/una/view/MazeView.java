package cr.ac.una.view;

import cr.ac.una.controller.Controller;
import cr.ac.una.controller.MazeViewController;
import cr.ac.una.model.ViewModel;

import javax.swing.*;
import java.awt.*;

import static cr.ac.una.controller.MazeViewController.*;

public class MazeView extends JFrame {
    private final MazeViewController controller;
    private final ViewModel mazeInfo;
    private JLabel scaleLabel;
    private JButton increaseScale;
    private JButton reduceScale;
    private JScrollPane scrollPane;
    private JPanel mazeBoard;
    public MazeView(ViewModel mazeInfo, Controller controller) {
        super(mazeInfo.getName());
        this.controller = (MazeViewController) controller;
        this.mazeInfo = mazeInfo;

        setMinimumSize(new Dimension(BASE_WINDOW_WIDTH, BASE_WINDOW_HEIGHT));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        scaleLabel = new JLabel();
        increaseScale = new JButton("+");
        reduceScale = new JButton("-");

        scrollPane = getScrollPane((MazeViewController) controller);
    }

    public void init() {
        //Init JLabel and JButtons
        scaleLabel.setText(updateLabel());
        increaseScale.addMouseListener(controller);
        reduceScale.addMouseListener(controller);
        reduceScale.setFocusPainted(false);
        increaseScale.setFont(new Font("Arial", Font.PLAIN, 10));
        reduceScale.setFont(new Font("Arial", Font.PLAIN, 14));
        increaseScale.setMargin(new Insets(0,0,0,0));
        reduceScale.setMargin(new Insets(0,0,0,0));
        increaseScale.setPreferredSize(new Dimension(15, 15));
        reduceScale.setPreferredSize(new Dimension(15, 15));
        increaseScale.setMaximumSize(new Dimension(15, 15));
        reduceScale.setMaximumSize(new Dimension(15, 15));
        increaseScale.setAlignmentX(Component.LEFT_ALIGNMENT);
        reduceScale.setAlignmentX(Component.LEFT_ALIGNMENT);
        scaleLabel.setPreferredSize(new Dimension(50, 15));
        scaleLabel.setMaximumSize(new Dimension(50, 25));

        //JPanel for JLabel and JButtons
        final JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.setBorder(BorderFactory.createEmptyBorder(0,2,2,2));
        southPanel.add(reduceScale);
        southPanel.add(Box.createHorizontalGlue());
        southPanel.add(scaleLabel);
        southPanel.add(Box.createHorizontalGlue());
        southPanel.add(increaseScale);

        //Add components
        add(scrollPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public String updateLabel() {
        return (controller.getCurrentScale() * 100) + "";
    }

    public void updateWindow() {
        scaleLabel.setText(updateLabel());
        scrollPane.getHorizontalScrollBar().revalidate();
        scrollPane.getVerticalScrollBar().revalidate();
        scrollPane.getHorizontalScrollBar().repaint();
        scrollPane.getVerticalScrollBar().repaint();
        repaint();
    }

    private JScrollPane getScrollPane(MazeViewController controller) {
        mazeBoard = new JPanel() {
            private final Dimension cellD = new Dimension(16, 16);
            private int sizeX;
            private int sizeY;
            @Override
            public Dimension getPreferredSize() {
                sizeX = mazeInfo.getSizeX();
                sizeY = mazeInfo.getSizeY();
                return new Dimension((int) ((cellD.width * sizeX) * controller.getCurrentScale()),
                        (int) ((cellD.height * sizeY) * controller.getCurrentScale()));
            }

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                sizeX = mazeInfo.getSizeX();
                sizeY = mazeInfo.getSizeY();
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.scale(controller.getCurrentScale(), controller.getCurrentScale());
                g2d.setColor(new Color(150, 50, 0));
                g2d.fillRect(0, 0, cellD.width * sizeX, cellD.height * sizeY);
                g2d.setColor(Color.WHITE);
                final boolean[][] drawing = mazeInfo.getDrawingMatrix();
                for(int i = 0; i < drawing.length; i++) {
                    for(int j = 0; j < drawing[i].length; j++) {
                        if(drawing[i][j]) {
                            mazeInfo.getDrawingPath()[i][j] = new Rectangle(i * cellD.width, j * cellD.width, cellD.width, cellD.height);
                            g2d.fill(mazeInfo.getDrawingPath()[i][j]);
                        }
                    }
                }
            }
        };

        return new JScrollPane(mazeBoard);
    }
    public JButton getIncreaseScale() {
        return increaseScale;
    }

    public JButton getReduceScale() {
        return reduceScale;
    }
}
