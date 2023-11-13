package cr.ac.una.controller;

import cr.ac.una.model.ViewModel;
import cr.ac.una.util.graphs.Edge;
import cr.ac.una.util.graphs.MGraph;
import cr.ac.una.util.graphs.VInfo;
import cr.ac.una.util.graphs.Vertex;
import cr.ac.una.view.MazeView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private boolean solved = false;

    public MazeViewController(ViewModel vm, ExecutorService executor) {
        this.window = new MazeView(vm, this);
        this.vm = vm;
        this.executor = executor;
        currentScale = BASE_SCALE;
        window.init();
    }

    private void solveMaze() {
        solved = true;
        selectRandom(ViewModel.CellState.START);
        selectRandom(ViewModel.CellState.END);
        solve();
        window.revalidate();
    }

    private void solve() {
        var maze = vm.getMaze();
        var cellStates = vm.getCellStates();

        //Busco el punto de inicio y el punto final
        Point startP = null;
        Point endP = null;
        for(var p : cellStates.keySet()) {
            if(cellStates.get(p).equals(ViewModel.CellState.START)) startP = p;
            if(cellStates.get(p).equals(ViewModel.CellState.END)) endP = p;
            if(startP != null && endP != null) break;
        }
        solve(startP, startP, endP, new ArrayList<>());
    }

    private Point solve(Point startP, Point currentPoint, Point endP, List<Edge<VInfo<Character>>> lastClosed) {
        var maze = vm.getMaze();
        var mazeEdges = maze.getMazeEdges();

        //Obtengo el vertice actual
        assert currentPoint != null;
        assert endP != null;
        boolean isStartOrEnd = currentPoint.equals(startP) || currentPoint.equals(endP);
        if(!isStartOrEnd) {
            vm.setAsDrawn(currentPoint);
            SwingUtilities.invokeLater(window::repaint);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}
        }

        if(currentPoint.equals(endP)) {
            return currentPoint;
        }

        int xCurrent = currentPoint.x / 2;
        int yCurrent = currentPoint.y / 2;
        var currentVertex = maze.getVertex(xCurrent, yCurrent);

        //Creo listas abierta y cerrada
        List<Edge<VInfo<Character>>> openEdges = new ArrayList<>();
        List<Edge<VInfo<Character>>> closedEdges = new ArrayList<>();
        for(var e : mazeEdges) {
            if(e.getStart().equals(currentVertex) || e.getEnd().equals(currentVertex)) {
                openEdges.add(e);
            }
        }
        openEdges.removeAll(lastClosed);

        //Itero hasta que encuentre el vértice final de manera recursiva, si hay backtrace elimino la linea trazada
        while(!openEdges.isEmpty()) {
            //Obtengo el primer edge de la lista abierta y el siguiente vertice a revisar
            var edge = openEdges.remove(0);
            closedEdges.add(edge);
            Vertex<VInfo<Character>> nextVertex;
            if(edge.getStart().equals(currentVertex)) nextVertex = edge.getEnd();
            else nextVertex = edge.getStart();
            int xNext = nextVertex.getInfo().getX();
            int yNext = nextVertex.getInfo().getY();

            //Dibujo el edge que conecta los dos vertices
            Point nextPoint = new Point(xCurrent + 1 + xNext, yCurrent + 1 + yNext);
            vm.setAsDrawn(nextPoint);
            SwingUtilities.invokeLater(window::repaint);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}

            //Paso el nuevo punto de manera recursiva
            var endPoint = solve(startP, new Point(xNext * 2 + 1, yNext * 2 + 1), endP, closedEdges);

            if(endPoint.equals(endP)) {
                return endPoint;
            } else {
                vm.setAsUndef(nextPoint);
                SwingUtilities.invokeLater(window::repaint);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {}
            }
        }
        if(!isStartOrEnd) vm.setAsUndef(currentPoint);
        SwingUtilities.invokeLater(window::repaint);
        try {
            Thread.sleep(10);
        } catch (InterruptedException ignored) {}
        return currentPoint;
    }

    private void selectRandom(ViewModel.CellState cellState) {
        if(cellState != ViewModel.CellState.START && cellState != ViewModel.CellState.END) {
            throw new IllegalArgumentException("This parameter should receive START or END only.");
        }

        var possibleStartEnd = vm.getPossibleStartEnd();
        var cellStates = vm.getCellStates();

        if(possibleStartEnd.isEmpty()){
            for (var k : cellStates.keySet()){
                if(k.getX() == 1 || k.getX() == vm.getSizeX() - 2) {
                    if(k.getY() % 2 == 1) possibleStartEnd.add(k);
                }
            }
        }

        List<Point> selectedList = null;
        if(cellState.equals(ViewModel.CellState.START)) {
            selectedList = possibleStartEnd.stream()
                    .filter(n -> n.getX() == 1)
                    .collect(Collectors.toList());
        } else {
            selectedList = possibleStartEnd.stream()
                    .filter(n -> n.getX() == vm.getSizeX() - 2)
                    .collect(Collectors.toList());
        }

        final Random rdn = new Random();
        boolean changed = false;
        while(!changed) {
            var point = selectedList.get(rdn.nextInt(selectedList.size()));
            var pointState = cellStates.get(point);
            if (pointState != cellState) {
                cellStates.put(point, cellState);
                changed = true;
            }
        }
    }

    public double getCurrentScale() {
        return currentScale;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try{
            executor.execute(() -> {
                if(e.getSource().equals(window.getIncreaseScale())){
                    if(currentScale < MAX_SCALE) {
                        currentScale = currentScale * 2;
                    }
                } else if(e.getSource().equals(window.getReduceScale())) {
                    if(currentScale > MIN_SCALE) {
                        currentScale = currentScale / 2;
                    }
                } else if(e.getSource().equals(window.getSolve())) {
                    if(!solved) {
                        SwingUtilities.invokeLater(() -> {
                            window.getClear().setEnabled(false);
                            window.getSolve().setEnabled(false);
                            window.getClear().setVisible(false);
                            window.getSolve().setVisible(false);
                        });
                        solveMaze();
                        SwingUtilities.invokeLater(() -> {
                            window.getClear().setEnabled(true);
                            window.getSolve().setEnabled(true);
                            window.getClear().setVisible(true);
                            window.getSolve().setVisible(true);
                        });
                    }
                } else if(e.getSource().equals(window.getClear())) {
                    vm.clearMaze();
                    solved = false;
                }
                window.updateWindow();
            });
        }catch (RejectedExecutionException ignored){}
    }
    @Override
    public void mousePressed(MouseEvent e) {
        try{
            executor.execute(() -> {
                int xMouse = e.getX();
                int yMouse = e.getY();
                int x = (int) (xMouse / vm.getCellDimensions().width * currentScale);
                int y = (int) (yMouse / vm.getCellDimensions().height * currentScale);


            });
        } catch (RejectedExecutionException ignored) {}
    }
    @Override
    public void actionPerformed(ActionEvent e) {}
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
