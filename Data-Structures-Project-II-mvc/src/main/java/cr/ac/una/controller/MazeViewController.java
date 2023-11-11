package cr.ac.una.controller;

import cr.ac.una.model.ViewModel;
import cr.ac.una.view.MazeView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.ExecutorService;

public class MazeViewController implements Controller, MouseMotionListener {
    private final MazeView window;
    private final ViewModel vm;
    public static final int BASE_WINDOW_WIDTH = 480;
    public static final int BASE_WINDOW_HEIGHT = 360;
    public static final double BASE_SCALE = 1;
    private static final double MAX_SCALE = 8;
    private static final double MIN_SCALE = 0.125;
    private double currentScale;
    private final ExecutorService executor;

    public MazeViewController(ViewModel vm, ExecutorService executor) {
        this.window = new MazeView(vm, this);
        this.vm = vm;
        this.executor = executor;
        currentScale = BASE_SCALE;
        window.init();
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {
        executor.execute(() -> {
            if(e.getSource().equals(window.getIncreaseScale())){
                if(currentScale < MAX_SCALE) {
                    currentScale = currentScale * 2;
                }
            } else if(e.getSource().equals(window.getReduceScale())) {
                if(currentScale > MIN_SCALE) {
                    currentScale = currentScale / 2;
                }
            }
            window.updateWindow();
        });
    }

    public double getCurrentScale() {
        return currentScale;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        executor.execute(() -> {
            int xMouse = e.getX();
            int yMouse = e.getY();
            int x = (int) (xMouse / vm.getCellDimensions().width * currentScale);
            int y = (int) (yMouse / vm.getCellDimensions().height * currentScale);
            Point p = new Point(x, y);
            vm.setAsStartPoint(p);
            window.repaint();
        });
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
}
