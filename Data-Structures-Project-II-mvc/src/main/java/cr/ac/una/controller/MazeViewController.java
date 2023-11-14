package cr.ac.una.controller;

import cr.ac.una.model.ViewModel;
import cr.ac.una.util.graphs.Edge;
import cr.ac.una.util.graphs.MGraph;
import cr.ac.una.util.graphs.VInfo;
import cr.ac.una.util.graphs.Vertex;
import cr.ac.una.view.MazeView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class MazeViewController implements Controller, MouseMotionListener, WindowListener {
    private MazeView window;
    private final ViewModel vm;
    public static final int BASE_WINDOW_WIDTH = 480;
    public static final int BASE_WINDOW_HEIGHT = 360;
    public static final double BASE_SCALE = 1;
    private static final double MAX_SCALE = 8;
    private static final double MIN_SCALE = 0.125;
    private double currentScale;
    private boolean playing = false;
    private final ExecutorService executor;
    private Future<?> futureTask;
    private boolean solved = false;
    private final HashMap<Point, List<Point>> drawnReady = new HashMap<>();

    public MazeViewController(ViewModel vm, ExecutorService executor) {
        this.vm = vm;
        this.executor = executor;
        currentScale = BASE_SCALE;
        createWindow();
    }

    public void createWindow() {
        this.window = new MazeView(vm, this);
        window.init();
    }

    private void solveMaze() {
        solved = true;
        placeStartEnd();
        solve();
        window.revalidate();
    }

    private void placeStartEnd() {
        selectRandom(ViewModel.CellState.START);
        selectRandom(ViewModel.CellState.END);
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
        if (futureTask.isCancelled()) return null;
        //Obtengo el vertice actual
        assert currentPoint != null;
        assert endP != null;
        boolean isStartOrEnd = currentPoint.equals(startP) || currentPoint.equals(endP);
        if (!isStartOrEnd) {
            vm.setAsDrawn(currentPoint);
            SwingUtilities.invokeLater(window::repaint);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
        }

        if (currentPoint.equals(endP)) {
            return currentPoint;
        }

        int xCurrent = currentPoint.x / 2;
        int yCurrent = currentPoint.y / 2;
        var currentVertex = maze.getVertex(xCurrent, yCurrent);

        //Creo listas abierta y cerrada
        List<Edge<VInfo<Character>>> openEdges = new ArrayList<>();
        List<Edge<VInfo<Character>>> closedEdges = new ArrayList<>();
        for (var e : mazeEdges) {
            if (e.getStart().equals(currentVertex) || e.getEnd().equals(currentVertex)) {
                openEdges.add(e);
            }
        }
        openEdges.removeAll(lastClosed);

        //Itero hasta que encuentre el vértice final de manera recursiva, si hay backtrace elimino la linea trazada
        while (!openEdges.isEmpty()) {
            if (futureTask.isCancelled()) return null;
            //Obtengo el primer edge de la lista abierta y el siguiente vertice a revisar
            var edge = openEdges.remove(0);
            closedEdges.add(edge);
            Vertex<VInfo<Character>> nextVertex;
            if (edge.getStart().equals(currentVertex)) nextVertex = edge.getEnd();
            else nextVertex = edge.getStart();
            int xNext = nextVertex.getInfo().getX();
            int yNext = nextVertex.getInfo().getY();

            //Dibujo el edge que conecta los dos vertices
            Point nextPoint = new Point(xCurrent + 1 + xNext, yCurrent + 1 + yNext);
            vm.setAsDrawn(nextPoint);
            SwingUtilities.invokeLater(window::repaint);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }

            //Paso el nuevo punto de manera recursiva
            var endPoint = solve(startP, new Point(xNext * 2 + 1, yNext * 2 + 1), endP, closedEdges);
            if (endPoint == null) openEdges.clear();
            else {
                if (endPoint.equals(endP)) {
                    return endPoint;
                } else {
                    vm.setAsUndef(nextPoint);
                    SwingUtilities.invokeLater(window::repaint);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }
        if (futureTask.isCancelled()) return null;
        if (!isStartOrEnd) vm.setAsUndef(currentPoint);
        SwingUtilities.invokeLater(window::repaint);
        try {
            Thread.sleep(10);
        } catch (InterruptedException ignored) {
        }
        return currentPoint;

    }

    private void selectRandom(ViewModel.CellState cellState) {
        if(cellState != ViewModel.CellState.START && cellState != ViewModel.CellState.END) {
            throw new IllegalArgumentException("This parameter should receive START or END only.");
        }

        var possibleStartEnd = vm.getPossibleStartEnd();
        var cellStates = vm.getCellStates();

        verifyPossibleStartEnd(possibleStartEnd, cellStates);

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
        boolean changed = false;
        for(var p : selectedList) {
            if(cellStates.get(p).equals(cellState)) changed = true;
        }

        final Random rdn = new Random();
        while(!changed) {
            var point = selectedList.get(rdn.nextInt(selectedList.size()));
            var pointState = cellStates.get(point);
            if (pointState != cellState) {
                cellStates.put(point, cellState);
                changed = true;
            }
        }
    }

    private void startConfigToPlay() {
        var possibleStartEnd = vm.getPossibleStartEnd();
        var cellStates = vm.getCellStates();

        verifyPossibleStartEnd(possibleStartEnd, cellStates);

        possibleStartEnd = possibleStartEnd.stream()
                .filter(n -> n.getX() == 1)
                .collect(Collectors.toList());
        
        for(var p : possibleStartEnd) {
            if(cellStates.get(p).equals(ViewModel.CellState.START)) {
                setNeighboursReady(p);
                break;
            }
        }
    }

    private void setNeighboursReady(Point p) {
        int xP = p.x;
        int yP = p.y;
        List<Point> readyList = new ArrayList<>();
        if(vm.isUndefPoint(new Point(xP + 1, yP))) readyList.add(new Point(xP + 1, yP));
        if(vm.isUndefPoint(new Point(xP - 1, yP))) readyList.add(new Point(xP - 1, yP));
        if(vm.isUndefPoint(new Point(xP, yP + 1))) readyList.add(new Point(xP, yP + 1));
        if(vm.isUndefPoint(new Point(xP, yP - 1))) readyList.add(new Point(xP, yP - 1));
        drawnReady.put(p, readyList);
    }

    private void verifyPossibleStartEnd(List<Point> possibleStartEnd, HashMap<Point, ViewModel.CellState> cellStates) {
        if(possibleStartEnd.isEmpty()){
            for (var k : cellStates.keySet()){
                if(k.getX() == 1 || k.getX() == vm.getSizeX() - 2) {
                    if(k.getY() % 2 == 1) possibleStartEnd.add(k);
                }
            }
        }
    }

    public double getCurrentScale() {
        return currentScale;
    }

    public boolean ownsMaze(MGraph maze) {
        return maze.equals(vm.getMaze());
    }

    public MazeView getWindow() {
        return window;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try{
            futureTask = executor.submit(() -> {
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
                            window.getInteractive().setVisible(false);
                        });
                        solveMaze();
                        SwingUtilities.invokeLater(() -> {
                            window.getClear().setEnabled(true);
                            window.getSolve().setEnabled(true);
                            window.getClear().setVisible(true);
                            window.getSolve().setVisible(true);
                            window.getInteractive().setVisible(true);
                        });
                    }
                } else if(e.getSource().equals(window.getClear())) {
                    vm.clearMaze();
                    solved = false;
                    playing = false;
                } else if(e.getSource().equals(window.getInteractive())) {
                    if(!playing) {
                        playing = true;
                        placeStartEnd();
                        startConfigToPlay();
                    }
                }
                window.updateWindow();
            });
        }catch (RejectedExecutionException ignored){}
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(playing) {
            try{
                executor.execute(() -> {
                    if(e.getButton() == MouseEvent.BUTTON1) {
                        processDrawn(e);
                    }
                });
            } catch (RejectedExecutionException ignored) {}
        }
    }

    private void processDrawn(MouseEvent e) {
        int xMouse = e.getX();
        int yMouse = e.getY();
        int x = (int) (xMouse / vm.getCellDimensions().width * currentScale);
        int y = (int) (yMouse / vm.getCellDimensions().height * currentScale);
        Point p = new Point(y, x);
        if(!vm.isStartPoint(p) & !vm.isEndPoint(p)) {
            boolean isReady = false;
            for(var l : drawnReady.values()) {
                if (l.contains(p)) {
                    isReady = true;
                    l.remove(p);
                    break;
                }
            }

            if(isReady) {
                vm.setAsDrawn(p);
                SwingUtilities.invokeLater(window::repaint);
                setNeighboursReady(p);
            }
        } else {
            if(vm.isEndPoint(p)) playing = false;
        }
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
    public void mouseDragged(MouseEvent e) {
        if(playing) {
            processDrawn(e);
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosing(WindowEvent e) {}
    @Override
    public void windowClosed(WindowEvent e) {
        if(futureTask != null) futureTask.cancel(true);
    }
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
}
