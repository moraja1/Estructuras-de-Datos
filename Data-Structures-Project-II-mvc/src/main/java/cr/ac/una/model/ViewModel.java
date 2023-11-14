package cr.ac.una.model;

import cr.ac.una.util.graphs.Edge;
import cr.ac.una.util.graphs.MGraph;
import cr.ac.una.util.graphs.VInfo;
import cr.ac.una.util.graphs.Vertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewModel {
    private final MGraph maze;
    private final boolean[][] drawingMatrix;
    private final Dimension cellD = new Dimension(16, 16);
    private final HashMap<Point, CellState> cellStates = new HashMap<>();
    final List<Point> possibleStartEnd = new ArrayList<>();

    public ViewModel(MGraph maze) {
        this.maze = maze;
        drawingMatrix = new boolean[getSizeX()][getSizeY()];
        initializeMatrix();
    }

    private void initializeMatrix() {
        for(int i = 1; i < drawingMatrix.length; i += 2) {
            for(int j = 1; j < drawingMatrix[i].length; j += 2) {
                drawingMatrix[i][j] = true;
                cellStates.put(new Point(i, j), CellState.UNDEFINED);
            }
        }

        for(int i = 1; i < drawingMatrix.length - 1; i += 2) {
            for(int j = 1; j < drawingMatrix[i].length - 1; j += 2) {
                if(i != getSizeX() - 2) {
                    int x = i / 2;
                    int y = j / 2;
                    drawingMatrix[i+1][j] = hasEdge(x, y, x + 1, y);
                    if(drawingMatrix[i+1][j]) cellStates.put(new Point(i+1, j), CellState.UNDEFINED);
                }
                if(j != getSizeY() - 2) {
                    int x = i / 2;
                    int y = j / 2;
                    drawingMatrix[i][j+1] = hasEdge(x, y, x, y + 1);
                    if(drawingMatrix[i][j+1]) cellStates.put(new Point(i, j+1), CellState.UNDEFINED);
                }
            }
        }
    }

    public int getSizeX() {
        return maze.getSizeX() + maze.getSizeX() + 1;
    }

    public int getSizeY() {
        return maze.getSizeY() + maze.getSizeY() + 1;
    }

    public String getName() {
        return maze.getLabel();
    }

    private boolean hasEdge(int x, int y, int x1, int y1) {
        var vStart = maze.getVertex(x, y);
        var vEnd = maze.getVertex(x1, y1);
        var mazeE = maze.getMazeEdges();

        List<Edge<VInfo<Character>>> vEdges = new ArrayList<>();
        for(var e : maze.getMazeEdges()) {
            if(e.getStart().equals(vStart) || e.getStart().equals(vEnd)) {
                vEdges.add(e);
            }
        }

        for(var e : vEdges) {
            if((e.getStart().equals(vStart) && e.getEnd().equals(vEnd)) ||
                    (e.getStart().equals(vEnd) && e.getEnd().equals(vStart))) {
                return true;
            }
        }

        return false;
    }

    public boolean[][] getDrawingMatrix() {
        return drawingMatrix;
    }

    public Dimension getCellDimensions() {
        return cellD;
    }
    public boolean isStartPoint(Point p) {
        return verifiesCellState(p, CellState.START);
    }

    public boolean isEndPoint(Point p) {
        return verifiesCellState(p, CellState.END);
    }

    public boolean isDrawnPoint(Point p) {
        return verifiesCellState(p, CellState.DRAWN);
    }

    private boolean verifiesCellState(Point p, CellState c) {
        CellState cs = cellStates.get(p);
        if (cs != null) return cs.equals(c);
        return false;
    }

    public void clearMaze(){
        cellStates.replaceAll((k, v) -> CellState.UNDEFINED);
    }

    public List<Point> getPossibleStartEnd() {
        return possibleStartEnd;
    }

    public HashMap<Point, CellState> getCellStates() {
        return cellStates;
    }

    public MGraph getMaze() {
        return maze;
    }

    public void setAsDrawn(Point p) {
        cellStates.put(p, CellState.DRAWN);
    }

    public void setAsUndef(Point p) {
        cellStates.put(p, CellState.UNDEFINED);
    }

    public void setAsReady(Point p){
        cellStates.put(p, CellState.READY);
    }

    public boolean isReadyPoint(Point p) {
        return verifiesCellState(p, CellState.READY);
    }

    public boolean isUndefPoint(Point p){
        return  verifiesCellState(p, CellState.UNDEFINED);
    }

    public enum CellState {
        START,
        END,
        DRAWN,
        UNDEFINED,
        READY
    }
}
