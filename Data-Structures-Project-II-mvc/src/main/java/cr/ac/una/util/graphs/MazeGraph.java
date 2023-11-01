package cr.ac.una.util.graphs;

public class MazeGraph {
    private final int MIN_SIZE = 4;
    private final Vertex<Boolean>[][] matrix;

    public MazeGraph() {
        matrix = new Vertex[MIN_SIZE][MIN_SIZE];
        generateMaze();
    }

    public MazeGraph(int sizeX, int sizeY) {
        matrix = new Vertex[sizeX][sizeY];
        generateMaze();
    }

    public void generateMaze() {

    }
}
