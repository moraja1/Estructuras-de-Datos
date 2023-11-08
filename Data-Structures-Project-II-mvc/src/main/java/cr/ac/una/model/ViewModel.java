package cr.ac.una.model;

import cr.ac.una.util.graphs.MGraph;

public class ViewModel {
    private final MGraph maze;

    public ViewModel(MGraph maze) {
        this.maze = maze;
    }

    public int getSizeX() {
        return maze.getSizeX();
    }

    public int getSizeY() {
        return maze.getSizeY();
    }

    public String getName() {
        return maze.getLabel();
    }


}
