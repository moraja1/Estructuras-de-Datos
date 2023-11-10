package cr.ac.una.model;

import cr.ac.una.util.graphs.Edge;
import cr.ac.una.util.graphs.MGraph;
import cr.ac.una.util.graphs.VInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final MGraph maze;
    private final boolean[][] drawingMatrix;
    private final Rectangle[][] drawingPath;

    public ViewModel(MGraph maze) {
        this.maze = maze;
        drawingMatrix = new boolean[getSizeX()][getSizeY()];
        drawingPath = new Rectangle[getSizeX()][getSizeY()];
        initializeMatrix();
    }

    private void initializeMatrix() {
        for(int i = 1; i < drawingMatrix.length; i += 2) {
            for(int j = 1; j < drawingMatrix[i].length; j += 2) {
                drawingMatrix[i][j] = true;
            }
        }

        for(int i = 1; i < drawingMatrix.length - 1; i += 2) {
            for(int j = 1; j < drawingMatrix[i].length - 1; j += 2) {
                if(i != getSizeX() - 2) {
                    int x = i / 2;
                    int y = j / 2;
                    drawingMatrix[i+1][j] = hasEdge(x, y, x + 1, y);
                }
                if(j != getSizeY() - 2) {
                    int x = i / 2;
                    int y = j / 2;
                    drawingMatrix[i][j+1] = hasEdge(x, y, x, y + 1);
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

    public List<Edge<VInfo<Character>>> maze() {
        return new ArrayList<>(maze.getMazeEdges());
    }

    public boolean hasEdge(int x, int y, int x1, int y1) {
        var vStart = maze.getVertex(x, y);
        var vEnd = maze.getVertex(x1, y1);
        List<Edge<VInfo<Character>>> vEdges = new ArrayList<>();
        for(var e : maze.getMazeEdges()) {
            if(e.getStart().equals(vStart) || e.getStart().equals(vEnd)) {
                vEdges.add(e);
            }
        }

        for(var e : vEdges) {
            if((e.getStart().equals(vStart) && e.getEnd().equals(vEnd)) || (e.getStart().equals(vEnd) && e.getEnd().equals(vStart))) {
                return true;
            }
        }

        return false;
    }

    public boolean[][] getDrawingMatrix() {
        return drawingMatrix;
    }

    public Rectangle[][] getDrawingPath() {
        return drawingPath;
    }
}
