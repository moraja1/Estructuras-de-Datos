package cr.ac.una.view;

import cr.ac.una.controller.Controller;
import cr.ac.una.controller.MazeViewController;
import cr.ac.una.model.ViewModel;

import javax.swing.*;
import java.awt.*;

import static cr.ac.una.controller.MazeViewController.*;

public class MazeView extends JFrame {
    private final MazeViewController controller;
    private final ViewModel vm;
    private final JLabel scaleLabel;
    private final JButton increaseScale;
    private final JButton reduceScale;
    private final JButton solve;
    private final JButton clear;
    private final JButton interactive;
    private final JScrollPane scrollPane;

    public MazeView(ViewModel vm, Controller controller) {
        super(vm.getName());
        this.controller = (MazeViewController) controller;
        this.vm = vm;

        setMinimumSize(new Dimension(BASE_WINDOW_WIDTH, BASE_WINDOW_HEIGHT));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        scaleLabel = new JLabel();
        increaseScale = new JButton("+");
        reduceScale = new JButton("-");
        solve = new JButton("Resolver");
        clear = new JButton("Reiniciar");
        interactive = new JButton("Jugar!");

        scrollPane = getScrollPane((MazeViewController) controller);
    }

    public void init() {
        //Init JLabel and JButtons
        scaleLabel.setText(updateLabel());
        increaseScale.addMouseListener(controller);
        reduceScale.addMouseListener(controller);
        solve.addMouseListener(controller);
        clear.addMouseListener(controller);
        interactive.addMouseListener(controller);
        clear.setFocusPainted(false);
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
        solve.setFont(new Font("Arial", Font.PLAIN, 14));
        solve.setMargin(new Insets(0,0,0,0));
        solve.setFocusPainted(false);
        clear.setFont(new Font("Arial", Font.PLAIN, 14));
        clear.setMargin(new Insets(0,0,0,0));
        interactive.setFocusPainted(false);
        interactive.setFont(new Font("Arial", Font.PLAIN, 14));
        interactive.setMargin(new Insets(0,0,0,0));

        //JPanel for JLabel and JButtons for Scale
        final JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.setBorder(BorderFactory.createEmptyBorder(0,2,2,2));
        southPanel.add(reduceScale);
        southPanel.add(Box.createHorizontalGlue());
        southPanel.add(scaleLabel);
        southPanel.add(Box.createHorizontalGlue());
        southPanel.add(increaseScale);

        //JPanel for JButtons on top
        final JPanel northPanel = new JPanel();
        northPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        northPanel.add(Box.createHorizontalGlue());
        northPanel.add(solve);
        northPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        northPanel.add(clear);
        northPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        northPanel.add(interactive);


        //Add components
        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        addWindowListener(controller);
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
        JPanel mazeBoard = new JPanel() {
            private final Dimension cellD = vm.getCellDimensions();
            private int sizeX;
            private int sizeY;

            @Override
            public Dimension getPreferredSize() {
                sizeX = vm.getSizeX();
                sizeY = vm.getSizeY();
                return new Dimension((int) ((cellD.width * sizeX) * controller.getCurrentScale()),
                        (int) ((cellD.height * sizeY) * controller.getCurrentScale()));
            }

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                sizeX = vm.getSizeX();
                sizeY = vm.getSizeY();
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.scale(controller.getCurrentScale(), controller.getCurrentScale());
                g2d.setColor(new Color(150, 50, 0));
                g2d.fillRect(0, 0, cellD.height * sizeY, cellD.width * sizeX);
                g2d.setColor(Color.WHITE);
                final boolean[][] drawing = vm.getDrawingMatrix();
                for (int i = 0; i < drawing.length; i++) {
                    for (int j = 0; j < drawing[i].length; j++) {
                        if (drawing[i][j]) {
                            if (vm.isStartPoint(new Point(i, j))) {
                                g2d.setColor(Color.GREEN);
                            } else if (vm.isEndPoint(new Point(i, j))) {
                                g2d.setColor(Color.RED);
                            } else if (vm.isDrawnPoint(new Point(i, j))) {
                                g2d.setColor(Color.BLACK);
                            }
                            g2d.fillRect(j * cellD.width, i * cellD.height,
                                    cellD.width, cellD.height);
                        }
                        g2d.setColor(Color.WHITE);
                    }
                }
            }
        };
        mazeBoard.addMouseListener(controller);
        mazeBoard.addMouseMotionListener(controller);
        return new JScrollPane(mazeBoard);
    }
    public JButton getIncreaseScale() {
        return increaseScale;
    }

    public JButton getReduceScale() {
        return reduceScale;
    }

    public JButton getSolve() {
        return solve;
    }

    public JButton getClear() {
        return clear;
    }

    public JButton getInteractive() {
        return interactive;
    }
}
